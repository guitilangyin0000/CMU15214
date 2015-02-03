package edu.cmu.cs.cs214.rec12.queue;


/**
 * Queue implementation with fine-grained synchronization
 *
 * @author Danny Lu
 */
public class FineGrainedUnboundedBlockingQueue<E> implements Queue<E> {
	private Node<E> head;  
	private Node<E> tail;  
	private int size;      
	
	// locks
	private final Object headLock = new Object();
	private final Object tailLock = new Object();

	private static class Node<E> {
		public Node<E> next;
		public E data;
		public Node(E data) {
			this.data = data;
			this.next = null;
		}
	}
	
	public FineGrainedUnboundedBlockingQueue() {
		head = new Node<E>(null);
		tail = head;
		size = 0;
	}

	@Override
	public boolean isEmpty() {
		synchronized (headLock) {
			synchronized (tailLock) {
				return size == 0;
			}
		}
	}

	@Override
	public int size() {
		synchronized (headLock) {
			synchronized (tailLock) {
				return size;
			}
		}
	}

	@Override
	public E peek() {
		synchronized (headLock) {
			if (size == 0) {
				return null;
			}
			synchronized (tailLock) {
				return head.next.data;
			}
		}
	}

	 @Override
	public void enqueue(E data) {
		synchronized (tailLock) {
			synchronized (tail) {
				Node<E> newTail = new Node<E>(data);		
				tail.next = newTail;
				Node<E> oldTail = tail;
				tail = newTail;
				synchronized (this) { 
					size++; 
				}
				oldTail.notify();
			}
		}
	}

	@Override
	public E dequeue() {
		synchronized (headLock) {
			synchronized (head) {
				while (size == 0) {
					try {
						head.wait();
					} catch (InterruptedException ignore) {
					}
				}
				E data = head.next.data;
				head = head.next;
				head.data = null;
				synchronized (this) {
					size--;
				}
				return data;
			}
		}
	}

	@Override
	public String toString() {
		synchronized (headLock) {
			synchronized (tailLock) {
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

	}
}
