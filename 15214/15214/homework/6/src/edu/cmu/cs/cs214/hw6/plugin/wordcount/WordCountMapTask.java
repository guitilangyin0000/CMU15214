package edu.cmu.cs.cs214.hw6.plugin.wordcount;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import edu.cmu.cs.cs214.hw6.Emitter;
import edu.cmu.cs.cs214.hw6.MapTask;

/**
 * The map task for a word-counting map/reduce computation.
 *
 * For each occurrence of a word in a corpus of data, this map task will emit a
 * key/value pair to a file on the disk, to be later accessed during the reduce
 * portion of the computation.
 */
public class WordCountMapTask implements MapTask {
    private static final long serialVersionUID = 8180942222152099921L;

    @Override
    public void execute(InputStream in, Emitter emitter) throws IOException {
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\W+");
        while (scanner.hasNext()) {
            String key = scanner.next().trim().toLowerCase();
            emitter.emit(key, "1");
        }
        scanner.close();
    }

}
