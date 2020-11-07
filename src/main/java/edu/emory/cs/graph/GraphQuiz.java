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

        System.out.println("Done");
        for (List<Integer> lst: visited) {
            System.out.println(lst.toString());
        }
        return visited.size();
    }

    private void cycleCount(int current, Deque<Integer> notVisited, List<Integer> tried) {
        tried.add(current);

        System.out.println("out: " + tried.toString());
        for (Edge edge : getIncomingEdges(current)) {
            if (tried.get(0) == edge.getSource()) {
                System.out.println("in: " + tried.toString());
                tried.sort(Comparator.naturalOrder());
                HashSet<Integer>temp = new HashSet(tried);
                if (tried.size() == temp.size())
                    visited.add(new ArrayList<Integer>(tried));
                return;
            } else if (tried.size() >= size()) {
                break;
            }

            cycleCount(edge.getSource(), notVisited, new ArrayList<Integer>(tried));

        }

    }
}
