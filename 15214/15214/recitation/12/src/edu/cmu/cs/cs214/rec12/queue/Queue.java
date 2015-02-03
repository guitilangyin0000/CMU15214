package edu.cmu.cs.cs214.rec12.queue;

public interface Queue<E> {

  /**
   * Returns true iff queue is empty.
   */
  public boolean isEmpty();

  /**
   * Returns the current size of the queue.
   */
  public int size();

  /**
   * Returns the first element of the queue without removing it from the queue.
   */
  public E peek();

  /**
   * Appends the data to the back of the queue.
   */
  public void enqueue(E element);

  /**
   * Removes and returns the element from the front of the queue.
   */
  public E dequeue();

}
