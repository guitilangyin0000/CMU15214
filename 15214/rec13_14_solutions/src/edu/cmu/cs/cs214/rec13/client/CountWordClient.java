package edu.cmu.cs.cs214.rec13.client;

import java.io.IOException;
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
import edu.cmu.cs.cs214.rec13.util.Log;
import edu.cmu.cs.cs214.rec13.util.WorkerConfig;

/**
 * A simple client that submits a task to three {@link WorkerServer}s, and
 * prints the combined result.
 */
public class CountWordClient extends Thread {
    private static final String TAG = "CountWordClient";

    /** Create at most one thread per available processor on this machine. */
    private static final int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors();

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
        try {
            List<CountCallable> callables = new ArrayList<CountCallable>();
            for (WorkerConfig worker : mWorkers) {
                // Create a callable task which, when executed, will send an
                // ExecuteTaskCommand to a distinct worker server in the system,
                // and wait for the worker server to execute the command and
                // write back its result.
                callables.add(new CountCallable(mTask, worker));
            }

            // The ith future object in 'countResults' corresponds to the
            // result of the ith callable object in 'countCallables'.
            List<Future<Integer>> results = null;
            try {
                // Executes all of the Callable tasks on background threads,
                // blocking until each call() method has either returned or
                // thrown an exception. This method provides an extremely
                // convenient way of executing multiple tasks in parallel (hint:
                // think about how you could use this in hw6).
                results = mExecutor.invokeAll(callables);
            } catch (InterruptedException e) {
                // If you are interested, there is a link to a great article
                // about when/how to deal with InterupttedExceptions below.
                // http://www.ibm.com/developerworks/java/library/j-jtp05236/index.html

                // Note that this exception will never be thrown in this sample
                // implementation (and more specifically, it would only ever be
                // thrown if the ExecutorService detected that we had explicitly
                // interuptted one of its threads, or if we had called
                // mExecutor.shutdownNow() in the middle of a call to invokeAll(),
                // etc). Understanding these concepts are beyond the scope of the
                // class, but just in case some of you were wondering. :)
                Thread.currentThread().interrupt();
            }

            // At this point we know that all of the callable tasks have
            // finished executing, so now we can sum up all of the results.
            int totalCount = 0;
            for (int i = 0; i < results.size(); i++) {
                CountCallable callable = callables.get(i);
                Future<Integer> result = results.get(i);
                try {
                    totalCount += result.get();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    // You will get this exception if the callable's call()
                    // method threw an exception when it was invoked by the
                    // ExecutorService. Hint: think about how (and for what
                    // purpose) you would better handle this exception in hw6!
                    String workerHost = callable.getWorker().getHost();
                    int workerPort = callable.getWorker().getPort();
                    String info = String.format("[host=%s, port=%d]", workerHost, workerPort);
                    Log.e(TAG, "Warning! Failed to execute task for worker: " + info, e.getCause());
                }
            }

            // Print the final result.
            Log.i(TAG, "Received total count: " + totalCount);
        } finally {
            // Don't forget to close your ExecutorService when you are finished
            // with it! Otherwise the JVM will never kill the process associated
            // with this program (since it will think that there are still
            // threads being executed inside).
            mExecutor.shutdown();
        }
    }

    /**
     * A command that manages a single client-worker connection to be executed
     * asynchronously.
     *
     * The {@link Callable} interface is similar to {@link Runnable} in that
     * both are commands whose instances may be executed by another thread.
     * Unlike the {@link Runnable#run()} method, however, the
     * {@link Callable#call()} method is able to both (1) return a result and
     * (2) throw a checked exception, so it is a bit more powerful than the
     * {@link Runnable} interface in that regard (in fact, this is mostly why
     * the {@link Callable} interface was introduced in Java 5.0, as described
     * in this blog post:
     * https://blogs.oracle.com/CoreJavaTechTips/entry/get_netbeans_6).
     */
    private static class CountCallable implements Callable<Integer> {
        private final CountWordTask mTask;
        private final WorkerConfig mWorker;

        public CountCallable(CountWordTask task, WorkerConfig worker) {
            mTask = task;
            mWorker = worker;
        }

        /**
         * Returns the {@link WorkerConfig} object that provides information
         * about the worker that this callable task is responsible for
         * interacting with.
         */
        public WorkerConfig getWorker() {
            return mWorker;
        }

        @Override
        public Integer call() throws Exception {
            Socket socket = null;
            try {
                // Establish a connection with the worker server.
                socket = new Socket(mWorker.getHost(), mWorker.getPort());

                // Create the ObjectOutputStream and write the WorkerCommand
                // over the network to be read and executed by a WorkerServer.
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(new ExecuteTaskCommand<Integer>(mTask, mWorker.getFile()));

                // Note that we instantiate the ObjectInputStream AFTER writing
                // the object over the objectOutputStream. Initializing it
                // immediately after initializing the ObjectOutputStream (but
                // before writing the object) will cause the entire program to
                // block, as described in this StackOverflow answer:
                // http://stackoverflow.com/q/5658089/844882:
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                // Read and return the worker's final result.
                return (Integer) in.readObject();
            } catch (Exception e) {
                // Catch, log, and re-throw the exception. Always make sure you
                // log your exceptions, or else debugging your code will be a
                // nightmare!
                Log.e(TAG, "Warning! Received exception while interacting with worker.", e);
                throw e;
            } finally {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    // Ignore because we're about to exit anyway.
                }
            }
        }
    }

    public static void main(String[] args) {
        List<WorkerConfig> workers = Arrays.asList(WorkerConfig.values());
        new CountWordClient(new CountWordTask("just"), workers).start(); // 75
        // new CountWordClient(new CountWordTask("gregor"), workers).start(); // 298
        // new CountWordClient(new CountWordTask("the"), workers).start(); // 2654
    }

}