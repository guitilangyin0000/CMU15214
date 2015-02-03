package edu.cmu.cs.cs214.rec02;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * TODO: Write detailed unit tests for the {@link LinkedIntQueue} and
 * {@link ArrayIntQueue} classes, as described in the handout. The
 * {@link ArrayIntQueue} class contains a few bugs. Use the tests you wrote
 * for the {@link LinkedIntQueue} class to test the {@link ArrayIntQueue}
 * implementation. Refine your tests to achieve 100% line coverage and use them
 * to determine and correct the bugs!
 * 
 * @author Alex Lockwood
 */
public class IntQueueTest {

    private IntQueue mQueue;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // TODO: comment/uncomment these lines to test each class
        mQueue = new LinkedIntQueue();
        // mQueue = new ArrayIntQueue();
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertNull(mQueue.peek());
    }

}
