package edu.cmu.cs.cs214.rec05;

public interface ITextAnalyzer {

	public int totalWordCount();
	
	public String mostFrequentWord();
	
	public int wordCount(String word);
	
}
