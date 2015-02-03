package edu.cmu.cs.cs214.rec12.runnable;

import edu.cmu.cs.cs214.rec12.queue.Queue;

public class Producer implements Runnable {

	private Queue<Integer> q;
	private int n;

	public Producer(Queue<Integer> q, int n) {
		this.q = q;
		this.n = n;
	}

	@Override
	public void run() {
		for(int i = 0; i < n; i++) {
			q.enqueue(i);
		}
	}
}
