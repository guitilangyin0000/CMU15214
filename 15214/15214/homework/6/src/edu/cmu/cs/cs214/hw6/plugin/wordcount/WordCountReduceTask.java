package edu.cmu.cs.cs214.hw6.plugin.wordcount;

import java.io.IOException;
import java.util.Iterator;

import edu.cmu.cs.cs214.hw6.Emitter;
import edu.cmu.cs.cs214.hw6.ReduceTask;

/**
 * The reduce task for a word-counting map/reduce computation.
 *
 * For each distinct word in a corpus of data, this reduce task will sum the
 * number of occurrences of each word, and will emit a final key/value pair (the
 * key being the word, and the value being the number of times that word
 * occurred in the corpus of data).
 */
public class WordCountReduceTask implements ReduceTask {
    private static final long serialVersionUID = 4940788163371894920L;

    @Override
    public void execute(String key, Iterator<String> values, Emitter emitter) throws IOException {
        int sum = 0;
        while (values.hasNext()) {
            sum += Integer.parseInt(values.next());
        }
        emitter.emit(key, Integer.toString(sum));
    }
    
}
