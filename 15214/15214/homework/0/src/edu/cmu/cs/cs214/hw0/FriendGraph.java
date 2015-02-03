package edu.cmu.cs.cs214.hw0;

/**
 * Name: Yang Wang
 * Andrew Id: yangwan2
 */

/**
 * FriendGraph is a class representing a the relationship among persons' friends
 * circles
 */
public class FriendGraph {

	private Person[][] persons;
	/* The relationship matrix */
	private int[] total;
	/* The number array which represents the number of friends of each person */
	private int numPers;
	/* The total number of persons in this graph */
	private static final int MAX_NUM_FRIENDS = 50;

	/**
	 * Class constructor specifying the identifier of the friends relationships
	 * 
	 * @param null
	 * 
	 */
	public FriendGraph() {
		numPers = 0;
		total = new int[MAX_NUM_FRIENDS];
		persons = new Person[MAX_NUM_FRIENDS + 1][MAX_NUM_FRIENDS + 1];
		/* Due to the maximum friends a person can have */
	}

	/**
	 * addPerson function can add a person into this relationship
	 * 
	 * @param person
	 *            the specified person
	 * 
	 */
	public void addPerson(Person person) {
		if (person == null) {
			throw new IllegalArgumentException("The person is not exist!");
		}
		if (isInGraph(person)) {
			throw new IllegalArgumentException(
					"The " + person.getName() + " is already in the FriendGraph!");
		}
		/* Initialize the matrix and array after a person is added */
		persons[numPers][0] = person;
		total[numPers] = 0;
		numPers += 1;
	}

	/**
	 * addFriendship function can make the two persons as friends
	 * 
	 * @param person1
	 *            , person2 two specified persons
	 * 
	 * @return null
	 */
	public void addFriendship(Person person_1, Person person_2) {
		if (person_1 == null || person_2 == null) {
			throw new IllegalArgumentException(
					"At least one person is not exist in this FriendGraph!");
		}
		if (person_1.getName().equals(person_2.getName())) {
			throw new IllegalArgumentException(
					"The names of the two persons should not be the same!");
		}
		if (!isInGraph(person_1) || !isInGraph(person_2)) {
			throw new IllegalArgumentException(
					"At least one person is not in this Friendgraph!");
		}
		int index_1 = getNumIndex(person_1);
		int index_2 = getNumIndex(person_2);
		int numFrds_1 = getNumFrds(person_1);
		int numFrds_2 = getNumFrds(person_2);
		Person[] friends_1 = getFriends(person_1);
		Person[] friends_2 = getFriends(person_2);
		if (isIn(person_2, friends_1, numFrds_1)
				|| isIn(person_1, friends_2, numFrds_2)) {
			throw new IllegalArgumentException(person_1.getName() + " and "
					+ person_2.getName() + " are already friends");
		}
		/* Add the other person as a friend in each row */
		persons[index_1][numFrds_1 + 1] = person_2;
		persons[index_2][numFrds_2 + 1] = person_1;
		/* Update the number of each person's friends */
		total[index_1] += 1;
		total[index_2] += 1;
		return;
	}

	/**
	 * getDistance function can return the distance of two persons in the
	 * FriendGraph.
	 * 
	 * @param person1, person2 
	 *         two specified persons
	 * @return integer the distance of their relationships in current
	 *         FriendGraph
	 */

	public int getDistance(Person person_1, Person person_2) {
		if (person_1 == null || person_2 == null) {
			throw new IllegalArgumentException(
					"At least one person is not exist!");
		}
		if (!isInGraph(person_1)){
			throw new IllegalArgumentException(
					"person: " + person_1.getName() + " is not in this FriendGraph!");
		}
		if (!isInGraph(person_2)){
			throw new IllegalArgumentException(
					"person: " + person_2.getName() + " is not in this FriendGraph!");
		}
		if (person_1.getName().equals(person_2.getName())){
			return 0;
		}
		/* use BFS to get the distance */
		return solve(person_1, person_2);

	}

