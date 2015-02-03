package edu.cmu.cs.cs214.rec12.runnable;

import edu.cmu.cs.cs214.rec12.queue.Queue;

public class Consumer implements Runnable {

	private Queue<Integer> q;
	private int n;

	public Consumer(Queue<Integer> q, int n) {
		this.q = q;
		this.n = n;
	}


	@Override
	public void run() {
		while(n > 0) {
			q.dequeue();
			n--;
		}
	}
}
