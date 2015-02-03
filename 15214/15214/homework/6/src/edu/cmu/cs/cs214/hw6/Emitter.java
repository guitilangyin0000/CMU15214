package edu.cmu.cs.cs214.hw6;

import java.io.Closeable;
import java.io.IOException;

/**
 * Provides an interface for {@link MapTask}s and {@link ReduceTask}s to output
 * a key/value pair to the framework.
 *
 * For example, the {@link MapTask#execute(FileInputStream, Emitter emitter)}
 * method should be passed an {@link Emitter} that is capable of emitting key
 * value pairs to an intermediary results file. Don't forget to close the
 * Emitter once you've finished using it by calling the {@link Emitter#close()}
 * method!
 */
public interface Emitter extends Closeable {

    /**
     * Emits a key/value pair. You must write the pair in a human-readable format.
     *
     * @param key The key to output to the framework.
     * @param value The value to output to the framework.
     */
    void emit(String key, String value) throws IOException;

}
