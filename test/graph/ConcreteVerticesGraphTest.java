package graph;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test class for ConcreteVerticesGraph functionality.
 * Extends GraphInstanceTest to inherit basic graph tests and
 * includes additional tests specific to ConcreteVerticesGraph and Vertex operations.
 * 
 * Testing Strategy:
 * The testing strategy focuses on verifying both the overall functionality of the ConcreteVerticesGraph and the specific behavior of individual vertices.
 * It includes two key tests:
 * 1. The "testConcreteVerticesGraphToString()" ensures that the string representation of the graph correctly lists the vertices and edges, 
 *    formatted as expected with the correct weights.
 * 2. The "testVertexOperations()" verifies that the "Vertex" class correctly handles adding and removing edges, checking if edge weights are 
 *    correctly set, retrieved, and removed (i.e., setting the weight to zero removes the edge).
 * The "emptyInstance()" method ensures that each test starts with a fresh, empty graph, promoting isolated and repeatable tests.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
	/**
     * Test the toString method of ConcreteVerticesGraph.Verifies that the string representation matches the expected format, showing vertices and edges.
     */
    @Test
    public void testConcreteVerticesGraphToString() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        
        String expected = "ConcreteVerticesGraph(vertices=[Vertex(A): {B=5}, Vertex(B): {}])";
        assertEquals("expected specific toString output for ConcreteVerticesGraph", expected, graph.toString());
    }
    /**
     * Test basic operations on a Vertex object.Verifies setting and retrieving edge weights, as well as removing edges by setting weight to zero.
     */
    @Test
    public void testVertexOperations() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        Vertex vertex = new Vertex("A");
        vertex.setEdge("B", 5);
        assertEquals("expected correct edge weight", Integer.valueOf(5), vertex.getEdgeWeight("B"));
        vertex.setEdge("B", 0);
        assertNull("expected edge to be removed", vertex.getEdgeWeight("B"));
    }
    /**
     * Provides an empty instance of ConcreteVerticesGraph.This method overrides the abstract method in GraphInstanceTest.
     */
	@Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
}
