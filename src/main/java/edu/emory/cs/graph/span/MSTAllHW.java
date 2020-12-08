package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;
import edu.emory.cs.set.DisjointSet;


import java.util.*;

/** @author Jinho D. Choi */
public class MSTAllHW implements MSTAll {
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        List<SpanningTree> result = new ArrayList<>();
        List<Edge> array = new ArrayList<>();
        List<Integer> vertices = new ArrayList<>();

        for (int i = 0; i < graph.size(); i++) {
            vertices.add(i);
        }

        heapPermutation(vertices, vertices.size(), graph, result);


        for (int z = 0; z < graph.size(); z++) {
            for (int i = 0; i < result.size(); i++) {
                for (int j = i + 1; j < result.size(); j++) {
                    array.clear();
                    array.addAll(result.get(j).getEdges());
                    for (Edge edge : result.get(i).getEdges()) {
                        for (int k = 0; k < array.size(); k++) {
                            if (isSameEdge(edge, array.get(k))) array.remove(k);
                        }
                    }
                    if (array.isEmpty()) result.remove(j);
                }
            }
        }

        return result;
    }

    private void getAll(Graph graph, Subgraph sub, List<Integer> missing, List<SpanningTree> result) {
        if (missing.isEmpty()) {
            SpanningTree tree = new SpanningTree();
            for (Edge edge : sub.getEdges()) {
                tree.addEdge(edge);
            }
            if (!result.contains(tree)) result.add(tree);
            return;
        }

        int target = missing.get(0);

        for (Edge edge : graph.getIncomingEdges(target)) {
            if (sub.getVertices().contains(edge.getSource())) {
                List<Integer> temp = missing.subList(1, missing.size());
                Subgraph temp1 = new Subgraph(sub);
                temp1.addEdge(edge);
                getAll(graph, temp1, temp, result);
            } else continue;
        }
        return;
    }

    void heapPermutation(List<Integer> lst, int size, Graph graph, List<SpanningTree> result)
    {
        if (size == 1) {
            Subgraph sub;
                for (Edge edge : graph.getIncomingEdges(lst.get(0))) {
                    sub = new Subgraph();
                    sub.addEdge(edge);
                    List<Integer> temp = new ArrayList<>(lst);
                    temp.remove(temp.indexOf(edge.getSource()));
                    temp.remove(0);
                    getAll(graph, sub, temp, result);
                }

        }

        for (int i = 0; i < size; i++) {
            heapPermutation(lst, size - 1, graph, result);

            if (size % 2 == 1) {
                int temp = lst.get(0);
                lst.set(0, lst.get(size-1));
                lst.set(size - 1, temp);
            }

            else {
                int temp = lst.get(i);
                lst.set(i, lst.get(size - 1));
                lst.set(size-1, temp);
            }
        }
    }

    private SpanningTree getMST(Graph graph, List<Edge> queue) {
        DisjointSet forest = new DisjointSet(graph.size());
        SpanningTree tree = new SpanningTree();

        while (!queue.isEmpty()) {
            Edge edge = queue.remove(0);

            if (!forest.inSameSet(edge.getTarget(), edge.getSource())) {
                tree.addEdge(edge);

                // a spanning tree is found
                if (tree.size() + 1 == graph.size()) break;
                // merge forests
                forest.union(edge.getTarget(), edge.getSource());
            }
        }

        return tree;
    }

    private void add(PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph, int target) {
        visited.add(target);
        for (Edge edge : graph.getIncomingEdges(target)) {
            if (!visited.contains(edge.getSource()))
                queue.add(edge);
        }
    }

    private boolean isSameEdge (Edge edge1, Edge edge2) {
        int source1, source2, target1, target2;
        source1 = edge1.getSource();
        target1 = edge1.getTarget();
        source2 = edge2.getSource();
        target2 = edge2.getTarget();

        if ((source1 == source2 && target1 == target2) || (source1 == target2 && target1 == source2)) return true;
        return false;


    }

    private double getMaxWeight(SpanningTree tree) {
        double max = 0;
        for (Edge edge : tree.getEdges()) if (edge.getWeight() > max) max = edge.getWeight();
        return max;
    }

    public static void main(String args[]) {
        MSTAllHW test = new MSTAllHW();
        Graph graph = test.getCompleteGraph(5);

//        graph.setUndirectedEdge(0, 1, 1);
//        graph.setUndirectedEdge(0, 2, 1);
//        graph.setUndirectedEdge(1, 2, 2);


        List<SpanningTree> testList = test.getMinimumSpanningTrees(graph);
        System.out.println(testList.size());



    }


    public Graph getCompleteGraph(int V) {
        Graph graph = new Graph(V);

        for (int i = 0; i < V - 1; i++)
            for (int j = i + 1; j < V; j++)
                graph.setUndirectedEdge(i, j, 1);

        return graph;
    }
}