package edu.cmu.cs.cs214.rec12.queue;

import java.util.NoSuchElementException;

/**
 * Modify this class to be thread-safe and be an UnboundedBlockingQueue.  
 * The queue disallows null elements.
 *
 * @author Danny Lu
 */
public class UnboundedBlockingQueue<E> implements Queue<E>{
	private Node<E> head;  // A dummy node.  head.next points to the  first real node in the queue.
	private Node<E> tail;  // The last node in the queue.  head == tail if the queue is empty.
	private int size;      // The number of elements in the queue.
	
	private static class Node<E> {
		public Node<E> next;
		public E data;
		public Node(E data) {
			this.data = data;
			this.next = null;
		}
	}
	
	public UnboundedBlockingQueue() {
		head = new Node<E>(null);
		tail = head;
		size = 0;
	}

	@Override
	public synchronized boolean isEmpty() {
		return size == 0;
	}

	@Override
	public synchronized int size() {
		return size;
	}

	@Override
	public synchronized E peek() {
		if (size == 0) {
			return null;
		}
		return head.next.data;
	}

	@Override
	public synchronized void enqueue(E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		Node<E> newTail = new Node<E>(element);		
		tail.next = newTail;
		tail = newTail;
		size++;
		notifyAll();
	}

	@Override
	public synchronized E dequeue() {
		while (size == 0){
			try{
				this.wait();
			} catch (InterruptedException e){
				
			}
		}
//		// TODO:  Change this method to block (waiting for an enqueue) rather
//		// than throw an exception.
//		if (size == 0) {
//			throw new NoSuchElementException("Can't dequeue from an empty queue.");
//		}
		E data = head.next.data;
		head = head.next;
		head.data = null;
		size--;
		return data;
	}

	@Override
	public synchronized String toString() {
		if (size == 0) {
			return "";
		}
		StringBuilder s = new StringBuilder();
		s.append(head.next.data);
		Node<E> temp = head.next.next;  
		while (temp != null) {
			s.append(" " + temp.data);
			temp = temp.next;
		}
		return s.toString();
	}
}
