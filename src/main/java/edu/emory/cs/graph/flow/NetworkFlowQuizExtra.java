package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.*;

/** @author Jinho D. Choi */
public class NetworkFlowQuizExtra {
    /**
     * Using breadth-first traverse.
     * @param graph  a directed graph.
     * @param source the ource vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        if (source == target) return null;
        Queue<Subgraph> queue = new LinkedList<>();
        Set<Integer> visited;
        Subgraph sub;
        Set<Subgraph> result = new HashSet<Subgraph>();
        Subgraph temp;
        int index;

        // adds incoming edges of source to queue
        for (Edge edge : graph.getIncomingEdges(target)) {
            sub = new Subgraph();
            sub.addEdge(edge);
            queue.offer(sub);
        }

        while (!queue.isEmpty()) {
            sub = new Subgraph(queue.poll());
            visited = sub.getVertices();
            index = sub.getEdges().size() - 1;
            for (Edge edge : graph.getIncomingEdges(sub.getEdges().get(index).getSource())) {
                if (visited.contains(edge.getSource())) continue;
                temp = new Subgraph(sub);
                temp.addEdge(edge);
                if (edge.getSource() == source) result.add(temp);
                else queue.offer(temp);
            }
        }
        return result;
    }
}