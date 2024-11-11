package graph;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Collections;
import java.util.Set;
import java.util.Map;

/**
 * Abstract class providing a set of test cases for any implementation of the Graph interface.Subclasses must implement the emptyInstance method to provide a new, empty graph instance.
 *
 * Testing Strategy:
 * The testing strategy focuses on verifying the core operations of the Graph interface implementation. The strategy includes the following test cases:
 * 
 * 1. testAssertionsAreEnabled(): Ensures that assertions are enabled in the environment by testing an intentional failure.
 * 2. testGraphStartsWithNoVertices(): Verifies that a new graph instance starts with no vertices.
 * 3. testAddNewVertex(): Checks that adding a new vertex is successful and correctly updates the vertex set.
 * 4. testPreventDuplicateVertex()**: Ensures that duplicate vertices cannot be added to the graph.
 * 5. testAddAndUpdateEdgeWeight()**: Validates that edge weights can be added, updated, and removed correctly. Ensures the previous weight is returned when an edge is updated or removed.
 * 6. testRemoveVertexAndRelatedEdges(): Verifies that removing a vertex also removes it from the graph and removes all related edges.
 * 7. testCorrectSourcesAndTargetsForEdges(): Checks the correct retrieval of source and target vertices along with their associated edge weights.
 * 
 * The tests are designed to ensure that the graph operations work as expected, including vertex and edge manipulation, removal of vertices and edges, and correct handling of source/target relationships. Each test checks for edge cases like duplicate vertices or zero-weighted edges, ensuring the robustness of the implementation.
 */
public abstract class GraphInstanceTest {
	// Abstract method to be implemented by subclasses to provide a new, empty graph instance.
    public abstract Graph<String> emptyInstance();
    // This test will fail if assertions are not enabled, ensuring a properly configured environment.
    @Test(expected=AssertionError.class)
    public void testAssertionsAreEnabled() {
        assert false; 
    }
    // Test that a new graph starts with no vertices.Verifies that calling vertices() on a new graph instance returns an empty set.
    @Test
    public void testGraphStartsWithNoVertices() {
        assertEquals("A new graph should have no vertices initially", Collections.emptySet(), emptyInstance().vertices());
    }
    // Test that adding a new vertex succeeds.Checks that the vertex is successfully added to the graph's vertex set.
    @Test
    public void testAddNewVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("Adding a new vertex should succeed", graph.add("A"));
        assertEquals("The vertex set should contain the added vertex", Set.of("A"), graph.vertices());
    }
    // Test that duplicate vertices are not added.Ensures that adding the same vertex twice returns false and does not duplicate it in the vertex set.
    @Test
    public void testPreventDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertFalse("Adding a duplicate vertex should fail", graph.add("A"));
        assertEquals("The vertex set should contain only one instance of the vertex", Set.of("A"), graph.vertices());
    }
    // Test adding and updating an edge with a weight.Verifies the correct previous weight is returned when setting new and existing edges, and that setting the weight to zero removes the edge.
    @Test
    public void testAddAndUpdateEdgeWeight() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        
        int previousWeight = graph.set("A", "B", 5);
        assertEquals("The previous weight of the edge should be zero when adding a new edge", 0, previousWeight);
        
        previousWeight = graph.set("A", "B", 10);
        assertEquals("The previous weight of the edge should be 5 when updating the edge", 5, previousWeight);
        previousWeight = graph.set("A", "B", 0);
        assertEquals("The previous weight of the edge should be 10 when removing the edge", 10, previousWeight);
        assertTrue("The edge should be removed when the weight is set to zero", graph.targets("A").isEmpty());
    }
    // Test removing a vertex and its related edges.Verifies that a removed vertex is no longer in the graph, and that all its connected edges are also removed.
    @Test
    public void testRemoveVertexAndRelatedEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        assertTrue("Removing a vertex should succeed", graph.remove("A"));
        assertFalse("The vertex should be removed from the graph", graph.vertices().contains("A"));
        assertTrue("All edges related to the removed vertex should be removed", graph.sources("B").isEmpty());
    }
    // Test the correct retrieval of sources and targets for edges.Ensures that the graph correctly returns the sources and targets with appropriate weights for a given vertex.
    @Test
    public void testCorrectSourcesAndTargetsForEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 5);
        graph.set("C", "B", 3);
        
        Map<String, Integer> sources = graph.sources("B");
        assertEquals("There should be two sources for vertex B", Map.of("A", 5, "C", 3), sources);

        Map<String, Integer> targets = graph.targets("A");
        assertEquals("There should be one target for vertex A", Map.of("B", 5), targets);
    }
}
