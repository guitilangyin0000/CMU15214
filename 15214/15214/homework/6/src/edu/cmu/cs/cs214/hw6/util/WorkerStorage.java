package edu.cmu.cs.cs214.hw6.util;

import java.io.File;

/**
 * Provides quick-and-easy access to a worker's data storage directories.
 */
public final class WorkerStorage {

    private static final String WORKER_STORAGE_DIR = "worker_storage";
    private static final String DATA_DIR = "data";
    private static final String INTERMEDIATE_RESULTS_DIR = "intermediate_results";
    private static final String FINAL_RESULTS_DIR = "final_results";

    /**
     * Returns the pathname containing a worker's subset of data.
     */
    public static String getDataDirectory(String workerName) {
        return combineFilePaths(WORKER_STORAGE_DIR, workerName, DATA_DIR);
    }

    /**
     * Returns the directory for a map worker to store its intermediate results.
     */
    public static String getIntermediateResultsDirectory(String workerName) {
        return combineFilePaths(WORKER_STORAGE_DIR, workerName, INTERMEDIATE_RESULTS_DIR);
    }

    /**
     * Returns the directory for a reduce workers to store its final results.
     */
    public static String getFinalResultsDirectory(String workerName) {
        return combineFilePaths(WORKER_STORAGE_DIR, workerName, FINAL_RESULTS_DIR);
    }

    /**
     * Combines three directory names (grandparent, parent, and child) and
     * returns a single file path.
     */
    private static String combineFilePaths(String p1, String p2, String p3) {
        return new File(new File(new File(p1), p2), p3).getPath();
    }

    private WorkerStorage() {
        // This class should not be instantiated.
    }

}
