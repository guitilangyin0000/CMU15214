package edu.cmu.cs.cs214.hw6;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import edu.cmu.cs.cs214.hw6.util.Log;
import edu.cmu.cs.cs214.hw6.util.StaffUtils;
import edu.cmu.cs.cs214.hw6.util.WorkerStorage;

/**
 * This class represents the "master server" in the distributed map/reduce
 * framework. The {@link MasterServer} is in charge of managing the entire
 * map/reduce computation from beginning to end. The {@link MasterServer}
 * listens for incoming client connections on a distinct host/port address, and
 * is passed an array of {@link WorkerInfo} objects when it is first initialized
 * that provides it with necessary information about each of the available
 * workers in the system (i.e. each worker's name, host address, port number,
 * and the set of {@link Partition}s it stores). A single map/reduce computation
 * managed by the {@link MasterServer} will typically behave as follows:
 *
 * <ol>
 * <li>Wait for the client to submit a map/reduce task.</li>
 * <li>Distribute the {@link MapTask} across a set of "map-workers" and wait for
 * all map-workers to complete.</li>
 * <li>Distribute the {@link ReduceTask} across a set of "reduce-workers" and
 * wait for all reduce-workers to complete.</li>
 * <li>Write the locations of the final results files back to the client.</li>
 * </ol>
 */
public class MasterServer extends Thread {
	private static final String TAG = "Master Server";
	// Get the avaliable size of thread pool
	private static final int POOL_SIZE = Runtime.getRuntime()
			.availableProcessors();
	private final int mPort;
	private final List<WorkerInfo> mWorkers;
	// For connecting with client
	private final ExecutorService mExecutorClient;
	// For connecting with worker server
	private final ExecutorService mExecutorWorker;
	// When doing mapper work, we don't want to map the same partition for
	// multiple times, we use String instead object to avoid not equaling
	private HashMap<String, Boolean> partiDist;
	// Record every partition with its avaliable workers
	private HashMap<String, ArrayList<WorkerInfo>> partiStorage;
	// HealthCheck map, check the health status of the worker when doing tasks
	private HashMap<WorkerInfo, Boolean> healthCheck;
	// Record the intermediate result path for specified map worker
	private HashMap<WorkerInfo, ArrayList<String>> interResults;
	// Record the code number for health workers
	private HashMap<WorkerInfo, Integer> workerCodes;
	// Reverse workerCodes
	private HashMap<Integer, WorkerInfo> codeWorkers;
	// Record the paths after shuffling for specified hashcode(mod)
	private HashMap<Integer, ArrayList<String>> shuffleResults;
	// Record each shuffleResultPath with each WorkerInfo
	private HashMap<String, WorkerInfo> shufflePathWorkers;

	/**
	 * The {@link MasterServer} constructor.
	 *
	 * @param masterPort
	 *            The port to listen on.
	 * @param workers
	 *            Information about each of the available workers in the system.
	 */
	public MasterServer(int masterPort, List<WorkerInfo> workers) {
		mPort = masterPort;
		mWorkers = workers;
		// For master server, we only need one thread
		mExecutorClient = Executors.newSingleThreadExecutor();
		mExecutorWorker = Executors.newFixedThreadPool(Math.min(
				mWorkers.size(), POOL_SIZE));
		healthCheck = new HashMap<WorkerInfo, Boolean>();
		partiDist = new HashMap<String, Boolean>();
		partiStorage = new HashMap<String, ArrayList<WorkerInfo>>();
		interResults = new HashMap<WorkerInfo, ArrayList<String>>();
		workerCodes = new HashMap<WorkerInfo, Integer>();
		codeWorkers = new HashMap<Integer, WorkerInfo>();
		shuffleResults = new HashMap<Integer, ArrayList<String>>();
		shufflePathWorkers = new HashMap<String, WorkerInfo>();
		initialMaps();
		clearIntermediateFiles();
		clearFinalFiles();
	}

	/**
	 * Method that can delete all the files under the intermediate folder
	 */
	private void clearIntermediateFiles() {
		for (WorkerInfo worker : mWorkers) {
			String dir = WorkerStorage.getIntermediateResultsDirectory(worker
					.getName());
			File dirFiles = new File(dir);
			for (File file : dirFiles.listFiles()) {
				file.delete();
			}
		}
	}

