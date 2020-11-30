package edu.emory.cs.graph.span;


import edu.emory.cs.graph.Graph;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public interface MST {
    /**
     * @param graph a graph containing at least one spanning tree.
     * @return a minimum spanning tree.
     */
    SpanningTree getMinimumSpanningTree(Graph graph);
}