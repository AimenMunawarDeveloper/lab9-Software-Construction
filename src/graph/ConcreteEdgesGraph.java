package graph;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/**
 * ConcreteEdgesGraph represents a weighted directed graph where:
 * - The graph contains vertices represented by String labels.
 * - The edges have weights and connect two vertices (source -> target).
 * 
 * Abstraction Function (AF):
 * - A ConcreteEdgesGraph represents a graph with vertices (unique labels) and directed, weighted edges between them.
 * - The graph’s vertices are stored in a set (`vertices`), and the edges are stored as a list (`edges`), where each edge is represented by an instance of the `Edge` class.
 * 
 */
public class ConcreteEdgesGraph implements Graph<String> {
    // Representation Invariant:
    // - vertices is a Set of String labels representing the vertices in the graph.
    // - edges is a List of Edge objects, where each Edge contains source, target, and weight attributes.
    // - Every edge's source and target must be present in the vertices set. This invariant ensures consistency between vertices and edges.
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    /**
     * Constructs an empty graph and verifies its representation invariant.
     */
    public ConcreteEdgesGraph() {
        checkRep();
    }
    /**
     * Checks the representation invariant for the graph.
     * Ensures that all edges reference valid vertices that exist in the vertices set.
     */
    private void checkRep() {
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource()) && vertices.contains(edge.getTarget()) 
                   : "Edge vertices must exist in the vertices set";
        }
    }
    /**
     * Adds a vertex to the graph.
     * If the vertex already exists, it does not get added again.
     * 
     * @param vertex The vertex to add.
     * @return True if the vertex was added, false if it already exists.
     */
    @Override
    public boolean add(String vertex) {
        if (vertex == null) throw new IllegalArgumentException("Vertex cannot be null");
        boolean added = vertices.add(vertex);
        checkRep();
        return added;
    }
    /**
     * Sets an edge between the source and target vertices with a specified weight.
     * If the edge already exists, it replaces the existing weight and returns the previous weight.
     * 
     * @param source The source vertex.
     * @param target The target vertex.
     * @param weight The weight of the edge.
     * @return The previous weight of the edge, or 0 if it did not exist.
     */
    @Override
    public int set(String source, String target, int weight) {
        if (source == null || target == null) throw new IllegalArgumentException("Vertices cannot be null");

        int prevWeight = 0;
        // Remove existing edge if it exists
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                prevWeight = edge.getWeight();
                edges.remove(edge);
                break;
            }
        }
        
        // Add new edge if weight is greater than 0
        if (weight > 0) {
            edges.add(new Edge(source, target, weight));
            vertices.add(source);
            vertices.add(target);
        }

        checkRep();
        return prevWeight;
    }
    /**
     * Removes a vertex from the graph and all associated edges.
     * 
     * @param vertex The vertex to remove.
     * @return True if the vertex was removed, false if it did not exist.
     */
    @Override
    public boolean remove(String vertex) {
        boolean removed = vertices.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        checkRep();
        return removed;
    }
    /**
     * Returns a Set of all vertices in the graph.
     * 
     * @return A Set containing all vertices in the graph.
     */
    @Override
    public Set<String> vertices() {
        return new HashSet<>(vertices); // Prevents rep exposure by returning a copy
    }
    /**
     * Returns a map of source vertices and their respective edge weights for a given target vertex.
     * 
     * @param target The target vertex.
     * @return A Map where keys are source vertices and values are edge weights.
     */
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return sources;
    }
    /**
     * Returns a map of target vertices and their respective edge weights for a given source vertex.
     * 
     * @param source The source vertex.
     * @return A Map where keys are target vertices and values are edge weights.
     */
    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targets;
    }
    /**
     * Provides a human-readable string representation of the graph.
     * Includes the vertices and edges in the graph.
     * 
     * @return A string representing the graph.
     */
    @Override
    public String toString() {
        return "ConcreteEdgesGraph(vertices=" + vertices + ", edges=" + edges + ")";
    }
    /**Prevention of Rep Exposure:
     * The ConcreteEdgesGraph class prevents exposure of its internal representation by returning copies of mutable collections (new HashSet<>(vertices) and new HashMap<>()). 
     * This prevents external code from modifying the internal vertices and edges directly.
     */
}

/**
 * Represents an edge in the graph, with a source vertex, a target vertex, and a weight.
 * The weight must be non-negative.
 * 
 * Abstraction Function (AF):
 * - An Edge represents a directed, weighted connection from one vertex (source) to another (target).
 * - The edge has a weight associated with it that represents the cost or strength of the connection.
 * 
 * The Edge class ensures that the source and target are valid (non-null) and the weight is non-negative through its representation invariant.
 */
class Edge {
	/*
	 * Representation Invariant (Rep Inv):
	 * - The source and target vertices must not be null.
	 * - The weight must be a non-negative integer.*/
    private final String source;
    private final String target;
    private final int weight;

    /**
     * Constructs an edge with the given source, target, and weight.
     * Ensures that the weight is non-negative.
     * 
     * @param source The source vertex.
     * @param target The target vertex.
     * @param weight The weight of the edge.
     */
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    /**
     * Checks the representation invariant for the edge.Ensures that source and target are non-null and weight is non-negative.
     */
    private void checkRep() {
        assert source != null && target != null : "Source and target must not be null";
        assert weight >= 0 : "Weight must be non-negative";
    }
    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }
    /**
     * Provides a string representation of the edge.
     * 
     * @return A string representing the edge.
     */
    @Override
    public String toString() {
        return String.format("Edge(%s -> %s, weight=%d)", source, target, weight);
    }
}