	/**
	 * Method that can delete all the files under the final folder
	 */
	private void clearFinalFiles() {
		for (WorkerInfo worker : mWorkers) {
			String dir = WorkerStorage.getFinalResultsDirectory(worker
					.getName());
			File dirFiles = new File(dir);
			for (File file : dirFiles.listFiles()) {
				file.delete();
			}
		}
	}

	/**
	 * Initial the HashMaps for storing infos
	 */
	private void initialMaps() {
		for (WorkerInfo worker : mWorkers) {
			// Suppose all the workers are good at the beginning.
			if (!healthCheck.containsKey(worker)) {
				healthCheck.put(worker, true);
			}
			// Set the intermediate results to be empty list for workers
			if (!interResults.containsKey(worker)) {
				interResults.put(worker, new ArrayList<String>());
			}
			for (Partition partition : worker.getPartitions()) {
				String name = partition.getPartitionName();
				if (!partiDist.containsKey(name)) {
					partiDist.put(name, false);
				}
				if (!partiStorage.containsKey(name)) {
					ArrayList<WorkerInfo> storages = new ArrayList<WorkerInfo>();
					storages.add(worker);
					partiStorage.put(name, storages);
				} else {
					Boolean isIn = false;
					for (WorkerInfo workerInfo : partiStorage.get(name)) {
						if (workerInfo.equals(worker)) {
							isIn = true;
							break;
						}
					}
					if (!isIn) {
						ArrayList<WorkerInfo> tmp = partiStorage.get(name);
						tmp.add(worker);
						partiStorage.put(name, tmp);
					}
				}
			}
		}
	}

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(mPort);
			} catch (IOException e) {
				Log.e(TAG, "Could not open server socket on port " + mPort
						+ ".", e);
				return;
			}

			Log.i(TAG, "Listening for incoming commands on port " + mPort + ".");

			while (true) {
				try {
					Socket clientSocket = serverSocket.accept();
					mExecutorClient.execute(new WorkerCommandHandler(
							clientSocket));
				} catch (IOException e) {
					Log.e(TAG,
							"Error while listening for incoming connections.",
							e);
					break;
				}
			}

			Log.i(TAG, "Shutting down...");

			try {
				serverSocket.close();
			} catch (IOException e) {
				// Ignore because we're about to exit anyway.
			}
		} finally {
			mExecutorClient.shutdown();
		}
	}

	/**
	 * Handles a single worker-client request.
	 */
	private class WorkerCommandHandler implements Runnable {
		private final Socket mSocket;

		public WorkerCommandHandler(Socket socket) {
			mSocket = socket;
		}

		@Override
		public void run() {
			List<MapCallable> mapCallables = new ArrayList<MapCallable>();
			List<ShuffleCallable> shuffleCallables = new ArrayList<ShuffleCallable>();
			List<ReduceCallable> reduceCallables = new ArrayList<ReduceCallable>();
			int healthWorkerSize = -1;
			int unhealthWorkerSize = -1;
			ObjectInputStream in = null;
			try {
				Log.i(TAG, "Map task start!");
				in = new ObjectInputStream(mSocket.getInputStream());
				MapTask mapTask = (MapTask) in.readObject();
				// The complete flag for map work
				// if a map worker fails, the common work will
				// be remarked as undone and let other workers 
				// finish, until all the callable functions
				// get the results successfully.
				Boolean isComplete = false;
				while (!isComplete) {
					for (WorkerInfo worker : mWorkers) {
						if (!healthCheck.get(worker)) {
							continue;
						}
						mapCallables.add(new MapCallable(mapTask, worker));
					}
					healthWorkerSize = mapCallables.size();
					unhealthWorkerSize = mWorkers.size() - healthWorkerSize;
					Log.i(TAG, "Number of health worker: " + healthWorkerSize);
					Log.i(TAG, "Number of unhealth worker: "
							+ unhealthWorkerSize);
					List<Future<ArrayList<String>>> results = null;
					try {
						results = mExecutorWorker.invokeAll(mapCallables);

					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					// If all the results are without exception, map task
					// complete
					isComplete = true;
					for (int i = 0; i < results.size(); i++) {
						MapCallable mapCallable = mapCallables.get(i);
						Future<ArrayList<String>> result = results.get(i);
						try {
							ArrayList<String> paths = result.get();
							WorkerInfo worker = mapCallable.getWorker();
							ArrayList<String> resultPaths = interResults
									.get(worker);
							for (String path : paths) {
								// System.out.println("path: " + path);
								resultPaths.add(path);
							}
							interResults.put(worker, resultPaths);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						} catch (ExecutionException e) {
							WorkerInfo failWorker = mapCallable.getWorker();
							String workerHost = failWorker.getHost();
							int workerPort = failWorker.getPort();
							String info = String.format("[host=%s, port=%d]",
									workerHost, workerPort);
							Log.e(TAG,
									"Warning! Failed to execute map task for worker: "
											+ info, e.getCause());
							isComplete = false;
							healthCheck.put(failWorker, false);
							for (Partition partition : failWorker
									.getPartitions()) {
								partiDist.put(partition.getPartitionName(),
										false);
							}
							checkPartition();
						}
					}
				}
				Log.i(TAG, "Map task done!");
				Log.i(TAG, "Shuffle task start!");
				int index = 0;
				workerCodes.clear();
				codeWorkers.clear();
				shuffleResults.clear();
				shufflePathWorkers.clear();
				for (WorkerInfo worker : mWorkers) {
					if (!healthCheck.get(worker)) {
						continue;
					}
					workerCodes.put(worker, index);
					codeWorkers.put(index, worker);
					shuffleResults.put(index, new ArrayList<String>());
					shuffleCallables.add(new ShuffleCallable(worker,
							healthWorkerSize));
					index += 1;
				}
				List<Future<ArrayList<String>>> data = null;
				try {
					data = mExecutorWorker.invokeAll(shuffleCallables);

				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}

				for (int i = 0; i < data.size(); i++) {
					ShuffleCallable shuffleCallable = shuffleCallables.get(i);
					Future<ArrayList<String>> result = data.get(i);
					try {
						ArrayList<String> infos = result.get();
						for (String info : infos) {
							System.out.println("shuffle info: " + info);
							String[] infoList = info.split("#");
							int hashcode = Integer.parseInt(infoList[0]);
							String path = infoList[1];
							ArrayList<String> paths = shuffleResults
									.get(hashcode);
							paths.add(path);
							shuffleResults.put(hashcode, paths);
							shufflePathWorkers.put(path,
									codeWorkers.get(hashcode));
						}
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} catch (ExecutionException e) {
						WorkerInfo failWorker = shuffleCallable.getWorker();
						String workerHost = failWorker.getHost();
						int workerPort = failWorker.getPort();
						String info = String.format("[host=%s, port=%d]",
								workerHost, workerPort);
						Log.e(TAG,
								"Warning! Failed to execute shuffle task for worker: "
										+ info, e.getCause());
					}
				}
				Log.i(TAG, "Shuffle task done!");
				Log.i(TAG, "Reduce task start!");
				ReduceTask reduceTask = (ReduceTask) in.readObject();
				for (WorkerInfo worker : mWorkers) {
					if (!healthCheck.get(worker)) {
						continue;
					}
					reduceCallables.add(new ReduceCallable(reduceTask, worker));
				}
				List<Future<String>> finalResult = null;
				try {
					finalResult = mExecutorWorker.invokeAll(reduceCallables);

				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				ArrayList<String> finalResults = new ArrayList<String>();
				for (int i = 0; i < finalResult.size(); i++) {
					ReduceCallable reduceCallable = reduceCallables.get(i);
					Future<String> result = finalResult.get(i);
					try {
						String finalPath = result.get();
						finalResults.add(finalPath);
						Log.i(TAG, "The final path is: " + finalPath);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} catch (ExecutionException e) {
						WorkerInfo failWorker = reduceCallable.getWorker();
						String workerHost = failWorker.getHost();
						int workerPort = failWorker.getPort();
						String info = String.format("[host=%s, port=%d]",
								workerHost, workerPort);
						Log.e(TAG,
								"Warning! Failed to execute reduce task for worker: "
										+ info, e.getCause());
					}
				}
				ObjectOutputStream out = new ObjectOutputStream(
						mSocket.getOutputStream());
				out.writeObject(finalResults);
				Log.i(TAG, "Reduce task done!");
				// printMaps();

			} catch (IOException e) {
				Log.e(TAG, "Received invalid task from client.", e);
			} catch (ClassNotFoundException e) {
				Log.e(TAG, "Connection lost.", e);
			} finally {
				mExecutorWorker.shutdown();
			}

		}

	}

	/**
	 * Method that can debug the HashMaps
	 */
	private void printMaps() {
		// print interResults
		Iterator<Entry<WorkerInfo, ArrayList<String>>> iterator0 = interResults
				.entrySet().iterator();
		while (iterator0.hasNext()) {
			Map.Entry<WorkerInfo, ArrayList<String>> entry = iterator0.next();
			String name = entry.getKey().getName();
			for (String value : entry.getValue()) {
				System.out
						.println("Worker: " + name + " intern path: " + value);
			}
		}
		// print shuffleResults
		Iterator<Entry<Integer, ArrayList<String>>> iterator = shuffleResults
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer, ArrayList<String>> entry = iterator.next();
			int hashcode = entry.getKey();
			for (String value : entry.getValue()) {
				System.out.println("Hashcode: " + hashcode + " path: " + value);
			}
		}
		// print workercodes
		Iterator<Entry<WorkerInfo, Integer>> iterator1 = workerCodes.entrySet()
				.iterator();
		while (iterator1.hasNext()) {
			Map.Entry<WorkerInfo, Integer> entry = iterator1.next();
			String name = entry.getKey().getName();
			System.out.println("Worker: " + name + " hashcode: "
					+ entry.getValue());
		}
	}

	/**
	 * Method that can check the avaliability if all the partitions
	 */
	private void checkPartition() {
		Iterator<Map.Entry<String, ArrayList<WorkerInfo>>> iterator = partiStorage
				.entrySet().iterator();
		ArrayList<String> undoneParti = new ArrayList<String>();
		while (iterator.hasNext()) {
			Boolean done = false;
			Map.Entry<String, ArrayList<WorkerInfo>> entry = iterator.next();
			String partition = entry.getKey();
			ArrayList<WorkerInfo> workers = entry.getValue();
			for (WorkerInfo worker : workers) {
				if (healthCheck.get(worker)) {
					done = true;
				}
			}
			if (!done) {
				undoneParti.add(partition);
			}
		}
		if (undoneParti.isEmpty()) {
			Log.i(TAG, "We still can complete all the partitions!");
		} else {
			StringBuffer info = new StringBuffer();
			for (String partition : undoneParti) {
				info.append(" ");
				info.append(partition);
			}
			Log.i(TAG,
					"For partition:"
							+ info.toString()
							+ ", we could not complete them since all the relevant workers are not health now.");
		}
	}

	/**
	 * ReduceCallable class, which can sum up shuffle results to final results.
	 * 
	 * @author yangwan2
	 *
	 */
	private class ReduceCallable implements Callable<String> {

		private final ReduceTask mReduceTask;
		private final WorkerInfo mWorker;
		private final List<ExecuteTransferCommand> transferCommands;

		public ReduceCallable(ReduceTask reduceTask, WorkerInfo worker) {
			mReduceTask = reduceTask;
			mWorker = worker;
			transferCommands = new ArrayList<ExecuteTransferCommand>();
			for (String path : shuffleResults.get(workerCodes.get(mWorker))) {
				ExecuteTransferCommand transferCommand = new ExecuteTransferCommand(
						path, shufflePathWorkers.get(path));
				transferCommands.add(transferCommand);
			}
		}

		public WorkerInfo getWorker() {
			return mWorker;
		}

		@Override
		public String call() throws Exception {
			if (!healthCheck.containsKey(mWorker) || !healthCheck.get(mWorker)) {
				return null;
			}
			String finalPath = null;
			int hashcode = workerCodes.get(mWorker);
			Socket socket = null;
			ObjectOutputStream out = null;
			ObjectInputStream in = null;
			try {
				socket = new Socket(mWorker.getHost(), mWorker.getPort());
				out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(new ExecuteReduceCommand(transferCommands,
						hashcode, mWorker, mReduceTask));
				in = new ObjectInputStream(socket.getInputStream());
				finalPath = ((String) in.readObject());
			} catch (Exception e) {
				Log.e(TAG,
						"Error when connecting with the reduce task workers!");
				throw e;
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
					if (socket != null) {
						socket.close();
					}
				} catch (IOException e) {
					// Ignore because we're about to exit anyway.
				}
			}
			return finalPath;
		}

	}

	/**
	 * ShuffleCallable class, which can create separated txt files according to
	 * different mod hash codes.
	 * 
	 * @author yangwan2
	 *
	 */
	private class ShuffleCallable implements Callable<ArrayList<String>> {

		private final WorkerInfo mWorker;
		private final int healthWorkerNum;

		public ShuffleCallable(WorkerInfo worker, int number) {
			mWorker = worker;
			healthWorkerNum = number;
		}

		public WorkerInfo getWorker() {
			return mWorker;
		}

		@SuppressWarnings("unchecked")
		@Override
		public ArrayList<String> call() throws Exception {
			if (!healthCheck.containsKey(mWorker) || !healthCheck.get(mWorker)) {
				return null;
			}
			ArrayList<String> data = new ArrayList<String>();
			ArrayList<String> interPaths = interResults.get(mWorker);
			String baseDir = WorkerStorage
					.getIntermediateResultsDirectory(mWorker.getName());
			Socket socket = null;
			ObjectOutputStream out = null;
			ObjectInputStream in = null;
			try {
				socket = new Socket(mWorker.getHost(), mWorker.getPort());
				out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(new ExecuteShuffleCommand(baseDir, interPaths,
						healthWorkerNum));
				in = new ObjectInputStream(socket.getInputStream());
				data = ((ArrayList<String>) in.readObject());
			} catch (Exception e) {
				Log.e(TAG,
						"Error when connecting with the shuffle task workers!");
				throw e;
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
					if (socket != null) {
						socket.close();
					}
				} catch (IOException e) {
					// Ignore because we're about to exit anyway.
				}
			}
			return data;
		}

	}

	/**
	 * MapCallable class, which can return a list of intermediate paths
	 * 
	 * @author yangwan2
	 *
	 */
	private class MapCallable implements Callable<ArrayList<String>> {
		private final MapTask mMapTask;
		private final WorkerInfo mWorker;

		public MapCallable(MapTask mapTask, WorkerInfo mWorker) {
			this.mMapTask = mapTask;
			this.mWorker = mWorker;
		}

		public WorkerInfo getWorker() {
			return mWorker;
		}

		@Override
		public ArrayList<String> call() throws Exception {
			if (!healthCheck.containsKey(mWorker) || !healthCheck.get(mWorker)) {
				return null;
			}
			ArrayList<String> paths = new ArrayList<String>();
			for (Partition partition : mWorker.getPartitions()) {
				synchronized (this) {
					if (partiDist.get(partition.getPartitionName())) {
						continue;
					}
					// We need to block other mappers' actions when revising
					// the status of the partiDist HashMap.
					partiDist.put(partition.getPartitionName(), true);
				}
				Socket socket = null;
				ObjectOutputStream out = null;
				ObjectInputStream in = null;
				try {
					socket = new Socket(mWorker.getHost(), mWorker.getPort());
					out = new ObjectOutputStream(socket.getOutputStream());
					out.writeObject(new ExecuteMapCommand(mMapTask, partition));
					in = new ObjectInputStream(socket.getInputStream());
					paths.add((String) in.readObject());
				} catch (Exception e) {
					Log.e(TAG,
							"Error when connecting with the map task workers!");
					throw e;
				} finally {
					try {
						if (out != null) {
							out.close();
						}
						if (in != null) {
							in.close();
						}
						if (socket != null) {
							socket.close();
						}
					} catch (IOException e) {
						// Ignore because we're about to exit anyway.
					}
				}
			}
			return paths;

		}
	}

	/********************************************************************/
	/***************** STAFF CODE BELOW. DO NOT MODIFY. *****************/
	/********************************************************************/

	/**
	 * Starts the master server on a distinct port. Information about each
	 * available worker in the distributed system is parsed and passed as an
	 * argument to the {@link MasterServer} constructor. This information can be
	 * either specified via command line arguments or via system properties
	 * specified in the <code>master.properties</code> and
	 * <code>workers.properties</code> file (if no command line arguments are
	 * specified).
	 */
	public static void main(String[] args) {
		StaffUtils.makeMasterServer(args).start();
	}

}
