package edu.cmu.cs.cs214.concurrency;

import java.util.Random;

public class Setter implements Runnable {
	StringConverter sc;

	public Setter(StringConverter sc) {
		this.sc = sc;
	}

	@Override
	public void run() {
		Random rand = new Random();
		while (true) {
			if (rand.nextDouble() < 0.5) {
				sc.set(null);
			} else {
				sc.set("Hello!");
			}
		}
	}

	public static void main(String[] args) {
		StringConverter sc = new StringConverter();
		Getter getter = new Getter(sc);
		Setter setter = new Setter(sc);

		new Thread(getter).start();
		new Thread(setter).start();
	}
}
