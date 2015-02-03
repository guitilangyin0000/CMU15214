package edu.cmu.cs.cs214.hw1.graph;

import edu.cmu.cs.cs214.hw1.staff.Graph;
import edu.cmu.cs.cs214.hw1.staff.Vertex;

public class AdjacencyMatrixGraph implements Graph {
	private Vertex[] vertexes;
	private boolean[][] relations;
	private int numVers;
	private int upperBoundNum;

	/**
	 * The constructor of the AdjacencyMatrixGraph class
	 * 
	 * Initialize the upperBoundNum parameter.
	 */
	public AdjacencyMatrixGraph(int maxVertices) {
		upperBoundNum = maxVertices;
		vertexes = new Vertex[upperBoundNum];
		relations = new boolean[upperBoundNum][upperBoundNum];
		numVers = 0;
		return;
	}

	@Override
	public void addVertex(Vertex v) {
		if (v == null) {
			System.out
			.println("The vertex you want to add is null");
			return;
		}
		Vertex[] vertexes_1 = getVertices();
		for (int i = 0; i < vertexes_1.length; i++){
			if (vertexes[i].equals(v)){
				System.out
				.println("The vertex you want to add is already in the graph!");
		        return;
			}
		}
		if (numVers >= upperBoundNum) {
			System.out
					.println("The number of vertexes in this graph will exceed the upper limit value!");
			return;

		}
		vertexes[numVers] = v;
		relations[numVers][numVers] = false;
		numVers++;
		return;
	}

	@Override
	public void addEdge(Vertex v1, Vertex v2) {
		if (v1 == null || v2 == null) {
			System.out
			.println("The vertex you want to add is null");
			return;
		}
		Vertex[] vertexes_1 = getVertices();
		boolean flag_1 = false;
		boolean flag_2 = false;
		for (int i = 0; i < vertexes_1.length; i++){
			if (vertexes_1[i].equals(v1)){
				flag_1 = true;
			}
			if (vertexes_1[i].equals(v2)){
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
		if (v1.equals(v2)){
			System.out.println("The same vertex: " + v1.getLabel());
			return;
		}
		if (isAdjacent(v1, v2)) {
			System.out.println("Vertexes: " + v1.getLabel() + " and "
					+ v2.getLabel() + " has already connected!");
			return;
		}
		int index_1 = 0, index_2 = 0;
		for (int i = 0; i < numVers; i++) {
			if (vertexes[i].equals(v1)) {
				index_1 = i;
			}
			if (vertexes[i].equals(v2)) {
				index_2 = i;
			}
		}
		relations[index_1][index_2] = true;
		relations[index_2][index_1] = true;
		return;
	}

	@Override
	public boolean isAdjacent(Vertex v1, Vertex v2) {
		if (v1 == null || v2 == null) {
			System.out
			.println("The vertex you want to add is null");
			return false;
		}
		Vertex[] vertexes_1 = getVertices();
		boolean flag_1 = false;
		boolean flag_2 = false;
		for (int i = 0; i < vertexes_1.length; i++){
			if (vertexes_1[i].equals(v1)){
				flag_1 = true;
			}
			if (vertexes_1[i].equals(v2)){
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
		int index_1 = 0, index_2 = 0;
		for (int i = 0; i < numVers; i++) {
			if (vertexes[i].equals(v1)) {
				index_1 = i;
			}
			if (vertexes[i].equals(v2)) {
				index_2 = i;
			}
		}
		/* prevent weird bugs */
		return relations[index_1][index_2];
	}

	@Override
	public Vertex[] getNeighbors(Vertex v) {
		if (v == null) {
			System.out
			.println("The vertex you want to add is null");
			return new Vertex[0];
		}
		Vertex[] vertexes_1 = getVertices();
		boolean flag = false;
		for (int i = 0; i < vertexes_1.length; i++){
			if (vertexes_1[i].equals(v)){
				flag = true;
			}
		}
		if (flag == false){
			System.out
			.println(v.getLabel() + " is not in the graph!");
			return new Vertex[0];
		}
		int index = 0;
		int numNebs = 0;
		Vertex[] tmp = new Vertex[upperBoundNum - 1];
		/* find out the index of Vertex v in the vertexes array */
		for (int i = 0; i < numVers; i++) {
			if (vertexes[i].equals(v)) {
				index = i;
			}
		}
		/*
		 * fill in the tmp array and calculate the number of Vertex v's
		 * neighbors
		 */
		for (int j = 0; j < numVers; j++) {
			if (relations[index][j]) {
				tmp[numNebs++] = vertexes[j];
			}
		}
		/* define the result array with length numNebs, no nulls in it */
		Vertex[] result = new Vertex[numNebs];
		for (int k = 0; k < numNebs; k++) {
			result[k] = tmp[k];
		}

		return result;
	}

	@Override
	public Vertex[] getVertices() {
		/* make sure no nulls in the output array */
		Vertex[] result = new Vertex[numVers];
		for (int i = 0; i < numVers; i++) {
			result[i] = vertexes[i];
		}
		return result;
	}

}