/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

package poet;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;

/**
 * Tests for GraphPoet.
 * This class contains various unit tests for the GraphPoet class, ensuring that it functions as expected in different scenarios. The tests verify the
 * correct construction of the word graph, the poem generation with and without bridge words, and the proper handling of edge cases.
 */

public class GraphPoetTest {
    
    // Testing strategy
    // Tests will cover the following aspects:
    // 1. Correct handling of input files for graph construction
    // 2. Correct generation of poems with and without bridge words
    // 3. Proper validation for edge cases such as empty or simple input strings
    // 4. Ensuring that assertions are enabled and functioning
	
    @Test(expected = AssertionError.class)
    public void testAssertionsAreEnabled() {
        assert false; // This line will trigger an AssertionError if assertions are enabled.
    }
    @Test
    public void testEmptyInput() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/sample.txt"));
        String input = "";
        String output = poet.poem(input);
        
        // Since the input is empty, the output should also be empty.
        assertEquals("The output for an empty input should also be empty.", "", output);
    }
    

    // Test for constructing the graph from a file. The graph should correctly represent words and edges derived from the sample text file.
    @Test
    public void testGraphConstructionFromFile() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/sample.txt"));
        String graph = poet.toString();
        
        // Check if the graph contains the expected vertices and edges.In this case, we expect the word "aimen" to be a vertex in the graph. We also expect an edge from "aimen" to "munawar".
        assertTrue("Graph should contain the word 'aimen' as a vertex", graph.contains("aimen"));
        assertTrue("Graph should contain an edge from 'aimen' to 'munawar'", graph.contains("aimen -> munawar"));
    }
    // Test for generating a poem with bridge words. In this case, the input string contains words that can be bridged by words from the graph, so the poem
    // should have bridge words inserted between adjacent words.
    @Test
    public void testPoemWithBridgeWords() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/sample.txt"));
        String input = "Test the system.";
        String output = poet.poem(input);
        
        // In this example, the word "of" should be inserted as a bridge word between "Test" and "the" based on the graph data.
        assertEquals("Poem with bridge words", "Test of the system.", output);
    }
    // Test for generating a poem without bridge words. In this case, the input poem has no adjacent words with a bridge word between them, so the output should
    // remain unchanged from the input.
    @Test
    public void testPoemWithoutBridgeWords() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/sample.txt"));  
        String input = "life is exciting";
        String output = poet.poem(input);
        
        // Since there are no bridge words to insert, the output should be the same as the input string.
        assertEquals("The input poem should remain unchanged as there are no bridge words to modify it.", 
                     "life is exciting", output);
    }
    

}
