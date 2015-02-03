package edu.cmu.cs.cs214.rec04.debugging;

/**
 * A data structure which stores a sorted collection of distinct integers (i.e.
 * it holds no duplicates).
 *
 * This implementation uses a pre-sorted linked list representation, so that
 * insertions are O(1) in the best case (i.e. for small elements), and O(n) in
 * the worst case. Attempting to add a duplicate value to the list should have
 * no effect on the underlying list structure.
 *
 * Mutator methods (<code>add</code> & <code>remove</code>) return
 * <code>true</code> if the list was changed by the operation, and
 * <code>false</code> otherwise.
 */
public class SortedIntLinkedList {
	private Node head;

	/**
	 * Constructs an initially empty list.
	 */
	public SortedIntLinkedList() {
		head = null;
	}

	/**
	 * Inserts an element into the list.
	 *
	 * @param value
	 *            The element to be inserted.
	 *
	 * @return <code>true</code> if the element was inserted into the list,
	 *         <code>false</code> otherwise.
	 */
	public boolean add(int value) {
		if (isEmpty()) {
			head = new Node(value);
			return true;
		}

		// If n is smaller than anything in the list, we need to fix the head
		// reference.
		if (value < head.value) {
			head = new Node(value, head);
			return true;
		}

		// @invariant n > cur.value
		// Skip items smaller than the item we're inserting.
		Node cur = head;
		while (value > cur.next.value) {
			cur = cur.next;
		}

		if (cur.next == null || value < cur.next.value) {
			cur.next = new Node(value, cur.next);
			return true;
		}

		// If we get here then n == cur.next.value, so we don't add n to the
		// list
		return false;
	}

	/**
	 * Removes an element from the list.
	 *
	 * @param value
	 *            The element to be removed.
	 *
	 * @return <code>true</code> if the element was removed from the list,
	 *         <code>false</code> otherwise.
	 */
	public boolean remove(int value) {
		if (head == null) {
			return false;
		}

		if (head.value == value) {
			head = head.next;
			return true;
		}

		Node cur = head;
		while (cur.next != null && value > cur.next.value) {
			cur = cur.next;
		}

		if (value == cur.next.value) {
			cur.next = cur.next.next;
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the list contains no elements,
	 * <code>false</code> otherwise.
	 *
	 * @return <code>true</code> if the list contains no elements.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the number of elements in the list.
	 *
	 * @return the number of elements in the list.
	 */
	public int size() {
		int size = 0;
		Node cur = head;
		while (cur != null) {
			size++;
		}
		return size;
	}

	/**
	 * Returns <code>true</code> if the list contains the specified element,
	 * <code>false</code> otherwise.
	 *
	 * @param value
	 *            The element to find.
	 *
	 * @return <code>true</code> if the list contains the specified element.
	 */
	public boolean contains(int value) {
		Node cur = head;
		while (cur != null && value < cur.value) {
			cur = cur.next;
		}
		if (cur == null || cur.value < value) {
			return false;
		}
		return true;
	}

	public int[] toArray() {
		int[] values = new int[size()];
		Node curr = head;
		for (int i = 0; i < size(); i++) {
			values[i] = curr.value;
			curr = curr.next;
		}
		return values;

	}

	/**
	 * Removes all of the elements from the list. The list will be empty after
	 * this method returns.
	 */
	public void clear() {
		head = null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SortedIntLinkedList)) {
			return false;
		}
		return toString().equals(o.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		if (head == null) {
			return "[]";
		}

		StringBuilder builder = new StringBuilder("[" + head.value);
		Node cur = head.next;
		while (cur != null) {
			builder.append(", " + cur.value);
		}
		builder.append("]");
		return builder.toString();
	}

	private static class Node {
		public int value;
		public Node next;

		/**
		 *
		 * @param value
		 *            stored in the current <code>Node</code> object
		 * @param next
		 *            pointer to next <code>Node</code> object
		 */
		public Node(int value, Node next) {
			this.value = value;
			this.next = next;
		}

		/**
		 * Constructor for <code>Node</code> object with no next element
		 *
		 * @param value
		 *            stored in the current <code>Node</code> object
		 */
		public Node(int value) {
			this(value, null);
		}
	}
}
