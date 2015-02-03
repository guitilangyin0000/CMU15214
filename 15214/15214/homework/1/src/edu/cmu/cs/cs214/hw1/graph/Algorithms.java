package edu.cmu.cs.cs214.hw1.graph;

import edu.cmu.cs.cs214.hw1.staff.Graph;
import edu.cmu.cs.cs214.hw1.staff.Vertex;

public class Algorithms {

	/**
	 * *********************** Algorithms **************************** Please
	 * see the handout for a more detailed specification of the behavior of each
	 * method.
	 */

	/**
	 * This method finds the shortest distance between two vertices. It returns
	 * -1 if the two nodes are not connected.
	 * 
	 * Precondition: The graph is not null and a and b are vertices in the
	 * graph.
	 * 
	 * @param graph
	 *            the friends graph
	 * @param a
	 *            the first Vertex
	 * @param b
	 *            the second Vertex
	 */
	public static int shortestDistance(Graph graph, Vertex a, Vertex b) {
		if (graph == null){
			System.out
			.println("The graph is null");
			return -1;
		}
		if (a == null || b == null) {
			System.out
			.println("The vertex you want to check is null");
			return -1;
		}
		Vertex[] vertexes_1 = graph.getVertices();
		boolean flag_1 = false;
		boolean flag_2 = false;
		for (int i = 0; i < vertexes_1.length; i++){
			if (vertexes_1[i].equals(a)){
				flag_1 = true;
			}
			if (vertexes_1[i].equals(b)){
				flag_2 = true;
			}
		}
		if (flag_1 == false){
			System.out.println(a.getLabel() + " is not in the graph");
			return -1;
		}
		if (flag_2 == false){
			System.out.println(b.getLabel() + " is not in the graph");
			return -1;
		}
		
		if (a.equals(b)){
			return 0;
		}
		int numVers = graph.getVertices().length;
		int depth = 0; /* record the distance of relationship */
		int numSeen = 0; /* record the number of seen persons */
		int numTrav = 0; /* record the number of persons in traverse array */
		int numLayer = 0; /* record the number of persons in current layer */

		Vertex[] traverse = new Vertex[numVers];
		traverse[numTrav++] = a; /* only one person in traverse array */

		Vertex[] seen = new Vertex[numVers]; // only one person we have
														// seen */
		seen[numSeen++] = a;

		Vertex[] layer = new Vertex[numVers]; /* only one person in current layer */
		layer[numLayer++] = a;

		/* the while loop, there are two conditions it can jump out of the loop */
		/* (1) the traverse array comes to empty, or means we cannot find it in */
		/* graph */
		/* (2) we find it in current traverse array */
		while (numTrav != 0) {
			Vertex head = traverse[0];
			/* extract the head of the traverse loop */
			Vertex[] tmp = graph.getNeighbors(head);
			int numTmp = tmp.length;
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
			traverse = deleteHead(traverse, numTrav, numVers);
			numTrav -= 1;

			/* if the layer array has no common person with traverse array, just */
			/* update */
			/* the layer array, at the same time, we should update the value of */
			/* depth */
			if (!hasCommon(traverse, numTrav, layer, numLayer)) {
				Vertex[] layerTmp = new Vertex[numVers];
				System.arraycopy(traverse, 0, layerTmp, 0, numTrav);
				layer = layerTmp;
				numLayer = numTrav;
				depth += 1;
				if (isIn(b, traverse, numTrav)){
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
	 * This method is used to find common friends between v1 and v2. The
	 * resulting array should not contain any duplicates, and should have length
	 * equal to the number of vertices. It should not contain any nulls. The
	 * vertices in the array can be ordered arbitrarily.
	 * 
	 * If there are no common friends, then return an array of size 0.
	 * 
	 * Precondition: The graph is not null and a and b are vertices in the
	 * graph.
	 * 
	 * @param graph
	 *            the friends graph
	 * @param a
	 *            the first Vertex
	 * @param b
	 *            the second Vertex
	 */
	public static Vertex[] commonFriends(Graph graph, Vertex a, Vertex b) {
		if (graph == null){
			System.out
			.println("The graph is null");
			return new Vertex[0];
		}
		if (a == null || b == null) {
			System.out
			.println("The vertex you want to check is null");
			return new Vertex[0];
		}
		Vertex[] vertexes_1 = graph.getVertices();
		boolean flag_1 = false;
		boolean flag_2 = false;
		for (int i = 0; i < vertexes_1.length; i++){
			if (vertexes_1[i].equals(a)){
				flag_1 = true;
			}
			if (vertexes_1[i].equals(b)){
				flag_2 = true;
			}
		}
		if (flag_1 == false){
			System.out.println(a.getLabel() + " is not in the graph");
			return new Vertex[0];
		}
		if (flag_2 == false){
			System.out.println(b.getLabel() + " is not in the graph");
			return new Vertex[0];
		}
		if (a.equals(b)){
			return new Vertex[0];
		}
		Vertex[] n_a = graph.getNeighbors(a);
		Vertex[] n_b = graph.getNeighbors(b);
		if (n_a.length == 0){
			return n_a;
		}
		if (n_b.length == 0){
			return n_b;
		}
		Vertex[] tmp;
		int numComs = 0;
		if (n_a.length > n_b.length){
			tmp = new Vertex[n_a.length];
		}else{
			tmp = new Vertex[n_b.length];
		}
		
		for (int i = 0; i < n_a.length; i++){
			for (int j = 0; j < n_b.length; j++){
				if (n_a[i].equals(n_b[j])){
					tmp[numComs++] = n_a[i];
				}
			}
		}
		
		Vertex[] result = new Vertex[numComs];
		for (int k = 0; k < numComs; k++){
			result[k] = tmp[k];
		}
		return result;
	}

	/**
	 * This method is used to find the person who has the most common friends
	 * with a particular user. If there is a tie, you can return any of the
	 * people who are tied.
	 * 
	 * Precondition: The graph is not null and source is a vertex in the graph.
	 * 
	 * @param graph
	 *            the friends graph
	 * @param source
	 *            the Vertex(Person) in question
	 */
	public static Vertex suggestFriend(Graph graph, Vertex source) {
		if (graph == null){
			System.out
			.println("The graph is null");
			return null;
		}
		if (source == null) {
			System.out
			.println("The vertex you want to check is null");
			return null;
		}
		Vertex[] vertexes_1 = graph.getVertices();
		boolean flag = false;
		for (int i = 0; i < vertexes_1.length; i++){
			if (vertexes_1[i].equals(source)){
				flag = true;
			}

		}
		if (flag == false){
			System.out.println(source.getLabel() + " is not in the graph");
			return null;
		}
		int maxVal = -1;
		Vertex maxVer = source;
		Vertex[] vertexes = graph.getVertices();
		for (int i = 0; i < vertexes.length; i++){
			if (!vertexes[i].equals(source)){
				int tmp = commonFriends(graph, source, vertexes[i]).length;
				if (tmp > maxVal){
					maxVal = tmp;
					maxVer = vertexes[i];
				}
			}
		}
		return maxVer;
	}
	
	/**
	 * hasCommon function can check whether the two arrays have common elements.
	 * 
	 * @param array_1, num_1, array_2, num_2 
	 *            two arrays and their effective length
	 * 
	 * @return true/false the boolean result
	 */
	private static boolean hasCommon(Vertex[] persons_1, int num_1,
			Vertex[] persons_2, int num_2) {
		for (int i = 0; i < num_1; i++) {
			for (int j = 0; j < num_2; j++) {
				if (persons_1[i].getLabel().equals(persons_2[j].getLabel())) {
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
	private static boolean isIn(Vertex person, Vertex[] persons, int num) {
		for (int i = 0; i < num; i++) {
			if (person.getLabel().equals(persons[i].getLabel())) {
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
	private static Vertex[] deleteHead(Vertex[] persons, int numTrav, int numVers) {
		Vertex[] tmp = new Vertex[numVers];
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
	private static void addTail(Vertex[] persons, int num, Vertex person) {
		int tmp = num;
		persons[tmp++] = person;
		return;
	}

}
