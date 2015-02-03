package edu.cmu.cs.cs214.rec12.queue;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

import edu.cmu.cs.cs214.rec12.runnable.*;


public class QueueTest {
	private static Queue<Integer> makeEmptyQueue() {
		//return new UnsynchronizedQueue<Integer>();
		return new UnboundedBlockingQueue<Integer>();
		//return new FineGrainedUnboundedBlockingQueue<Integer>();
	}
	
	@Test
	public void testSize() {
		Queue<Integer> q = makeEmptyQueue();
		Thread t1 = new Thread(new Producer(q, 100000));
		Thread t2 = new Thread(new Producer(q, 100000));
		Thread t3 = new Thread(new SpinWaitConsumer(q, 200000));
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("Queue size should be zero", 0, q.size());	
	}

	@Test
	public void testEnqueue() throws Exception {
		final int SIZE = 1000;
		Queue<Integer> q = makeEmptyQueue();
		Thread t1 = new Thread(new Producer(q, SIZE));
		Thread t2 = new Thread(new Producer(q, SIZE));
		t1.start();  // Inserts two of each item 0..SIZE-1 into the queue.
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int[] counts = new int[SIZE];
		
		Field headField = q.getClass().getDeclaredField("head");  // Uses Java reflection to
		headField.setAccessible(true);                            // access private data.
		Object currentNode = headField.get(q);
		
		Field nextField = currentNode.getClass().getDeclaredField("next");
		nextField.setAccessible(true);
		Field dataField = currentNode.getClass().getDeclaredField("data");
		dataField.setAccessible(true);
		
		currentNode = nextField.get(currentNode);  // Skips the dummy node at front of the queue.
		while (currentNode != null) {             // Tracks count of each item in queue.
			Integer data = (Integer) dataField.get(currentNode);
			assertTrue("data should be >= 0", data.intValue() >= 0);
			assertTrue("data should be < SIZE", data.intValue() < SIZE);
			counts[data]++;
			currentNode = nextField.get(currentNode);
		}
		
		for (int i = 0; i < SIZE; ++i) {
			assertEquals("Queue should contain two of each value 1.." + SIZE + ", error at value " + i, 2, counts[i]);
		}
	}
	
	@Test
	public void testQueueBlocking() {
		Queue<Integer> q = new UnboundedBlockingQueue<Integer>();
		Thread t1 = new Thread(new Producer(q, 200000));
		Thread t2 = new Thread(new Consumer(q, 100000));
		Thread t3 = new Thread(new Consumer(q, 100000));
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("Queue size should be zero", 0, q.size());
	}
}
