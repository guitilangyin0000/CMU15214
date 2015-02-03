package edu.cmu.cs.cs214.hw1.staff;

/**
 * This Graph interface represents an undirected graph. DO NOT MODIFY THIS FILE!
 */
public interface Graph {
    
	/**
	 * Adds a vertex to the graph.
	 *
	 * Precondition: v is not null and v is not already a vertex in the graph
	 */
	public void addVertex(Vertex v);
    
	/**
	 * Adds an edge to the graph.
	 *
	 * Precondition: v1 and v2 are vertices in the graph
	 */
	public void addEdge(Vertex v1, Vertex v2);
    
	/**
	 * Check if there is an edge between v1 and v2.
	 *
	 * Precondition: v1 and v2 are vertices in the graph
	 * Postcondition: return true iff an edge connects v1 and v2
	 */
	public boolean isAdjacent(Vertex v1, Vertex v2);
    
	/**
	 * Get an array containing all vertices adjacent to v.
	 *
	 * Precondition: v is a vertex in the graph
	 * Postcondition: returns an array where each vertex adjacent to v
	 *                appears exactly once. The size of the array must
	 *                be as small as possible (No trailing null elements).
	 *                This method should return an array of size 0 iff v
	 *                has no neighbors.
	 */
	public Vertex[] getNeighbors(Vertex v);
    
	/**
	 * Get all vertices in the graph.
	 *
	 * Postcondition: returns an array containing all vertices in the graph.
	 *                This method should return an array of size 0 iff the graph
	 *                has no vertices.
	 */
	public Vertex[] getVertices();
}
