package edu.cmu.cs.cs214.rec13;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.cmu.cs.cs214.rec13.util.Log;
import edu.cmu.cs.cs214.rec13.util.WorkerConfig;

/**
 * Defines a generic worker server in the distributed system. Each
 * {@link WorkerServer} listens for incoming client connections on a distinct
 * host/port address, and waits for clients to send {@link WorkerCommand}
 * objects for it to execute remotely.
 *
 * @see WorkerCommand
 */
public class WorkerServer extends Thread {
    private static final String TAG = "Worker";

    /** Create at most one thread per available processor on this machine. */
    private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private final int mPort;
    private final ExecutorService mExecutor;

    public WorkerServer(int port) {
        mPort = port;
        mExecutor = Executors.newFixedThreadPool(POOL_SIZE);
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(mPort);
            } catch (IOException e) {
                Log.e(TAG, "Could not open server socket on port " + mPort + ".", e);
                return;
            }

            Log.i(TAG, "Listening for incoming commands on port " + mPort + ".");

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    // Handle the client's request on a background thread, and
                    // immediately begin listening for other incoming client
                    // connections once again.
                    mExecutor.execute(new WorkerCommandHandler(clientSocket));
                } catch (IOException e) {
                    Log.e(TAG, "Error while listening for incoming connections.", e);
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
            mExecutor.shutdown();
        }
    }

    /**
     * Handles a single worker-client request.
     */
    private static class WorkerCommandHandler implements Runnable {
        private final Socket mSocket;

        public WorkerCommandHandler(Socket socket) {
            mSocket = socket;
        }

        @Override
        public void run() {
            try {
                // Note that it will be very important that you understand
                // exactly how the below four lines behave for homework 6!
                ObjectInputStream in = new ObjectInputStream(mSocket.getInputStream());
                WorkerCommand runnable = (WorkerCommand) in.readObject();
                runnable.setSocket(mSocket);
                runnable.run();
            } catch (IOException e) {
                Log.e(TAG, "Connection lost.", e);
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "Received invalid task from client.", e);
            } finally {
                try {
                    mSocket.close();
                } catch (IOException e) {
                    // Ignore because we're about to exit anyway.
                }
            }
        }
    }

    public static void main(String[] args) {
        // Starts up the three workers mentioned in WorkerConfig as separate
        // threads.
        new WorkerServer(WorkerConfig.WORKER_ONE.getPort()).start();
        new WorkerServer(WorkerConfig.WORKER_TWO.getPort()).start();
        new WorkerServer(WorkerConfig.WORKER_THREE.getPort()).start();
    }

}
