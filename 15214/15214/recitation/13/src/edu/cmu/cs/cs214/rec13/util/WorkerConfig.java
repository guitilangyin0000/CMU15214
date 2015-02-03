package edu.cmu.cs.cs214.rec13.util;

/**
 * Simple configuration class that describes the host, port, and file of each of the
 * workers.
 */
public enum WorkerConfig {
    WORKER_ONE("localhost", 15214, "assets/pg1952.txt"),
    WORKER_TWO("localhost", 15215,"assets/pg5200.txt"),
    WORKER_THREE("localhost", 15216, "assets/pg17405.txt");

    private final String mHost;
    private final int mPort;
    private final String mFile;

    private WorkerConfig(String host, int port, String file) {
        mHost = host;
        mPort = port;
        mFile = file;
    }

    public String getHost() {
        return mHost;
    }

    public int getPort() {
        return mPort;
    }

    public String getFile() {
        return mFile;
    }

}
