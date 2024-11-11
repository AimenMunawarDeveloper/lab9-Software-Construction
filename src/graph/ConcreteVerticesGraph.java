package graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

/**
 * ConcreteVerticesGraph represents a mutable weighted directed graph. Each vertex is represented by a unique label, and edges have non-negative weights.
 * The graph allows adding vertices and edges, removing vertices and edges, and querying the edges associated with a vertex.
 * 
 * Abstraction Function:
 *  - This graph is represented as a list of Vertex objects where each Vertex contains a label (String) and a set of outgoing edges, which are represented as a map of
 *    target vertex labels to edge weights.
 * 
 */
public class ConcreteVerticesGraph implements Graph<String> {
	/** Representation Invariant:
	 *  - The graph must contain only non-null vertices.
	 *  - Edge weights must be non-negative.
	 *  - The edges map for each vertex must not contain null values.
	 *  - The label for each vertex must be unique within the graph.
	 * 
	 * */
    private final List<Vertex> vertices = new ArrayList<>(); 
    public ConcreteVerticesGraph() {
        checkRep();
    }
    /**
     * Checks the representation invariant.Ensures all vertices are non-null and edge weights are non-negative.
     */
    private void checkRep() {  
        for (Vertex vertex : vertices) {
            assert vertex != null : "null elements can not be within vertices";
        }
    }
    @Override
    public boolean add(String vertexLabel) {
        for (Vertex vertex : vertices) {
            if (vertex.getLabel().equals(vertexLabel)) return false; // Check if the vertex already exists to prevent duplicates
        }
        vertices.add(new Vertex(vertexLabel));
        checkRep();
        return true;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        if (weight < 0) throw new IllegalArgumentException("Weight cannot be negative");

        Vertex sourceVertex = findVertex(source);         // Find or create source and target vertices
        if (sourceVertex == null) {
            sourceVertex = new Vertex(source);
            vertices.add(sourceVertex);
        }
        Vertex targetVertex = findVertex(target);
        if (targetVertex == null) {
            targetVertex = new Vertex(target);
            vertices.add(targetVertex);
        }
        int previousWeight = sourceVertex.setEdge(target, weight); // Set or update edge weight between source and target, returning previous weight
        checkRep();
        return previousWeight;
    }
    private Vertex findVertex(String label) {
        for (Vertex vertex : vertices) {
            if (vertex.getLabel().equals(label)) {
                return vertex;
            }
        }
        return null;
    }
    
    @Override
    public boolean remove(String vertexLabel) {
        Vertex vertex = findVertex(vertexLabel);
        if (vertex == null) return false;

        vertices.remove(vertex);
        for (Vertex v : vertices) {
            v.removeEdge(vertexLabel);
        }
        checkRep();
        return true;
    }
    
    @Override
    public Set<String> vertices() {
        Set<String> labels = new HashSet<>();
        for (Vertex vertex : vertices) {
            labels.add(vertex.getLabel());
        }
        return labels;
    }
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();   // Collect vertices with edges pointing to the target vertex
        for (Vertex vertex : vertices) {
            Integer weight = vertex.getEdgeWeight(target);
            if (weight != null) {
                sources.put(vertex.getLabel(), weight);
            }
        }
        return sources;
    }
    @Override
    public Map<String, Integer> targets(String source) {
        Vertex vertex = findVertex(source);  // Retrieve edges from the source vertex, if it exists
        if (vertex != null) {
            return vertex.getEdges();
        }
        return new HashMap<>();
    }
    @Override
    public String toString() {
        return "ConcreteVerticesGraph(vertices=" + vertices + ")";
    }
    /*Rep Exposure:
     *  - The internal representation of the graph is encapsulated through the use of a private `vertices` list and a `Map<String, Integer>` for each vertex.
     *  - We return copies of mutable collections (new HashSet<>(vertices) and new HashMap<>(edges)) to prevent external modification of the internal state of the graph.
     * */
}
/**
 * Vertex represents a vertex in a graph, maintaining a label and a map of outgoing edges.Each edge has a target vertex and an associated weight.
 * 
 * Abstraction Function:
 *  - Each Vertex object contains a unique label (String) and a map of outgoing edges,
 *    where each key is the target vertex label and the value is the edge weight.
 * 
 */
class Vertex {
	/*
	 * Representation Invariant:
	 *  - The label of the vertex must be non-null.
	 *  - The edge weights for each outgoing edge must be non-negative.
	 * */
    private final String label; 
    private final Map<String, Integer> edges = new HashMap<>(); 

    public Vertex(String label) {
        this.label = label;
        checkRep(); 
    }
    private void checkRep() {
        assert label != null : "Vertex label must not be null";
        for (Integer weight : edges.values()) {
            assert weight >= 0 : "Edge weights must be non-negative";
        }
    }
    public String getLabel() {
        return label;
    }
    public int setEdge(String target, int weight) { 
        // Sets an edge to the target with a specified weight; returns previous weight if it existed
        if (weight == 0) {
            Integer previousWeight = edges.remove(target);
            return previousWeight == null ? 0 : previousWeight;
        } else {
            Integer previousWeight = edges.put(target, weight);
            return previousWeight == null ? 0 : previousWeight;
        }
    }
    public Integer getEdgeWeight(String target) {
        // Retrieves the weight of an edge to a given target, if it exists
        return edges.get(target);
    }
    public Map<String, Integer> getEdges() {
        // Returns a copy of all edges from this vertex with their weights
        return new HashMap<>(edges);
    }
    public void removeEdge(String target) {
        // Removes an edge to a given target
        edges.remove(target);
    }
    @Override
    public String toString() {
        return "Vertex(" + label + "): " + edges;
    }
    /*
     * Rep Exposure:
     *  - The internal state of the Vertex is encapsulated with a private label and a private map for storing edges. We provide copies of mutable collections (edges) to prevent external modification.*/
}
