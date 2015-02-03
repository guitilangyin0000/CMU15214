package edu.cmu.cs.cs214.hw6;
/**
 *  ExecuteMapCommand class, which can serve as a mapper that execute 
 *  the map task for specified purpose.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.cmu.cs.cs214.hw6.util.Log;
import edu.cmu.cs.cs214.hw6.util.WorkerStorage;

public class ExecuteMapCommand extends WorkerCommand {

	private static final long serialVersionUID = 1152253527360713015L;
	private static final String TAG = "Execute Map Command";
	private final MapTask mapTask;
	private final Partition partition;

	public ExecuteMapCommand(MapTask mapTask, Partition partition) {
		this.mapTask = mapTask;
		this.partition = partition;
	}

	// Method that we can get the current time info
	private String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		return sdf.format(date);
	}

	@Override
	public void run() {
		String intermediateDir = WorkerStorage
				.getIntermediateResultsDirectory(partition.getWorkerName());
		String fileName = "partition" + partition.getPartitionName() + '_' + getCurrentTime()
				+ ".txt";
		String intermediatePath = intermediateDir + "/" + fileName;
		Log.i(TAG, "Intermediate path is: " + intermediatePath);
		Socket socket = null;
		Emitter emitter = null;
		FileInputStream in = null;
		ObjectOutputStream out = null;
		socket = getSocket();
		try {
			emitter = new EmitterConcrete(intermediatePath);
			for (File file: partition){
				in = new FileInputStream(file);
				mapTask.execute(in, emitter);
			}
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(intermediatePath);
		} catch (FileNotFoundException e) {
			Log.e(TAG, "Fail to create or find the file!", e);
		} catch (IOException e) {
			Log.e(TAG, "I/O exception when executing the map task!", e);
		} finally{
			try{
				if (emitter != null){
					emitter.close();
				}
				if (out != null){
					out.close();
				}
				if (in != null){
					in.close();
				}
				if (socket != null){
					socket.close();
				}
			}catch (IOException e){
				Log.e(TAG, "I/O exception when closing stream!");
			}
		}
		

	}

}
