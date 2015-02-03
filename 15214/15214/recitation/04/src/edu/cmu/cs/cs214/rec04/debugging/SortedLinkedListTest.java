package edu.cmu.cs.cs214.rec04.debugging;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Press the following buttons:
 *
 * (1) To begin debugging, press "Run --> Debug".
 *
 * (2) To enable assertions, press "Run --> Run Configurations", click on the
 * "Arguments" tab, type "-ea" in VM arguments, and press execute (err... I
 * mean, 'apply').
 * 
 * Also please note that this is not an example of a complete test file. There
 * are many test cases that should be added in order to have a complete test
 * file.
 */
public class SortedLinkedListTest {

	private SortedIntLinkedList halfMeasure;

	@Before
	public void setUp() {
		halfMeasure = new SortedIntLinkedList();
	}

	@Test
	public void testSortedIntLinkedListAdd() {
		halfMeasure.add(10);
		halfMeasure.add(2);
		halfMeasure.add(3);
		halfMeasure.add(4);
		halfMeasure.add(5);
		halfMeasure.add(6);
		halfMeasure.add(7);
		halfMeasure.add(42);
		halfMeasure.add(5);
		halfMeasure.add(6);
		assertArrayEquals(new int[] { 2, 3, 4, 5, 6, 7, 10, 42 },
				halfMeasure.toArray());
	}

	@Test
	public void testSortedIntLinkedListRemove() {
		halfMeasure.add(10);
		halfMeasure.add(2);
		halfMeasure.add(3);
		halfMeasure.add(4);
		halfMeasure.add(5);
		halfMeasure.add(6);
		halfMeasure.remove(6);
		halfMeasure.remove(2);
		halfMeasure.remove(7418880);
		assertArrayEquals(new int[] { 3, 4, 5, 10 }, halfMeasure.toArray());
	}

}
