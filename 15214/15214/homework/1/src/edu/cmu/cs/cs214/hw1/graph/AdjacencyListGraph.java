package edu.cmu.cs.cs214.hw1.graph;

import edu.cmu.cs.cs214.hw1.staff.Graph;
import edu.cmu.cs.cs214.hw1.staff.Vertex;

public class AdjacencyListGraph implements Graph {

	private static class Node<Vertex> {
		private Vertex data; /* private data parameter */
		private Node<Vertex> next; /* the next node of the same type */

		/* constructor with data and next node */
		public Node(Vertex data) {
			this.data = data;
			this.next = null;
			return;
		}
	}

	private Node<Vertex>[] nodes;
	private int numNodes;
	private int upperBoundNum;

	/**
	 * The constructor of the AdjacencyListGraph class
	 * 
	 * Initialize the upperBoundNum parameter.
	 */
	public AdjacencyListGraph(int maxVertices) {
		upperBoundNum = maxVertices;
		numNodes = 0;
		nodes = new Node[upperBoundNum];
		return;
	}

	@Override
	public void addVertex(Vertex v) {
		if (v == null) {
			System.out
			.println("The vertex you want to add is null");
			return;
		}
		Vertex[] vertexes = getVertices();
		for (int i = 0; i < vertexes.length; i++){
			if (vertexes[i].equals(v)){
				System.out
				.println("The vertex you want to add is already in the graph!");
		        return;
			}
		}
		if (numNodes >= upperBoundNum) {
			System.out
					.println("The number of vertexes in this graph will exceed the upper limit value!");
			return;
		}
		Node<Vertex> node = new Node<Vertex>(v);
		nodes[numNodes++] = node;
		return;
	}

	@Override
	public void addEdge(Vertex v1, Vertex v2) {
		if (v1 == null || v2 == null) {
			System.out
			.println("The vertex you want to add is null");
			return;
		}
		Vertex[] vertexes = getVertices();
		boolean flag_1 = false;
		boolean flag_2 = false;
		for (int i = 0; i < vertexes.length; i++){
			if (vertexes[i].equals(v1)){
				flag_1 = true;
			}
			if (vertexes[i].equals(v2)){
				flag_2 = true;
			}
		}
		if (flag_1 == false){
			System.out.println(v1.getLabel() + " is not in the graph");
			return;
		}
		if (flag_2 == false){
			System.out.println(v2.getLabel() + " is not in the graph");
			return;
		}
		if (v1.equals(v2)) {
			System.out.println("The same vertex: " + v1.getLabel());
			return;
		}
		if (isAdjacent(v1, v2)) {
			System.out.println("Vertexes: " + v1.getLabel() + " and "
					+ v2.getLabel() + " has already connected!");
			return;
		}
		/*
		 * linear search to find the corresponding node, then insert node after
		 * it
		 */
		for (int i = 0; i < numNodes; i++) {
			if (nodes[i].data.equals(v1)) {
				Node<Vertex> tmp = nodes[i].next;
				nodes[i].next = new Node<Vertex>(v2);
				nodes[i].next.next = tmp;
			}
			if (nodes[i].data.equals(v2)) {
				Node<Vertex> tmp = nodes[i].next;
				nodes[i].next = new Node<Vertex>(v1);
				nodes[i].next.next = tmp;
			}
		}
		return;

	}

	@Override
	public boolean isAdjacent(Vertex v1, Vertex v2) {
		if (v1 == null || v2 == null) {
			System.out
			.println("The vertex you want to add is null");
			return false;
		}
		Vertex[] vertexes = getVertices();
		boolean flag_1 = false;
		boolean flag_2 = false;
		for (int i = 0; i < vertexes.length; i++){
			if (vertexes[i].equals(v1)){
				flag_1 = true;
			}
			if (vertexes[i].equals(v2)){
				flag_2 = true;
			}
		}
		if (flag_1 == false){
			System.out.println(v1.getLabel() + " is not in the graph");
			return false;
		}
		if (flag_2 == false){
			System.out.println(v2.getLabel() + " is not in the graph");
			return false;
		}
		
		if (v1.equals(v2)) {
			System.out
					.println("The two persons is the same one, which leads to false result.");
			return false;
		}
		/*
		 * linear search t find the node v1, then check whether v2 is in
		 * adjacent list
		 */
		for (int i = 0; i < numNodes; i++) {
			if (nodes[i].data.equals(v1)) {
				Node<Vertex> curr = nodes[i].next;
				while (curr != null) {
					if (curr.data.equals(v2)) {
						return true;
					} else {
						curr = curr.next;
					}
				}
			}
		}
		return false;
	}

	@Override
	public Vertex[] getNeighbors(Vertex v) {
		if (v == null) {
			System.out
			.println("The vertex you want to add is null");
			return new Vertex[0];
		}
		Vertex[] vertexes = getVertices();
		boolean flag = false;
		for (int i = 0; i < vertexes.length; i++){
			if (vertexes[i].equals(v)){
				flag = true;
			}
		}
		if (flag == false){
			System.out
			.println(v.getLabel() + " is not in the graph!");
			return new Vertex[0];
		}
		Vertex[] tmp = new Vertex[upperBoundNum];
		int numNebs = 0;
		/* create tmp array and calculate the number of neighbors */
		for (int i = 0; i < numNodes; i++) {
			if (nodes[i].data.equals(v)) {
				Node<Vertex> curr = nodes[i].next;
				while (curr != null) {
					tmp[numNebs++] = curr.data;
					curr = curr.next;
				}
			}
		}
		/* then copy to a no-null array */
		Vertex[] result = new Vertex[numNebs];
		for (int j = 0; j < numNebs; j++) {
			result[j] = tmp[j];
		}
		return result;
	}

	@Override
	public Vertex[] getVertices() {
		Vertex[] result = new Vertex[numNodes];
		for (int i = 0; i < numNodes; i++) {
			result[i] = nodes[i].data;
		}
		return result;
	}

}