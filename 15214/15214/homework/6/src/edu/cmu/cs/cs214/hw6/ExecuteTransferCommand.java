package edu.cmu.cs.cs214.hw6;

/**
 * ExecuteTransferCommand class, which will transfer keypairs among 
 * workers by sockets, so workers will receive keypairs from other
 * workers in order to start the reduce task.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import edu.cmu.cs.cs214.hw6.util.KeyValuePair;
import edu.cmu.cs.cs214.hw6.util.Log;

public class ExecuteTransferCommand extends WorkerCommand {

	private static final long serialVersionUID = 8883674853631384827L;
	private static final String TAG = "Execute Transfer Command";
	private final String path;
	private final WorkerInfo worker;

	public ExecuteTransferCommand(String path, WorkerInfo worker) {
		Log.i(TAG, "Set up transfer task");
		this.path = path;
		this.worker = worker;
	}

	public WorkerInfo getWorker() {
		return worker;
	}

	@Override
	public void run() {
		Socket socket = null;
		ObjectOutputStream out = null;
		socket = getSocket();
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			Scanner scanner = new Scanner(new File(path));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.equals("")) {
					continue;
				}
				String[] info_list = line.split("\t");
				String key = info_list[0];
				String value = info_list[1];
				out.writeObject(new KeyValuePair(key, value));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			Log.e(TAG, "Fail to create or find the file!", e);
		} catch (IOException e) {
			Log.e(TAG, "I/O exception when executing the transfer task!", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				Log.e(TAG, "I/O exception when closing stream!");
			}
		}
	}

}
