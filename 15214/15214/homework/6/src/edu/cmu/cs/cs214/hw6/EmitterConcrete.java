package edu.cmu.cs.cs214.hw6;
/**
 * EmitterConcrete class which will write key value pairs to 
 * file with specified path, if the file exists, just append
 * content at the end of the file.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class EmitterConcrete implements Emitter{
	
	private final PrintWriter handler;

	public EmitterConcrete(String filePath) throws FileNotFoundException{
		File file = new File(filePath);
		// The second append para should be set to be true, or
		// we will overwrite the file just created
		handler = new PrintWriter(new FileOutputStream(file), true);
	}
	
	@Override
	public void close() throws IOException {
		handler.close();
		
	}

	@Override
	public void emit(String key, String value) throws IOException {
		handler.write(key + '\t' + value + '\n');
	}

}
