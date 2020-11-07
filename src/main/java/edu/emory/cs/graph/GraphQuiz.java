package edu.emory.cs.graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** @author Jinho D. Choi */
public class GraphQuiz extends Graph {
    public GraphQuiz(int size) { super(size); }
    public GraphQuiz(Graph g) { super(g); }
    private HashSet<List<Integer>> visited = new HashSet<>();
    /** @return the total number of cycles in this graph. */
    public int numberOfCycles() {
        Deque<Integer> notVisited = IntStream.range(0, size()).boxed().collect(Collectors.toCollection(ArrayDeque::new));
        List<List<Integer>> result = new ArrayList<>();

        while (!notVisited.isEmpty()) {
            cycleCount(notVisited.poll(), notVisited, new ArrayList<>());
        }


        visited = new HashSet<>(visited);

        int counter = 0;
        for (List<Integer> lst : visited) {
            boolean hi = true;
            if (lst.size()>2) {
                for (int i = 0; i < lst.size() - 1; i++) {
                    if (!undirectedEdge(lst.get(i), lst.get(i+1))) {
                        hi = false;
                    }
                }
            } else continue;

            if (hi) counter++;
        }
        return visited.size() + counter;
    }

    private void cycleCount(int current, Deque<Integer> notVisited, List<Integer> tried) {
        tried.add(current);

        for (Edge edge : getIncomingEdges(current)) {
            if (tried.get(0) == edge.getSource()) {
                tried.sort(Comparator.naturalOrder());
                HashSet<Integer>temp = new HashSet(tried);
                if (tried.size() == temp.size()){
                    visited.add(new ArrayList<Integer>(tried));
                    return;
                }

            } else if (tried.size() >= size()) {
                break;
            }

            cycleCount(edge.getSource(), notVisited, new ArrayList<Integer>(tried));

        }

    }

    private boolean undirectedEdge (int i, int j) {
        boolean first = false;
        boolean second = false;
        for (Edge edge : getIncomingEdges(i)) {
            if (edge.getSource() == j) first = true;
        }
        for (Edge edge : getIncomingEdges(j)) {
            if (edge.getSource() == i) second = true;
        }
        boolean result = false;
        if (first && second) result = true;

        return result;
    }
}
