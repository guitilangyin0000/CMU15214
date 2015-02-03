package edu.cmu.cs.cs214.rec05;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map.Entry;

public class DocumentAnalyzer implements ITextAnalyzer {

	private Hashtable<String, Integer> freqTable;
	private String content;
	private String filename;
	private int totalWord;
	private String mostFrequentWord;
	
	public DocumentAnalyzer(String filename) {
		this.filename = filename;
		content = null;
		totalWord = 0;
		freqTable = new Hashtable<String, Integer>();
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(filename);
			DataInput dataInput = new DataInputStream(fileInput);
			content = dataInput.readLine();
		} catch (FileNotFoundException e) {
			System.out.println("File :\"" + filename + "\" does not exist!");
		} catch (IOException e) {
			System.out.println("Cannot open file: \"" + filename + "\"!");
		}

		String[] words = content.split("[ |,|.|:|;]");
		int maxFrequency = 1;
		for (String word : words) {
			if (word.length() == 0) continue;
			String lowerWord = word.toLowerCase();
			if (freqTable.containsKey(lowerWord)) {
				int newFrequency = freqTable.get(lowerWord) + 1;
				if (maxFrequency < newFrequency) {
					maxFrequency = newFrequency;
					mostFrequentWord = lowerWord;
				}
				freqTable.put(lowerWord, newFrequency);
			} else {
				freqTable.put(lowerWord, 1);
			}
			totalWord++;
		}
	}

	@Override
	public int wordCount(String str) {

		String word = str.toLowerCase();
		if (freqTable.containsKey(word)) {
			return freqTable.get(word);
		}
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DocumentAnalyzer)) {
			return false;
		}
		DocumentAnalyzer doc = (DocumentAnalyzer) obj;
		return filename.equals(doc.filename) || content.equals(doc.content);
	}

	@Override
	public int totalWordCount() {
		return totalWord;
	}

	@Override
	public String mostFrequentWord() {
		return mostFrequentWord;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (Entry<String, Integer> entry : freqTable.entrySet()) {
			str += entry.getKey();
			str += " ";
			str += entry.getValue();
			str += "\n";
		}
		return str;
	}
	public static void main(String[] args) {

		DocumentAnalyzer doc = new DocumentAnalyzer("sample.txt");

		String word = doc.mostFrequentWord();
		System.out.println("Most Frequent Word: \"" + word +"\"");
		System.out.println("Frequency: " + doc.wordCount(word));
		
		System.out.println("Print Word Searching Result:\n" + doc);
	}
}
