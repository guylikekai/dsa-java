package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/** @author Jinho D. Choi */
public class MSTAllHW implements MSTAll {
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        SpanningTree tree = new SpanningTree();
        Set<Integer> visited = new HashSet<>();
        Edge edge;

        //for (graph.get)


        return null;
    }

    private SpanningTree getMST(PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph, int target) {
        SpanningTree tree = new SpanningTree();
        Edge edge;

        add(queue, visited, graph, target);

        while (!queue.isEmpty()) {
            edge = queue.poll();

            if (!visited.contains(edge.getSource())) {
                tree.addEdge(edge);
                // if a spanning tree is found, break.
                if (tree.size() + 1 == graph.size()) break;
                // add all connecting vertices from current vertex to the queue
                add(queue, visited, graph, edge.getSource());
            }
        }
        return null;
    }

    private void add(PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph, int target) {
        visited.add(target);
        for (Edge edge : graph.getIncomingEdges(target)) {
            if (!visited.contains(edge.getSource()))
                queue.add(edge);
        }

//        graph.getIncomingEdges(target).stream().
//                filter(edge -> !visited.contains(edge.getSource())).
//                collect(Collectors.toCollection(() -> queue));
    }
}