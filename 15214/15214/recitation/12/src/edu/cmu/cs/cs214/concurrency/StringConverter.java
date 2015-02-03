package edu.cmu.cs.cs214.concurrency;

public class StringConverter {
	private Object o;

	public synchronized void set(Object o) {
		this.o = o;
	}

	public synchronized String get() {
		if (o == null)
			return "null";
		return o.toString();
	}
}
