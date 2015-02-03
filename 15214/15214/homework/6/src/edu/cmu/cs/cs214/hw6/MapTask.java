package edu.cmu.cs.cs214.hw6;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * A plug-in interface for the map portion of a map/reduce computation.
 *
 * DO NOT MODIFY THIS INTERFACE.
 */
public interface MapTask extends Serializable {

    /**
     * Executes the map portion of a map/reduce computation over the contents of
     * an input file, and emits the intermediate key/value pairs to an output
     * file on the disk.
     *
     * @param in The {@link InputStream} to use to read the data contents.
     * @param emitter The {@link Emitter} to use to write the intermediate
     *        key/value pair results.
     *
     * @throws IOException If an I/O error occurs (to be handled by the
     *         framework).
     */
    void execute(InputStream in, Emitter emitter) throws IOException;

}
