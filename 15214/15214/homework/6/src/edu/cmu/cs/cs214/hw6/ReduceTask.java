package edu.cmu.cs.cs214.hw6;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

/**
 * A plug-in interface for the reduce portion of a map/reduce computation.
 *
 * DO NOT MODIFY THIS INTERFACE.
 */
public interface ReduceTask extends Serializable {

    /**
     * Given a key and an {@link Iterator} over all of the values for that key,
     * computes and emits the final key/value pair for the specified key.
     *
     * @param key The key currently being reduced.
     * @param values An {@link Iterator} over all of the values for the
     *        specified key.
     * @param emitter The {@link Emitter} to use to emit the final key/value
     *        pair for the specified key.
     *
     * @throws IOException If an I/O error occurs (to be handled by the
     *         framework).
     */
    void execute(String key, Iterator<String> values, Emitter emitter) throws IOException;

}
