package edu.cmu.cs.cs214.rec13;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import edu.cmu.cs.cs214.rec13.tasks.Task;
import edu.cmu.cs.cs214.rec13.util.Log;

/**
 * A {@link WorkerCommand} that executes a {@link Task} and sends the calculated
 * result back to the client.
 *
 * Note that the generic type <code>T</code> must extend {@link Serializable}
 * since we will be writing it over the network back to the client using an
 * {@link ObjectOutputStream}.
 */
public class ExecuteTaskCommand<T extends Serializable> extends WorkerCommand {
    private static final long serialVersionUID = -5314076333098679665L;
    private static final String TAG = "ExecuteTaskCommand";

    private final Task<T> mTask;
    private final String mFileName;

    public ExecuteTaskCommand(Task<T> task, String fileName) {
        mTask = task;
        mFileName = fileName;
    }

    @Override
    public void run() {
        // Get the socket to use to send results back to the client. Note that
        // the WorkerServer will close this socket for us, so we don't need to
        // worry about that.
        Socket socket = getSocket();

        FileInputStream in = null;
        try {
            // Opens a FileInputStream for the specified file, execute the task,
            // and close the input stream once we've calculated the result.
            in = new FileInputStream(mFileName);
            T result = mTask.execute(in);
            in.close();

            // Open an ObjectOutputStream to use to communicate with the client
            // that sent this command, and write the result back to the client.
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(result);
        } catch (IOException e) {
            Log.e(TAG, "I/O error while executing task.", e);
        }
    }

}
