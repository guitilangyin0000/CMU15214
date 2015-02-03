package edu.cmu.cs.cs214.concurrency;

public class Getter implements Runnable {
	StringConverter sc;

	public Getter(StringConverter sc) {
		this.sc = sc;
	}

	public void run() {
		while (true) {
			System.out.println(sc.get());
		}
	}
}
