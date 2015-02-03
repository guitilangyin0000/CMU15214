package edu.cmu.cs.cs214.hw1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw1.graph.AdjacencyListGraph;
import edu.cmu.cs.cs214.hw1.graph.AdjacencyMatrixGraph;
import edu.cmu.cs.cs214.hw1.graph.Algorithms;
import edu.cmu.cs.cs214.hw1.staff.Graph;
import edu.cmu.cs.cs214.hw1.staff.Vertex;

public class GraphAlgorithmTest {
	private static final int MAX_VERTICES = 20;
	private Graph graphMatrix, graphList;
	private Vertex anne, ben, claire, don, paul, mary, philip, tom, jessie,
			steven, emily, kate, robert, lisa;

	/** Called before each test case method. */
	@Before
	public void setUp() throws Exception {
		// Start each test case method with a brand new Graph object.

		graphMatrix = new AdjacencyMatrixGraph(MAX_VERTICES);
		graphList = new AdjacencyListGraph(MAX_VERTICES);
		buildSampleGraph(graphMatrix);
		buildSampleGraph(graphList);
	}

	/** Called after each test case method. */
	@After
	public void tearDown() throws Exception {
		// Don't need to do anything here.
	}

	/** Tests that shortest distance is correct */
	@Test
	public void testShortestDistance_matrix() {
		// First argument is the expected value.
		// Second argument is the actual returned value.
		assertEquals(1, Algorithms.shortestDistance(graphMatrix, paul, mary));
		assertEquals(4, Algorithms.shortestDistance(graphMatrix, tom, robert));
		assertEquals(0, Algorithms.shortestDistance(graphMatrix, paul, paul));
		assertEquals(-1, Algorithms.shortestDistance(graphMatrix, ben, don));
		assertEquals(-1, Algorithms.shortestDistance(graphMatrix, ben, null));
		assertEquals(-1, Algorithms.shortestDistance(graphMatrix, null, don));
		assertEquals(-1, Algorithms.shortestDistance(null, ben, don));
		Vertex wang = new Vertex("wang");
		assertEquals(-1, Algorithms.shortestDistance(graphMatrix, ben, wang));
		assertEquals(-1, Algorithms.shortestDistance(graphMatrix, wang, ben));
		
	}

	@Test
	public void testShortestDistance_list() {
		assertEquals(1, Algorithms.shortestDistance(graphList, paul, mary));
		assertEquals(4, Algorithms.shortestDistance(graphList, tom, robert));
		assertEquals(0, Algorithms.shortestDistance(graphList, paul, paul));
		assertEquals(-1, Algorithms.shortestDistance(graphList, ben, don));
		assertEquals(-1, Algorithms.shortestDistance(graphList, ben, null));
		assertEquals(-1, Algorithms.shortestDistance(graphList, null, don));
		Vertex wang = new Vertex("wang");
		assertEquals(-1, Algorithms.shortestDistance(graphList, ben, wang));
		assertEquals(-1, Algorithms.shortestDistance(graphList, wang, ben));
	}
	
	@Test
	public void testCommonFriends_matrix() {
		/*
		 * try to compare the common friends between the real and the expected
		 * one
		 */
		Vertex[] tom_philip_real = new Vertex[1];
		tom_philip_real[0] = jessie;
		Vertex[] tom_philip_test = Algorithms.commonFriends(graphMatrix, tom,
				philip);
		assertEquals(tom_philip_real[0], tom_philip_test[0]);
		assertEquals(tom_philip_real.length, tom_philip_test.length);

		Vertex[] jessie_mary_real = new Vertex[2];
		jessie_mary_real[0] = philip;
		jessie_mary_real[1] = emily;
		Vertex[] jessie_mary_test = Algorithms.commonFriends(graphMatrix, mary,
				jessie);
		assertEquals(jessie_mary_real.length, jessie_mary_test.length);
		/* we should take care about the different output sequence between 
		 * the matrix method and list method
		 */
		assertEquals(jessie_mary_real[0], jessie_mary_test[0]);
		assertEquals(jessie_mary_real[1], jessie_mary_test[1]);
		

		Vertex[] ben_don_test = Algorithms.commonFriends(graphMatrix, ben, don);
		assertEquals(0, ben_don_test.length);

		Vertex[] don_claire_test = Algorithms.commonFriends(graphMatrix, don,
				claire);
		assertEquals(0, don_claire_test.length);

		Vertex[] tom_tom_test = Algorithms.commonFriends(graphMatrix, tom, tom);
		assertEquals(0, tom_tom_test.length);

		Vertex[] steven_philip_real = new Vertex[1];
		steven_philip_real[0] = jessie;
		Vertex[] steven_philip_test = Algorithms.commonFriends(graphMatrix,
				philip, steven);
		assertEquals(steven_philip_real[0], steven_philip_test[0]);
		assertEquals(steven_philip_real.length, steven_philip_test.length);
		
		assertEquals(0, Algorithms.commonFriends(graphMatrix,
				null, steven).length);
		assertEquals(0, Algorithms.commonFriends(graphMatrix,
				steven, null).length);
		assertEquals(0, Algorithms.commonFriends(null,
				steven, tom).length);
		
		Vertex may = new Vertex("may");
		
		assertEquals(0, Algorithms.commonFriends(graphMatrix,
				may, steven).length);
		assertEquals(0, Algorithms.commonFriends(graphMatrix,
				steven, may).length);

	}

