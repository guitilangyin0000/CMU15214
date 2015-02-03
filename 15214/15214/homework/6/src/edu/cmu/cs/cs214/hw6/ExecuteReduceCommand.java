package edu.cmu.cs.cs214.hw6;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import edu.cmu.cs.cs214.hw6.util.KeyValuePair;
import edu.cmu.cs.cs214.hw6.util.Log;
import edu.cmu.cs.cs214.hw6.util.WorkerStorage;

/**
 * ExecuteReduceCommand class, which will collect data from other workers by
 * sockets and then do the reduceTask. Each worker will execute several
 * ExecuteTransferCommands.
 * 
 * @author yangwan2
 *
 */
public class ExecuteReduceCommand extends WorkerCommand {

	private static final long serialVersionUID = 8641755479391895579L;
	private static final String TAG = "Execute Reduce Command";
	private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
	private final WorkerInfo mWorker;
	private final ReduceTask reduceTask;
	private final int hashcode;
	private List<ExecuteTransferCommand> transferCommands;
	private Map<String, List<String>> reduceData;

	public ExecuteReduceCommand(List<ExecuteTransferCommand> transferCommands,
			int hashcode, WorkerInfo mWorker, ReduceTask reduceTask) {
		Log.i(TAG, "Set up reduce task!");
		this.mWorker = mWorker;
		this.reduceTask = reduceTask;
		this.hashcode = hashcode;
		this.transferCommands = transferCommands;
		reduceData = Collections
				.synchronizedMap(new HashMap<String, List<String>>());
		Log.i(TAG, "Set up reduce task done!");
	}

	private class TransferCallable implements Callable<Void> {
		private final WorkerInfo worker;
		private final ExecuteTransferCommand transferCommand;

		public TransferCallable(ExecuteTransferCommand transferCommand) {
			this.worker = transferCommand.getWorker();
			this.transferCommand = transferCommand;
		}

		public WorkerInfo getWorker() {
			return worker;
		}

		@Override
		public Void call() throws Exception {
			Socket socket = null;
			ObjectOutputStream out = null;
			ObjectInputStream in = null;
			try {
				socket = new Socket(worker.getHost(), worker.getPort());
				out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(transferCommand);
				in = new ObjectInputStream(socket.getInputStream());
				// try to get all keypairs sent from other workers
				while (true) {
					KeyValuePair keyPair = (KeyValuePair) in.readObject();
					String key = keyPair.getKey();
					String value = keyPair.getValue();
					if (reduceData.containsKey(key)) {
						reduceData.get(key).add(value);
					} else {
						// set up synchronized list to add value for specified key
						List<String> old = Collections
								.synchronizedList(new ArrayList<String>());
						old.add(value);
						reduceData.put(key, old);
					}
				}
			} catch (EOFException e) {
				return null;
			} catch (FileNotFoundException e) {
				Log.e(TAG, "Fail to create or find the file!", e);
			} catch (IOException e) {
				Log.e(TAG, "I/O exception when executing the reduce task!", e);
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
			return null;
		}
	}

	/**
	 * Method that will get the current time
	 * 
	 * @return
	 */
	private String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		return sdf.format(date);
	}

	/**
	 * run method, which will start the reduce work
	 */
	@Override
	public void run() {
		final ExecutorService mExecutor = Executors
				.newFixedThreadPool(POOL_SIZE);
		Log.i(TAG, "reduce task run!");
		String fileName = "final" + hashcode + '_' + getCurrentTime() + ".txt";
		String finalPath = WorkerStorage.getFinalResultsDirectory(mWorker
				.getName()) + "/" + fileName;
		Log.i(TAG, "Final path is: " + finalPath);
		Socket masterSocket = null;
		Emitter emitter = null;
		ObjectOutputStream out = null;
		masterSocket = getSocket();
		try {
			emitter = new EmitterConcrete(finalPath);
			// handle paths in other machines
			Log.i(TAG, "Transfer task for: " + mWorker.getName() + " start!");
			List<TransferCallable> transferCallables = new ArrayList<TransferCallable>();
			for (ExecuteTransferCommand transferCommand : transferCommands) {
				transferCallables.add(new TransferCallable(transferCommand));
			}
			List<Future<Void>> data = null;
			try {
				data = mExecutor.invokeAll(transferCallables);

			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			for (int i = 0; i < data.size(); i++) {
				TransferCallable transferCallable = transferCallables.get(i);
				Future<Void> result = data.get(i);
				try {
					// try to get void object and handle exception
					result.get();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (ExecutionException e) {
					WorkerInfo failWorker = transferCallable.getWorker();
					String workerHost = failWorker.getHost();
					int workerPort = failWorker.getPort();
					String info = String.format("[host=%s, port=%d]",
							workerHost, workerPort);
					Log.e(TAG,
							"Warning! Failed to execute transfer task for worker: "
									+ info, e.getCause());
				}
			}

			Log.i(TAG, "Transfer task for: " + mWorker.getName() + " done!");
			for (Map.Entry<String, List<String>> entry : reduceData.entrySet()) {
				Iterator<String> iter = entry.getValue().iterator();
				reduceTask.execute(entry.getKey(), iter, emitter);
			}
			out = new ObjectOutputStream(masterSocket.getOutputStream());
			out.writeObject(finalPath);
		} catch (FileNotFoundException e) {
			Log.e(TAG, "Fail to create or find the file!", e);
		} catch (IOException e) {
			Log.e(TAG, "I/O exception when executing the reduce task!", e);
		} finally {
			try {
				if (emitter != null) {
					emitter.close();
				}
				if (out != null) {
					out.close();
				}
				if (masterSocket != null) {
					masterSocket.close();
				}
			} catch (IOException e) {
				Log.e(TAG, "I/O exception when closing stream!");
			}
		}
	}
}
