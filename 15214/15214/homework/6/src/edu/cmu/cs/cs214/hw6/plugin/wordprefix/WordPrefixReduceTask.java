package edu.cmu.cs.cs214.hw6.plugin.wordprefix;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import edu.cmu.cs.cs214.hw6.Emitter;
import edu.cmu.cs.cs214.hw6.ReduceTask;

/**
 * The reduce task for a word-prefix map/reduce computation.
 */
public class WordPrefixReduceTask implements ReduceTask {
	private static final long serialVersionUID = 6763871961687287020L;

	@Override
	public void execute(String key, Iterator<String> values, Emitter emitter)
			throws IOException {
		// For one specified prefix key, use HashMap to calculate the number
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		while (values.hasNext()) {
			String value = values.next();
			if (map.containsKey(value)) {
				map.put(value, map.get(value) + 1);
			} else {
				map.put(value, 1);
			}
		}
		// Find the max number for the value by iterator
		int maxNum = Integer.MIN_VALUE;
		String maxString = "";
		Iterator<Entry<String, Integer>> iterator = map.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, Integer> entry = iterator.next();
			int number = entry.getValue();
			if (number > maxNum) {
				maxNum = number;
				maxString = entry.getKey();
			}
		}
		emitter.emit(key, maxString);

	}

}
