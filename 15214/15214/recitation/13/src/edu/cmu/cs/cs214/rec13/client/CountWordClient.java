package edu.cmu.cs.cs214.rec13.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.cmu.cs.cs214.rec13.ExecuteTaskCommand;
import edu.cmu.cs.cs214.rec13.WorkerServer;
import edu.cmu.cs.cs214.rec13.tasks.CountWordTask;
import edu.cmu.cs.cs214.rec13.util.WorkerConfig;

/**
 * A simple client that submits a task to three {@link WorkerServer}s, and
 * prints the combined result.
 */
public class CountWordClient extends Thread {

	/** Create at most one thread per available processor on this machine. */
	private static final int MAX_POOL_SIZE = Runtime.getRuntime()
			.availableProcessors();

	private final CountWordTask mTask;
	private final List<WorkerConfig> mWorkers;
	private final ExecutorService mExecutor;

	public CountWordClient(CountWordTask task, List<WorkerConfig> workers) {
		int numThreads = Math.min(MAX_POOL_SIZE, workers.size());
		mExecutor = Executors.newFixedThreadPool(numThreads);
		mTask = task;
		mWorkers = workers;
	}

	@Override
    public void run() {
        // TODO: (1) Send the task to a worker.
    	    

       
    	    // TODO: (Part 2) Convert the above three steps to work with multiple
            //       workers, with communication between the client and each worker
            //       on different threads.
    	    List<CountCallable> callables = new ArrayList<CountCallable>();
    	    for (WorkerConfig w: mWorkers){
    	    	    callables.add(new CountCallable(w));
    	    }
    	    
    	    List<Future<Integer>> results = null;
    	    		try{
    	    			results = mExecutor.invokeAll(callables);
    	    		}catch(InterruptedException e){
    	    			e.printStackTrace();
    	    		}
    	    
    	 
    	 int count = 0;
    	 for (Future<Integer> result: results){
    		 try{
    		     count += result.get();
    		 }catch(InterruptedException e){
    			 e.printStackTrace();
    		 }catch (ExecutionException e){
    			 e.printStackTrace();
    		 }
    	 }
    	 
    	 System.out.println(count);
 
    }

	private class CountCallable implements Callable<Integer> {
		WorkerConfig w;

		public CountCallable(WorkerConfig w) {
			this.w = w;
		}

		@Override
		public Integer call() throws Exception {
			WorkerConfig w = mWorkers.get(0);
			Socket socket = new Socket(w.getHost(), w.getPort());
			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());
			out.writeObject(new ExecuteTaskCommand<Integer>(mTask, w.getFile()));
			// TODO: (2) Wait for the worker to send back the result.

			ObjectInputStream in = new ObjectInputStream(
					socket.getInputStream());

			// TODO: (3) After the result is sent back, print it out.
			System.out.println((Integer) in.readObject());
			return (Integer) in.readObject();
		}
	}

	public static void main(String[] args) {
		// NOTE: These values are based on completion of Part 2 (sending the
		// task to multiple workers).

		List<WorkerConfig> workers = Arrays.asList(WorkerConfig.values());
		new CountWordClient(new CountWordTask("just"), workers).start(); // 75
		// new CountWordClient(new CountWordTask("gregor"), workers).start(); //
		// 298
		// new CountWordClient(new CountWordTask("the"), workers).start(); //
		// 2654
	}

}
