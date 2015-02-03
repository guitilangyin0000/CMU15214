package edu.cmu.cs.cs214.rec13.tasks;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Interface that defines a task to be executed.
 */
public interface Task<T> extends Serializable {

    /**
     * Given an input stream to the data's contents, perform a task and return a
     * single value as the result.
     *
     * @param in The input stream pointing to the data contents.
     * @return The final result.
     */
    T execute(InputStream in);

}
