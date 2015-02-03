package edu.cmu.cs.cs214.hw6.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import edu.cmu.cs.cs214.hw6.MasterServer;
import edu.cmu.cs.cs214.hw6.Partition;
import edu.cmu.cs.cs214.hw6.WorkerInfo;
import edu.cmu.cs.cs214.hw6.WorkerServer;
import edu.cmu.cs.cs214.hw6.plugin.wordcount.WordCountClient;
import edu.cmu.cs.cs214.hw6.plugin.wordprefix.WordPrefixClient;

/**
 * Configuration utility class used to load system properties and parse command
 * line arguments.
 *
 * DO NOT MODIFY OR CALL ANY METHODS IN THIS CLASS.
 */
public final class StaffUtils {
    private static final String MASTER_PROPERTIES = "master.properties";
    private static final String MASTER_HOST = "master.host";
    private static final String MASTER_PORT = "master.port";

    private static final String WORKERS_PROPERTIES = "workers.properties";
    private static final String WORKER_HOST = "%s.host";
    private static final String WORKER_PORT = "%s.port";
    private static final String WORKER_PARTITIONS = "%s.partitions";

    private static String sMasterHost = null;
    private static int sMasterPort = -1;
    private static List<WorkerInfo> sWorkers = null;

    static {
        initProperties();
    }

    private static void initProperties() {
        try {
            Properties masterProps = new Properties();
            FileInputStream masterIn = new FileInputStream(MASTER_PROPERTIES);
            masterProps.load(masterIn);
            masterIn.close();

            Properties workerProps = new Properties();
            FileInputStream workerIn = new FileInputStream(WORKERS_PROPERTIES);
            workerProps.load(workerIn);
            workerIn.close();

            sMasterHost = masterProps.getProperty(MASTER_HOST);
            sMasterPort = Integer.parseInt(masterProps.getProperty(MASTER_PORT));
            sWorkers = parseWorkers(workerProps);
        } catch (Exception e) {
            // If there was an error parsing the configuration files, we will
            // assume that command line arguments were specified instead. An
            // exception will be thrown later on if this is not the case.
            sMasterHost = null;
            sMasterPort = -1;
            sWorkers = null;
        }
    }

    /**
     * Makes a {@link MasterServer} using the configurations specified in the
     * system property files.
     */
    private static MasterServer makeMasterServer() {
        if (sMasterPort < 0) {
            throw new IllegalStateException("Error loading master port from properties.");
        }
        if (sWorkers == null) {
            throw new IllegalStateException("Error loading worker info from properties.");
        }
        return new MasterServer(sMasterPort, sWorkers);
    }

    /**
     * Makes a {@link MasterServer} using user-specified command-line arguments.
     */
    public static MasterServer makeMasterServer(String[] args) {
        if (args.length == 0) {
            // Default to system property files if no command line arguments
            // were specified.
            return makeMasterServer();
        }

        if (args.length < 5 || args.length % 4 != 1) {
            String usage = "Usage: java %s [masterPort] [workerName workerHost workerPort 1,2,3]...%n";
            System.err.format(usage, MasterServer.class.getSimpleName());
            System.exit(1);
            return null;
        }

        int numWorkers = (args.length - 1) / 4;
        List<WorkerInfo> workers = new ArrayList<WorkerInfo>();
        for (int i = 0; i < numWorkers; i++) {
            String name = args[i * 4 + 1];
            String host = args[i * 4 + 2];
            String port = args[i * 4 + 3];
            String partitions = args[i * 4 + 4];
            try {
                workers.add(parseWorkerInfo(name, host, port, partitions));
            } catch (NumberFormatException e) {
                System.err.format("Invalid port number: %s%n", port);
                System.exit(1);
                return null;
            }
        }
        return new MasterServer(parsePort(args[0]), Collections.unmodifiableList(workers));
    }

    /**
     * Makes a list of {@link WorkerServer}s using the configurations specified
     * in the system property files.
     */
    private static List<WorkerServer> makeWorkerServers() {
        if (sWorkers == null) {
            throw new IllegalStateException("Error loading worker info from properties.");
        }
        List<WorkerServer> servers = new ArrayList<WorkerServer>();
        for (WorkerInfo worker : sWorkers) {
            servers.add(new WorkerServer(worker.getPort()));
        }
        return Collections.unmodifiableList(servers);
    }

