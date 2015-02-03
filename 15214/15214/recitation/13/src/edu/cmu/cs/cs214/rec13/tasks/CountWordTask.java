package edu.cmu.cs.cs214.rec13.tasks;

import java.io.InputStream;
import java.util.Scanner;

/**
 * A simple task that counts the number of occurrences of a particular word.
 */
public class CountWordTask implements Task<Integer> {
    private static final long serialVersionUID = 7484160468220810614L;

    private final String mWord;

    public CountWordTask(String word) {
        mWord = word;
    }

    @Override
    public Integer execute(InputStream in) {
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\W+");
        int count = 0;
        while (scanner.hasNext()) {
            if (mWord.equals(scanner.next().trim().toLowerCase())) {
                count++;
            }
        }
        scanner.close();
        return count;
    }

}