	@Test
	public void testCommonFriends_list() {
		/*
		 * try to compare the common friends between the real and the expected
		 * one
		 */
		Vertex[] tom_philip_real = new Vertex[1];
		tom_philip_real[0] = jessie;
		Vertex[] tom_philip_test = Algorithms.commonFriends(graphList, tom,
				philip);
		assertEquals(tom_philip_real[0], tom_philip_test[0]);
		assertEquals(tom_philip_real.length, tom_philip_test.length);

		Vertex[] jessie_mary_real = new Vertex[2];
		jessie_mary_real[0] = philip;
		jessie_mary_real[1] = emily;
		Vertex[] jessie_mary_test = Algorithms.commonFriends(graphList, mary,
				jessie);
		assertEquals(jessie_mary_real.length, jessie_mary_test.length);
		/* we should take care about the different output sequence between 
		 * the matrix method and list method
		 */
		assertEquals(jessie_mary_real[1], jessie_mary_test[0]);
		assertEquals(jessie_mary_real[0], jessie_mary_test[1]);

		
		Vertex[] ben_don_test = Algorithms.commonFriends(graphList, ben, don);
		assertEquals(0, ben_don_test.length);

		Vertex[] don_claire_test = Algorithms.commonFriends(graphList, don,
				claire);
		assertEquals(0, don_claire_test.length);

		Vertex[] tom_tom_test = Algorithms.commonFriends(graphList, tom, tom);
		assertEquals(0, tom_tom_test.length);

		Vertex[] steven_philip_real = new Vertex[1];
		steven_philip_real[0] = jessie;
		Vertex[] steven_philip_test = Algorithms.commonFriends(graphList,
				philip, steven);
		assertEquals(steven_philip_real[0], steven_philip_test[0]);
		assertEquals(steven_philip_real.length, steven_philip_test.length);
		
		assertEquals(0, Algorithms.commonFriends(graphList,
				null, steven).length);
		assertEquals(0, Algorithms.commonFriends(graphList,
				steven, null).length);
		
		Vertex may = new Vertex("may");
		
		assertEquals(0, Algorithms.commonFriends(graphList,
				may, steven).length);
		assertEquals(0, Algorithms.commonFriends(graphList,
				steven, may).length);
	}
	
	@Test
	public void testSuggestFriend_matrix() {
		/*
		 * try to compare the suggested friend between the real and the expected
		 * one
		 */
		assertEquals(mary, Algorithms.suggestFriend(graphMatrix, jessie));
		assertEquals(philip, Algorithms.suggestFriend(graphMatrix, emily));
		assertEquals(null, Algorithms.suggestFriend(null, emily));
		assertEquals(null, Algorithms.suggestFriend(graphMatrix, null));
		Vertex ali = new Vertex("ali");
		assertEquals(null, Algorithms.suggestFriend(graphMatrix, ali));
		
	}
	
	@Test
	public void testSuggestFriend_list() {
		/*
		 * try to compare the suggested friend between the real and the expected
		 * one
		 */
		assertEquals(mary, Algorithms.suggestFriend(graphList, jessie));
		assertEquals(philip, Algorithms.suggestFriend(graphList, emily));
		assertEquals(null, Algorithms.suggestFriend(graphList, null));
		Vertex ali = new Vertex("ali");
		assertEquals(null, Algorithms.suggestFriend(graphList, ali));
	}

