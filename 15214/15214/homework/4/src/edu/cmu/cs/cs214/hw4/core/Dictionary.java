package edu.cmu.cs.cs214.hw4.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Dictionary {

	private HashSet<String> dic;

	public Dictionary() {
		dic = new HashSet<String>();
	}

	public void initial(String path) {
		try {
			Scanner scanner = new Scanner(new File(path));
			while (scanner.hasNext()) {
				dic.add(scanner.next());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/* check whether the string is in the dictionary*/
	public Boolean isIn(String string){
		return dic.contains(string);
	}

}
