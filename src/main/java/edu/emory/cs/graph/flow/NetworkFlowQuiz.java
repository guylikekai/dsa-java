package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** @author Jinho D. Choi */
public class NetworkFlowQuiz {
    /**
     * Using depth-first traverse.
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> result = new HashSet<Subgraph>();
        Subgraph sub = new Subgraph();

        getPaths(graph, sub, result, source, target);

        return result;
    }

    public void getPaths(Graph graph, Subgraph sub, Set<Subgraph> set, int source, int target) {
        if (source == target) {
            set.add(sub);
            return;
        }
        Subgraph tmp;

        for (Edge edge : graph.getIncomingEdges(target)) {
            if (sub.contains(edge.getSource())) continue;
            tmp = new Subgraph(sub);
            tmp.addEdge(edge);
            getPaths(graph, tmp, set, source, edge.getSource());
        }
        return;

    }
}