	@Test
	public void testException_matrix() {
		/* try to add the edge of the same person */
		graphMatrix.addEdge(paul, paul);
		/* try to determine whether the same person is adjacent to himself */
		assertEquals(false, graphMatrix.isAdjacent(paul, paul));
		/* try to add the edge of the already connected persons */
		graphMatrix.addEdge(mary, paul);
		/*
		 * try to determine whether the illegal action has an effect on the
		 * relationship
		 */
		assertEquals(true, graphMatrix.isAdjacent(mary, paul));
		assertEquals(false, graphMatrix.isAdjacent(null, paul));
		assertEquals(false, graphMatrix.isAdjacent(paul, null));
		Vertex zhang = new Vertex("zhang");
		assertEquals(false, graphMatrix.isAdjacent(zhang, paul));
		assertEquals(false, graphMatrix.isAdjacent(paul, zhang));
		assertEquals(0, graphMatrix.getNeighbors(null).length);
		assertEquals(0, graphMatrix.getNeighbors(zhang).length);
		/*
		 * try to find out what happens if the number of vertexes exceeds the
		 * maximum value
		 */
		Vertex a = new Vertex("a");
		Vertex b = new Vertex("b");
		Vertex c = new Vertex("c");
		Vertex d = new Vertex("d");
		Vertex e = new Vertex("e");
		Vertex f = new Vertex("f");
		Vertex g = new Vertex("g");
		graphMatrix.addVertex(a);
		graphMatrix.addVertex(b);
		graphMatrix.addVertex(c);
		graphMatrix.addVertex(d);
		graphMatrix.addVertex(e);
		graphMatrix.addVertex(f);
		graphMatrix.addVertex(g); /* try to add the 21st vertex into the graph */
		assertEquals(MAX_VERTICES, graphMatrix.getVertices().length);
		
		Vertex vertex_1 = new Vertex("vertex_1");
		Vertex vertex_2 = new Vertex("vertex_2");
		assertEquals(false, vertex_1.toString().equals(vertex_2.toString()));
		assertEquals(false, vertex_1.hashCode() == vertex_2.hashCode());
		assertEquals(false, vertex_1.equals('a'));
	}
	
	@Test
	public void testException_list() {
		/* try to add the edge of the same person */
		graphList.addEdge(paul, paul);
		/* try to determine whether the same person is adjacent to himself */
		assertEquals(false, graphList.isAdjacent(paul, paul));
		/* try to add the edge of the already connected persons */
		graphList.addEdge(mary, paul);
		/*
		 * try to determine whether the illegal action has an effect on the
		 * relationship
		 */
		assertEquals(true, graphList.isAdjacent(mary, paul));
		assertEquals(false, graphList.isAdjacent(null, paul));
		assertEquals(false, graphList.isAdjacent(paul, null));
		Vertex zhang = new Vertex("zhang");
		assertEquals(false, graphList.isAdjacent(zhang, paul));
		assertEquals(false, graphList.isAdjacent(paul, zhang));
		assertEquals(0, graphList.getNeighbors(null).length);
		assertEquals(0, graphList.getNeighbors(zhang).length);
		/*
		 * try to find out what happens if the number of vertexes exceeds the
		 * maximum value
		 */
		Vertex a = new Vertex("a");
		Vertex b = new Vertex("b");
		Vertex c = new Vertex("c");
		Vertex d = new Vertex("d");
		Vertex e = new Vertex("e");
		Vertex f = new Vertex("f");
		Vertex g = new Vertex("g");
		graphList.addVertex(a);
		graphList.addVertex(b);
		graphList.addVertex(c);
		graphList.addVertex(d);
		graphList.addVertex(e);
		graphList.addVertex(f);
		graphList.addVertex(g); /* try to add the 21st vertex into the graph */
		assertEquals(MAX_VERTICES, graphList.getVertices().length);
	}

	/**
	 * build a sample friend graph for testing
	 * 
	 * @param graph
	 *            the graph to be built
	 */
	private void buildSampleGraph(Graph graph) {
		paul = new Vertex("Paul");
		mary = new Vertex("Mary");
		philip = new Vertex("Philip");
		tom = new Vertex("Tom");
		jessie = new Vertex("Jessie");
		steven = new Vertex("Steven");
		emily = new Vertex("Emily");
		kate = new Vertex("Kate");
		robert = new Vertex("Robert");
		lisa = new Vertex("Lisa");
		anne = new Vertex("Anne");
		ben = new Vertex("Ben");
		claire = new Vertex("Claire");
		don = new Vertex("Don");
		Vertex yang = new Vertex("yang");
		graph.addVertex(null);
		graph.addVertex(paul);
		graph.addVertex(mary);
		graph.addVertex(philip);
		graph.addVertex(tom);
		graph.addVertex(jessie);
		graph.addVertex(steven);
		graph.addVertex(robert);
		graph.addVertex(emily);
		graph.addVertex(kate);
		graph.addVertex(lisa);
		graph.addVertex(lisa);
		graph.addEdge(paul, mary);
		graph.addEdge(null, mary);
		graph.addEdge(mary, null);
		graph.addEdge(paul, yang);
		graph.addEdge(yang, mary);
		graph.addEdge(mary, philip);
		graph.addEdge(philip, tom);
		graph.addEdge(philip, jessie);
		graph.addEdge(tom, jessie);
		graph.addEdge(jessie, steven);
		graph.addEdge(jessie, emily);
		graph.addEdge(emily, mary);
		graph.addEdge(emily, lisa);
		graph.addEdge(paul, robert);
		graph.addEdge(paul, kate);
		graph.addEdge(robert, kate);
		graph.addVertex(anne);
		graph.addVertex(ben);
		graph.addVertex(claire);
		graph.addVertex(don);
		graph.addEdge(anne, ben);
		graph.addEdge(ben, claire);
	}

}
