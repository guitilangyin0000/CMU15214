package edu.cmu.cs.cs214.hw6;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.cmu.cs.cs214.hw6.util.WorkerStorage;

/**
 * A {@link Partition} represents a collection of files that are stored on a
 * particular worker machine in the map/reduce framework's distributed storage
 * system. This class implements the {@link Iterable} interface, and therefore
 * can be used within an enhanced for-loop like so:
 *
 * <pre>
 * Partition partition = ...;
 * for (File file : partition) {
 *    ...
 * }
 * </pre>
 *
 * Note that the {@link MasterServer} must make some important decisions about
 * how the map/reduce task should be distributed across the available workers
 * depending on which workers store which partitions. For example, consider a
 * system that consists of 3 worker servers (W1, W2, W3) and 4 partitions (P1,
 * P2, P3, P4), with each worker storing the following partitions on their
 * disks:
 *
 * <ul>
 * <li>W1 stores P1, P2</li>
 * <li>W2 stores P2, P3</li>
 * <li>W3 stores P3, P4</li>
 * </ul>
 *
 * The {@link MasterServer} will need to take this information into account when
 * deciding how it should distribute the {@link MapTask} and {@link ReduceTask}
 * across the available workers in the system. For example, selecting {W1, W2}
 * as the set of map workers to execute a given {@link MapTask} would lead to an
 * incorrect final result (since neither W1 nor W2 stores P4). Similarly, if W2
 * were to crash during the computation of a map/reduce task, the
 * {@link MasterServer} should still be able to compute the correct final result
 * (since W1 stores P2 and W3 stores P3).
 */
public class Partition implements Iterable<File>, Serializable {
    private static final long serialVersionUID = 2907750670787748717L;

    private final String mPartitionName;
    private final String mWorkerName;

    public Partition(String partitionName, String workerName) {
        mPartitionName = partitionName;
        mWorkerName = workerName;
    }

    public String getPartitionName() {
        return mPartitionName;
    }

    public String getWorkerName() {
        return mWorkerName;
    }

    @Override
    public Iterator<File> iterator() {
        File workerDataDir = new File(WorkerStorage.getDataDirectory(mWorkerName));
        File partitionDir = new File(workerDataDir, mPartitionName);

        if (!partitionDir.exists() || !partitionDir.isDirectory()) {
            throw new IllegalArgumentException(String.format(
                    "Partition '%s' not found for worker '%s'.", mPartitionName, mWorkerName));
        } else if (!partitionDir.canRead()) {
            throw new IllegalStateException("Cannot read directory: " + partitionDir.getPath());
        }

        List<File> results = new ArrayList<File>();
        File[] allFiles = partitionDir.listFiles();
        for (File f : allFiles) {
            if (f.isFile() && f.canRead()) {
                results.add(f);
            }
        }
        return results.iterator();
    }

    @Override
    public String toString() {
        return String.format("<%s: partitionName=%s>", Partition.class.getSimpleName(),
                mPartitionName);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (mPartitionName == null ? 0 : mPartitionName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partition)) {
            return false;
        }
        Partition other = (Partition) o;
        return equals(mPartitionName, other.mPartitionName);
    }

    private static boolean equals(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

}
