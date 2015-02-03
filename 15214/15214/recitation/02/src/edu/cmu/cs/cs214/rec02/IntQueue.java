package edu.cmu.cs.cs214.rec02;

/**
 * Interface describing a first-in, first-out structure for integers. Values are 
 * added at the tail, and removed from the head. Queues are typically used to 
 * process values in the order that they appear and to store the state of a 
 * buffered object.
 *
 * @author Alex Lockwood
 */
public interface IntQueue {

    /**
     * Remove all the elements from the queue.
     */
    public void clear();

    /**
     * Fetch and remove the element at the head of the queue.
     *
     * @return 	The element at the head of the queue. Returns 
     * 			null if the queue is empty.
     */
    public Integer dequeue();

    /**
     * Add the element to the head of the queue.
     *
     * @param value	The element to place at the head of the queue.
     * @return 		Whether the element was enqueued successfully.
     */
    public boolean enqueue(Integer value);

    /**
     * Determine if the queue is empty.
     *
     * @return <tt>true</tt> if the queue is empty, <tt>false</tt> otherwise.
     */
    public boolean isEmpty();  

    /**
     * Fetch the element at the head of the queue.
     *
     * @return The element at the head of the queue. Returns null if queue 
     * 		   is empty.
     */
    public Integer peek();

    /**
     * Determine the number of elements in the queue.
     *
     * @return The number of elements in the queue.
     */
    public int size();
}