    /**
     * Makes a list of {@link WorkerServer}s from user-specified command-line
     * arguments.
     */
    public static List<WorkerServer> makeWorkerServers(String[] args) {
        if (args.length == 0) {
            // Default to system property files if no command line arguments
            // were specified.
            return makeWorkerServers();
        }

        if (args.length < 1) {
            System.err.format("Usage: java %s workerPort...%n", WorkerServer.class.getSimpleName());
            System.exit(1);
            return null;
        }

        List<WorkerServer> servers = new ArrayList<WorkerServer>();
        for (int i = 0; i < args.length; i++) {
            servers.add(new WorkerServer(parsePort(args[i])));
        }
        return Collections.unmodifiableList(servers);
    }

    /**
     * Makes a {@link WordCountClient} using the configurations specified in the
     * system property files.
     */
    private static WordCountClient makeWordCountClient() {
        if (sMasterHost == null) {
            throw new IllegalStateException("Error loading master host from properties.");
        }
        if (sMasterPort < 0) {
            throw new IllegalStateException("Error loading master port from properties.");
        }
        return new WordCountClient(sMasterHost, sMasterPort);
    }

    /**
     * Makes a {@link WordCountClient} using user-specified command-line
     * arguments.
     */
    public static WordCountClient makeWordCountClient(String[] args) {
        if (args.length == 0) {
            // Default to system property files if no command line arguments
            // were specified.
            return makeWordCountClient();
        }

        if (args.length != 2) {
            System.err.format("Usage: java %s masterHost masterPort%n",
                    WordCountClient.class.getSimpleName());
            System.exit(1);
            return null;
        }
        return new WordCountClient(args[0], parsePort(args[1]));
    }

    /**
     * Makes a {@link WordPrefixClient} using the configurations specified in
     * the system property files.
     */
    private static WordPrefixClient makeWordPrefixClient() {
        if (sMasterHost == null) {
            throw new IllegalStateException("Error loading master host from properties.");
        }
        if (sMasterPort < 0) {
            throw new IllegalStateException("Error loading master port from properties.");
        }
        return new WordPrefixClient(sMasterHost, sMasterPort);
    }

    /**
     * Makes a {@link WordPrefixClient} using user-specified command-line
     * arguments.
     */
    public static WordPrefixClient makeWordPrefixClient(String[] args) {
        if (args.length == 0) {
            // Default to system property files if no command line arguments
            // were specified.
            return makeWordPrefixClient();
        }

        if (args.length != 2) {
            System.err.format("Usage: java %s masterHost masterPort%n",
                    WordPrefixClient.class.getSimpleName());
            System.exit(1);
            return null;
        }
        return new WordPrefixClient(args[0], parsePort(args[1]));
    }

    private static List<WorkerInfo> parseWorkers(Properties props) throws Exception {
        Set<String> workerNames = new TreeSet<String>();
        for (String key : props.stringPropertyNames()) {
            workerNames.add(key.split("\\.")[0]);
        }
        List<WorkerInfo> workers = new ArrayList<WorkerInfo>();
        for (String workerName : workerNames) {
            String host = props.getProperty(String.format(WORKER_HOST, workerName));
            String port = props.getProperty(String.format(WORKER_PORT, workerName));
            String partitions = props.getProperty(String.format(WORKER_PARTITIONS, workerName));
            workers.add(parseWorkerInfo(workerName, host, port, partitions));
        }
        return Collections.unmodifiableList(workers);
    }

    private static WorkerInfo parseWorkerInfo(String workerName, String host, String port,
            String partitions) throws NumberFormatException {
        return new WorkerInfo(workerName, host, Integer.parseInt(port), parsePartitions(partitions,
                workerName));
    }

    private static List<Partition> parsePartitions(String partitions, String workerName) {
        String[] partitionNames = partitions.split(",");
        List<Partition> results = new ArrayList<Partition>();
        for (int i = 0; i < partitionNames.length; i++) {
            results.add(new Partition(partitionNames[i], workerName));
        }
        return Collections.unmodifiableList(results);
    }

    private static int parsePort(String port) {
        try {
            return Integer.parseInt(port);
        } catch (NumberFormatException e) {
            System.err.format("Invalid port number: %s%n", port);
            System.exit(1);
            return -1;
        }
    }

    private StaffUtils() {
        // This class should not be instantiated.
    }

}
