package edu.cmu.cs.cs214.rec12.runnable;

import edu.cmu.cs.cs214.rec12.queue.Queue;

public class SpinWaitConsumer implements Runnable {

	private Queue<Integer> q;
	private int n;

	public SpinWaitConsumer(Queue<Integer> q, int n) {
		this.q = q;
		this.n = n;
	}

	/**
	 * Waits a while (1 million loops) for the queue to be non-empty. 
	 */
	private boolean spinWait(Queue<Integer> q) {
		for (int i = 0; i < 1000000; ++i) {
			if (!q.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		while(spinWait(q) && n > 0) {
			q.dequeue();
			n--;
		}
	}
}
