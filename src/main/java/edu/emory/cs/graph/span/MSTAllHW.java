package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;
import edu.emory.cs.set.DisjointSet;


import java.util.*;

/** @author Jinho D. Choi */
public class MSTAllHW implements MSTAll {
    double finish;
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        List<SpanningTree> result = new ArrayList<>();
        List<Integer> vertices = new ArrayList<>();
        final double weight = getMST(graph).getTotalWeight();
        finish = Math.pow(graph.size(), graph.size() - 2);

        for (int i = 0; i < graph.size(); i++) {
            vertices.add(i);
        }

        heapPermutation(vertices, vertices.size(), graph, result);

        for (int i = 0; i < result.size(); i++) {
            //System.out.println("AHHHHHHH");
            if (result.get(i).getTotalWeight() > weight) {
                result.remove(i);
                i--;
                //System.out.println("REMOVEEEED");
            }
        }

        return result;
    }

    private boolean isSameTree(List<Edge> spanningTree, List<Edge> tree) {
        int count = tree.size();
        for (Edge edge : spanningTree) {
            for (Edge edge1 : tree) {
                if (isSameEdge(edge, edge1)) count--;
            }
        }

        if (count == 0) return true;
        return false;
    }

    boolean add;
    SpanningTree tree;
    private void getAll(Graph graph, Subgraph sub, List<Integer> missing, List<SpanningTree> result) {
        if (result.size() >= finish) return;

        if (missing.isEmpty()) {
            add = true;
            tree = new SpanningTree();
            for (Edge edge : sub.getEdges()) {
                tree.addEdge(edge);
            }

            if (result.contains(tree)) return;
            for (SpanningTree spanningTree : result) {
                if (isSameTree(spanningTree.getEdges(), sub.getEdges())) add = false;
            }

            if (add) result.add(tree);

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

    private SpanningTree getMST(Graph graph) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(graph.getAllEdges());
        DisjointSet forest = new DisjointSet(graph.size());
        SpanningTree tree = new SpanningTree();

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();

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

    private boolean isSameEdge (Edge edge1, Edge edge2) {
        int source1, source2, target1, target2;
        source1 = edge1.getSource();
        target1 = edge1.getTarget();
        source2 = edge2.getSource();
        target2 = edge2.getTarget();

        if ((source1 == source2 && target1 == target2) || (source1 == target2 && target1 == source2)) return true;
        return false;


    }

    public static void main(String[] args) {
        MSTAllHW hi = new MSTAllHW();

        List<SpanningTree> lst = hi.getMinimumSpanningTrees(hi.getGraph3a());
        for (SpanningTree tree : lst) {
            System.out.println(tree.toString());
        }

    }

    Graph getGraph3a() {
        Graph graph = new Graph(3);

        graph.setUndirectedEdge(0, 1, 1);
        graph.setUndirectedEdge(0, 2, 1);
        graph.setUndirectedEdge(1, 2, 2);

        return graph;
    }
}