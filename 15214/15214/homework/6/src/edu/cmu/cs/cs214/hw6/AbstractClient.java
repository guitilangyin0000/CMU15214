package edu.cmu.cs.cs214.hw6;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import edu.cmu.cs.cs214.hw6.plugin.wordcount.WordCountClient;
import edu.cmu.cs.cs214.hw6.plugin.wordprefix.WordPrefixClient;
import edu.cmu.cs.cs214.hw6.util.Log;

/**
 * An abstract client class used primarily for code reuse between the
 * {@link WordCountClient} and {@link WordPrefixClient}.
 */
public abstract class AbstractClient {
	private final String mMasterHost;
	private final int mMasterPort;
	private static final String TAG = "Abstract Client";

	/**
	 * The {@link AbstractClient} constructor.
	 *
	 * @param masterHost
	 *            The host name of the {@link MasterServer}.
	 * @param masterPort
	 *            The port that the {@link MasterServer} is listening on.
	 */
	public AbstractClient(String masterHost, int masterPort) {
		mMasterHost = masterHost;
		mMasterPort = masterPort;
	}

	protected abstract MapTask getMapTask();

	protected abstract ReduceTask getReduceTask();

	@SuppressWarnings("unchecked")
	public void execute() {
		final MapTask mapTask = getMapTask();
		final ReduceTask reduceTask = getReduceTask();

		Socket socket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			socket = new Socket(mMasterHost, mMasterPort);
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(mapTask);
			out.writeObject(reduceTask);
			// Avoid of blocking stream
			in = new ObjectInputStream(socket.getInputStream());
			ArrayList<String> paths = (ArrayList<String>) in.readObject();
			for (String path : paths) {
				Log.i(TAG, "The result paths include: " + path);
			}
		} catch (IOException e) {
			Log.e(TAG,
					"IO Exception when setting up the connection with the master server!");
		} catch (ClassNotFoundException e) {
			Log.e(TAG, "The result we get don't match the type!");
		} finally {
			// close all the pipes, the sequence should be out -> in -> socket
			try {
				if (out != null) {
                    out.close();;
				}
				if (in != null){
					in.close();;
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				// Ignore because we're about to exit anyway.
			}
		}

	}

}
