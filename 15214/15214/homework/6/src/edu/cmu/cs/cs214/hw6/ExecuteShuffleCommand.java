package edu.cmu.cs.cs214.hw6;

/**
 * ExecuteShuffleCommand class, which will let the worker handle 
 * the intermediate files of its local file system and split the 
 * the intermediate files into several shuffle files according
 * to the hash values of all the reduce workers, for example, if 
 * there are 2 intermediate files mapresult_1.txt and mapresult_2.txt
 * in the file system of worker 2 and there are 4 reduce workers,
 * then after shuffling, there should be 4 files in that folder:
 * shuffleresult_0.txt, shuffleresult_1.txt, shuffleresult_2.txt,
 * shuffleresult_3.txt. Then the worker 2 can use socket to send
 * shuffle results to different workers according to their hash codes. 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import edu.cmu.cs.cs214.hw6.util.Log;

public class ExecuteShuffleCommand extends WorkerCommand{

	private static final long serialVersionUID = 5043516913247172745L;
	private static final String TAG = "Execute Shuffle Command";
	private final ArrayList<String> paths;
	private final String baseDir;
	private final int workerNum;
	
	public ExecuteShuffleCommand(String baseDir, ArrayList<String> paths, int workerNum){
		this.baseDir = baseDir;
		this.paths = paths;
		this.workerNum = workerNum;
	}
	
	@Override
	public void run() {
		ArrayList<String> shufflePaths = new ArrayList<String>();
		ArrayList<Emitter> emitters = new ArrayList<Emitter>();
		for (int i = 0; i < workerNum; i++){
			String shufflePath = baseDir + '/' + "shuffle_" + i + ".txt";
			shufflePaths.add(shufflePath);
		}
		Socket socket = null;
		ObjectOutputStream out = null;
		socket = getSocket();
		try {
			for (int i = 0; i < shufflePaths.size(); i++){
				Emitter emitter = new EmitterConcrete(shufflePaths.get(i));
				emitters.add(emitter);
			}
			
			for (String path: paths){
				Scanner scanner = new Scanner(new File(path));
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine().trim();
					if (line.equals("")) {
						continue;
					}
					String[] info_list = line.split("\t");
					String key = info_list[0];
					String value = info_list[1];
					int hashcode = Math.abs(key.hashCode() % workerNum);
					emitters.get(hashcode).emit(key, value);
				}
				scanner.close();	
			}
			out = new ObjectOutputStream(socket.getOutputStream());
			ArrayList<String> results = new ArrayList<String>();
			for (int j = 0; j < workerNum; j++){
				results.add(j + "#" + shufflePaths.get(j));
			}
			out.writeObject(results);
		} catch (FileNotFoundException e) {
			Log.e(TAG, "Fail to create or find the file!", e);
		} catch (IOException e) {
			Log.e(TAG, "I/O exception when executing the shuffle task!", e);
		} finally{
			try{
				for (int i = 0; i < emitters.size(); i++){
					if (emitters.get(i) != null){
						emitters.get(i).close();
					}
				}	
				if (out != null){
					out.close();
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