	/**
	 * isIn function can check whether a person is already in the FriendGraph.
	 * 
	 * @param person
	 *            a specified person we want to check of
	 * @return true/false the boolean result
	 */
	private boolean isInGraph(Person person) {
		if (person == null) {
			throw new IllegalArgumentException("The person is not exist!");
		}
		for (int i = 0; i < numPers; i++) {
			if (persons[i][0].getName().equals(person.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * getFriends function can return the Person array of a person in this
	 * FriendGraphs.
	 * 
	 * @param person
	 *            the specified person
	 * @return Person array: persons a person array which represents the friends
	 *         of the person
	 */
	private Person[] getFriends(Person person) {
		if (person == null) {
			throw new IllegalArgumentException("The person is not exist!");
		}
		if (!isInGraph(person)) {
			throw new IllegalArgumentException("person: " + person.getName()
					+ " is not in this graph");
		}
		int numFrds = getNumFrds(person);
		int index = getNumIndex(person);
		Person[] friends = new Person[MAX_NUM_FRIENDS];
		for (int i = 0; i < numFrds; i++) {
			friends[i] = persons[index][i + 1];
		}
		return friends;
	}

	/**
	 * getNumPers function can return the number of persons in this
	 * FriendGraphs.
	 * 
	 * @param null
	 * 
	 * @return numPers a number which represents the number of persons
	 */
	public int getNumPers() {
		return numPers;
	}

	/**
	 * getNumIndex function can return the index of the person in persons matrix
	 * 
	 * @param person
	 *            the specified person
	 * 
	 * @return number a number which represents the number of friends of
	 *         his/hers
	 */
	private int getNumIndex(Person person) {
		if (person == null) {
			throw new IllegalArgumentException("The person is not exist!");
		}
		if (!isInGraph(person)) {
			throw new IllegalArgumentException("person: " + person.getName()
					+ " is not in this graph");
		} else {
			int index = 0;
			for (int i = 0; i < numPers; i++) {
				if (persons[i][0].getName().equals(person.getName())) {
					break;
				}
				index += 1;
			}
			return index;
		}
	}

	/**
	 * getNumFrds function can return the number of friends of a person in this
	 * graph
	 * 
	 * @param person
	 *            the specified person
	 * 
	 * @return number a number which represents the number of friends of
	 *         his/hers
	 */
	private int getNumFrds(Person person) {
		if (person == null) {
			throw new IllegalArgumentException("The person is not exist!");
		}
		if (!isInGraph(person)) {
			throw new IllegalArgumentException("person: " + person.getName()
					+ " is not in this graph");
		} else {
			int index = getNumIndex(person);
			return total[index];
		}
	}

	/**
	 * solve function can return the distance of relationship.
	 * 
	 * @param person_1, person_2 
	 *            the two persons we want to figure out
	 * 
	 * @return depth the distance of the relationship
	 */
	private int solve(Person person_1, Person person_2) {
		if (person_1 == null || person_2 == null) {
			throw new IllegalArgumentException(
					"At least one person is not exist!");
		}
		int depth = 0; /* record the distance of relationship */
		int numSeen = 0; /* record the number of seen persons */
		int numTrav = 0; /* record the number of persons in traverse array */
		int numLayer = 0; /* record the number of persons in current layer */

		Person[] traverse = new Person[MAX_NUM_FRIENDS];
		traverse[numTrav++] = person_1; /* only one person in traverse array */

		Person[] seen = new Person[MAX_NUM_FRIENDS]; // only one person we have
														// seen */
		seen[numSeen++] = person_1;

		Person[] layer = new Person[MAX_NUM_FRIENDS]; /*
													 * only one person in
													 * current layer
													 */
		layer[numLayer++] = person_1;

		/* the while loop, there are two conditions it can jump out of the loop */
		/* (1) the traverse array comes to empty, or means we cannot find it in */
		/* graph */
		/* (2) we find it in current traverse array */
		while (numTrav != 0) {
			Person head = traverse[0];
			/* extract the head of the traverse loop */
			Person[] tmp = getFriends(head);
			int numTmp = getNumFrds(head);
			for (int i = 0; i < numTmp; i++) {
				/* just add the persons we have never seen */
				if (!isIn(tmp[i], seen, numSeen)) {
					addTail(traverse, numTrav, tmp[i]);
					/* update the traverse array */
					numTrav += 1;
					addTail(seen, numSeen, tmp[i]);
					/* update the seen array */
					numSeen += 1;
				}
			}
			/* remove the head of the traverse array */
			traverse = deleteHead(traverse, numTrav);
			numTrav -= 1;

			/* if the layer array has no common person with traverse array, just */
			/* update */
			/* the layer array, at the same time, we should update the value of */
			/* depth */
			if (!hasCommon(traverse, numTrav, layer, numLayer)) {
				Person[] layerTmp = new Person[MAX_NUM_FRIENDS];
				System.arraycopy(traverse, 0, layerTmp, 0, numTrav);
				layer = layerTmp;
				numLayer = numTrav;
				depth += 1;
				if (isIn(person_2, traverse, numTrav)){
					break;
				}
			}
		}
		/* if we could not find it in graph, just return -1 */
		if (numTrav == 0) {
			return -1;
		}
		/* otherwise, return the value of depth */
		return depth;
	}

	/**
	 * hasCommon function can check whether the two arrays have common elements.
	 * 
	 * @param array_1, num_1, array_2, num_2 
	 *            two arrays and their effective length
	 * 
	 * @return true/false the boolean result
	 */
	private boolean hasCommon(Person[] persons_1, int num_1,
			Person[] persons_2, int num_2) {
		if (persons_1 == null || persons_2 == null) {
			throw new IllegalArgumentException(
					"At least one array is not exist!");
		}
		if (num_1 < 0 || num_2 < 0 || num_1 > persons_1.length || num_2 > persons_2.length) {
			throw new IllegalArgumentException(
					"The effective length is not matched with array!");
		}
		for (int i = 0; i < num_1; i++) {
			for (int j = 0; j < num_2; j++) {
				if (persons_1[i].getName().equals(persons_2[j].getName())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * isIn function can check whether the person is in the Person array
	 * 
	 * @param array, num, person 
	 *            the array, its effective length and the
	 *            specified person
	 * 
	 * @return true/false the boolean result
	 */
	private boolean isIn(Person person, Person[] persons, int num) {
		if (num > persons.length || num < 0) {
			throw new IllegalArgumentException(
					"The num and the array do not match!");
		}
		if (person == null) {
			throw new IllegalArgumentException("The person is not exsit!");
		}
		for (int i = 0; i < num; i++) {
			if (person.getName().equals(persons[i].getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * deleteHead function can return an array which remove the first element of
	 * the original one.
	 * 
	 * @param array, number 
	 *            the array and its effective length
	 * 
	 * @return array the revised Person array
	 */
	private Person[] deleteHead(Person[] persons, int numTrav) {
		if (persons.length < 1 || numTrav <= 0) {
			throw new IllegalArgumentException("The operated array is empty!");
		}
		if (numTrav > persons.length) {
			throw new IllegalArgumentException(
					"The effective length is not matched with the array!");
		}
		Person[] tmp = new Person[MAX_NUM_FRIENDS];
		System.arraycopy(persons, 1, tmp, 0, numTrav - 1);
		return tmp;
	}

	/**
	 * addTail function can add an element to the end of the array
	 * 
	 * @param array, number and person 
	 *            The Person array wanted to add person to,
	 *            the effective length of that array and the person we want to
	 *            add
	 * 
	 * @return null
	 */
	private void addTail(Person[] persons, int num, Person person) {
		if (persons == null) {
			throw new IllegalArgumentException("The array is not exist!");
		}
		if (person == null) {
			throw new IllegalArgumentException("The added person is not exist!");
		}
		if (num > persons.length || num < 0) {
			throw new IllegalArgumentException(
					"The effective length is not matched with the array!");
		}
		if (num >= MAX_NUM_FRIENDS) {
			throw new IllegalArgumentException(
					"The number of the array is full! This should not happend!");
		}
		int tmp = num;
		persons[tmp++] = person;
		return;
	}
}
