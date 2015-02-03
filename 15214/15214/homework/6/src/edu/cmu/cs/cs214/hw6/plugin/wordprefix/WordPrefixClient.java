package edu.cmu.cs.cs214.hw6.plugin.wordprefix;

import edu.cmu.cs.cs214.hw6.AbstractClient;
import edu.cmu.cs.cs214.hw6.util.StaffUtils;

/**
 * A client in the map/reduce framework that submits and executes a word prefix
 * task.
 *
 * DO NOT MODIFY THIS CLASS.
 */
public class WordPrefixClient extends AbstractClient {

    public WordPrefixClient(String masterHost, int masterPort) {
        super(masterHost, masterPort);
    }

    @Override
    protected WordPrefixMapTask getMapTask() {
        return new WordPrefixMapTask();
    }

    @Override
    protected WordPrefixReduceTask getReduceTask() {
        return new WordPrefixReduceTask();
    }

    /**
     * Run this class to submit and execute a word prefix task to the framework.
     */
    public static void main(String[] args) {
        StaffUtils.makeWordPrefixClient(args).execute();
    }

}
