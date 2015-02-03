package edu.cmu.cs.cs214.rec13;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import edu.cmu.cs.cs214.rec13.tasks.Task;

/**
 * A simple {@link WorkerCommand} that executes a {@link Task} and sends the
 * calculated result back to the client.
 */
public class ExecuteTaskCommand<T extends Serializable> extends WorkerCommand {
    private static final long serialVersionUID = -5314076333098679665L;

    private final Task<T> mTask;
    private final String mFileName;

    public ExecuteTaskCommand(Task<T> task, String fileName) {
        mTask = task;
        mFileName = fileName;
    }

    @Override
    public void run() {
        // Get the socket to use to send results back to the client.
        // (1) Open a FileInputStream for mFileName, execute the task,
        // and obtain the task's final result. Don't forget to close the
        // FileInputStream when you're finished using it!
        Socket socket = getSocket();
        T result = null;
        try {
			FileInputStream in = new FileInputStream(mFileName);
			result = mTask.execute(in);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
        // (2) Send the final result back to the client.
        try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(result);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        

    }

}
