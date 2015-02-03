package edu.cmu.cs.cs214.hw6;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * Defines a command to be executed by a remote {@link WorkerServer}. A client
 * server can write a {@link WorkerCommand} object over the network to a
 * {@link WorkerServer} with the expectation that the {@link WorkerServer} will,
 * (1) set the command's socket for the command to use to communicate results
 * back to the client server, and (2) execute the command by calling its
 * {@link #run()} method.
 *
 * Refer to recitation 13 for an example of how this class can be used.
 */
public abstract class WorkerCommand implements Runnable, Serializable {
    private static final long serialVersionUID = 3489636953747545414L;

    /**
     * Note that we declare the {@link Socket} as <code>transient</code> to
     * indicate that the field will not be serialized (that is, it will be
     * ignored when Java converts this object into a stream of bytes to be
     * written over an {@link ObjectOutputStream}).
     */
    private transient Socket mSocket;

    /**
     * Set the socket to use to communicate with the client that sent this
     * command.
     */
    protected final void setSocket(Socket socket) {
        mSocket = socket;
    }

    /**
     * Get the socket to use to communicate with the client that sent this
     * command.
     */
    protected final Socket getSocket() {
        return mSocket;
    }

}
