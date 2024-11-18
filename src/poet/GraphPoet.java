/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.*;
import java.io.IOException;

import graph.ConcreteEdgesGraph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
	private final ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
	 
	// Abstraction function: 
    //   Graph represents word relationships with weighted edges.
    
    // Representation invariant: 
    //   All words are non-null and non-empty; edge weights are positive.
    
    // Safety from rep exposure: 
    //   Internal graph is hidden, accessed through controlled methods.
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
	public GraphPoet(File corpusFile) throws IOException {
	    try (BufferedReader fileReader = new BufferedReader(new FileReader(corpusFile))) {
	        String currentLine;
	        String previousWord = null;
	        
	        while ((currentLine = fileReader.readLine()) != null) {
	            // Split the line into words
	            for (String currentWord : currentLine.split("\\s+")) {
	                currentWord = currentWord.toLowerCase().trim();
	                
	                if (!currentWord.isEmpty()) {
	                    graph.add(currentWord);
	                    // If there's a previous word, create or update the edge between previousWord and currentWord
	                    if (previousWord != null) {
	                        int edgeWeight = graph.set(previousWord, currentWord, graph.targets(previousWord).getOrDefault(currentWord, 0) + 1);
	                    }
	                    previousWord = currentWord;
	                }
	            }
	        }
	    }
	    checkRep();
	}

    // TODO checkRep
    private void checkRep() {
        for (String vertex : graph.vertices()) {
            assert vertex != null && !vertex.isEmpty();
            for (int weight : graph.targets(vertex).values()) {
                assert weight > 0;
            }
        }
    }
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String inputText) {
        String[] wordList = inputText.split("\\s+");
        StringBuilder poemBuilder = new StringBuilder();
        
        for (int index = 0; index < wordList.length - 1; index++) {
            String firstWord = wordList[index].toLowerCase();
            String secondWord = wordList[index + 1].toLowerCase();

            String bestBridgeWord = null;
            int highestWeight = 0;

            // Look for the best bridge word between firstWord and secondWord
            for (String potentialBridge : graph.targets(firstWord).keySet()) {
                if (graph.targets(potentialBridge).containsKey(secondWord)) {
                    // Calculate the total weight of the two-edge path (firstWord -> potentialBridge -> secondWord)
                    int currentWeight = graph.targets(firstWord).get(potentialBridge) + graph.targets(potentialBridge).get(secondWord);
                    if (currentWeight > highestWeight) {
                        highestWeight = currentWeight;
                        bestBridgeWord = potentialBridge;
                    }
                }
            }

            poemBuilder.append(wordList[index]).append(" ");
            
            // If a bridge word is found, append it to the poem
            if (bestBridgeWord != null) {
                poemBuilder.append(bestBridgeWord).append(" ");
            }
        }

        // Append the last word without a bridge
        poemBuilder.append(wordList[wordList.length - 1]);
        return poemBuilder.toString();
    }

    
    // TODO toString()
    @Override
    public String toString() {
        return graph.toString();
    }
}
