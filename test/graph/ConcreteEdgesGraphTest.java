/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test class for ConcreteEdgesGraph and its Edge operations.Extends GraphInstanceTest to inherit general graph tests and includes additional tests specific to ConcreteEdgesGraph and Edge functionality.
 *
 * Testing Strategy:
 * The testing strategy involves verifying both the graph's overall functionality and the specific behavior of edges.
 * The test checks that vertices can be added to the graph, edges can be set with the correct weight, and the graph's 
 * string representation matches the expected format. Additionally, the test ensures that the "Edge" class correctly 
 * represents the source, target, and weight of edges. The test uses assertions to confirm that the actual outcomes 
 * match the expected results for graph structure and edge properties. The `emptyInstance()` method provides a fresh 
 * graph instance for each test to ensure isolated tests.
 *
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
	/**
     * Test operations on both the graph and individual edges.Verifies adding vertices, setting edges, and the expected string representation.
    */
    @Test
    public void testGraphAndEdgeOperations() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");

        graph.set("A", "B", 5);
        
        String expectedGraphString = "ConcreteEdgesGraph(vertices=[A, B], edges=[Edge(A -> B, weight=5)])";
        assertEquals("Graph toString representation should match the expected format", expectedGraphString, graph.toString());

        Edge edge = new Edge("A", "B", 5);
        assertEquals("Source of the edge should be 'A'", "A", edge.getSource());
        assertEquals("Target of the edge should be 'B'", "B", edge.getTarget());
        assertEquals("Weight of the edge should be 5", 5, edge.getWeight());
    }
    /**
     * Provides an empty instance of ConcreteEdgesGraph.This method overrides the abstract method in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
}
