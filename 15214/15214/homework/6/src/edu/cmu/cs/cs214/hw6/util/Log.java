package edu.cmu.cs.cs214.hw6.util;

/**
 * A simple utility class for logging.
 *
 * You may modify this class if you wish.
 */
public final class Log {

    /**
     * Prints an error log message to the console.
     */
    public static void e(String tag, String message) {
        System.err.format("%s: %s%n", tag, message);
        System.err.flush();
    }

    /**
     * Prints an error log message to the console, logging the {@link Throwable}
     * that caused it as well.
     */
    public static void e(String tag, String message, Throwable throwable) {
        System.err.format("%s: %s%n", tag, message);
        System.err.flush();
        throwable.printStackTrace();
    }

    /**
     * Prints an informational log message to the console.
     */
    public static void i(String tag, String message) {
        System.out.format("%s: %s%n", tag, message);
        System.out.flush();
    }

    private Log() {
        // This class should not be instantiated.
    }

}
