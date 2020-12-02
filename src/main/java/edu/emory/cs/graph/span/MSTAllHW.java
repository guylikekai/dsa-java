package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;
import edu.emory.cs.set.DisjointSet;


import java.util.*;

/** @author Jinho D. Choi */
public class MSTAllHW implements MSTAll {
    static int count = 0;
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(graph.getAllEdges());
        List<Edge> lst = new ArrayList<>(queue);
        SpanningTree tree;
        List<SpanningTree> result = new ArrayList<>();
        List<Edge> temp = new ArrayList<>(lst);
        double total;
        double max;
        Graph graph1 = new Graph(graph);
        Subgraph sub = new Subgraph();
        List<Edge> array = new ArrayList<>();

        //result.add(getMST(graph, temp));
//        total = result.get(0).getTotalWeight();
//        max = getMaxWeight(result.get(0));

//        for (Edge edge : lst) {
//            if (edge.getWeight() > max) {
//                graph1.getAllEdges().remove(edge);
//            }
//        }

        int limit = graph.size();

        while (limit > 0) {
            for (int i = 0; i < graph.size(); i++) {
                getPaths(graph, sub, result, i, limit);
            }
            limit--;
        }


//        for (int i = 0; i < lst.size() - 1; i++) {
//            e = lst.remove(0);
//            lst.add(e);
//            temp = new ArrayList<>(lst);
//            tree = getMST(graph, temp);
//            if (!result.contains(tree)) result.add(tree);
//        }



//        for (int i = 0; i < lst.size(); i++) {
//            temp = new ArrayList<>(lst);
//            e = temp.get(0);
//            for (int j = i + 1; j < lst.size(); j++) {
//                temp.set(i, temp.get(j));
//                temp.set(j, e);
//            }
//        }

//        for (int i = lst.size() - 1; i >= 0; i--) {
//            e = lst.poll();
//            lst.add(e);
//            temp = new ArrayDeque<>(lst);
//            tree = getMST(graph, temp);
//            if (!result.contains(tree)) result.add(tree);
//        }


for (int z = 0; z < graph.size(); z++) {
    for (int i = 0; i < result.size(); i++) {
        for (int j = i + 1; j < result.size(); j++) {
            array.clear();
            array.addAll(result.get(j).getEdges());
            System.out.println("first: " + result.get(i).getEdges());
            System.out.println("OG: " + array);
            for (Edge edge : result.get(i).getEdges()) {
                for (int k = 0; k < array.size(); k++) {
                    if (isSameEdge(edge, array.get(k))) array.remove(k);
                }
            }
            System.out.println("Check: " + array);
            if (array.isEmpty()) result.remove(j);
        }
    }
}


//
//        boolean remove = false;
//        List<Integer> toRemove = new ArrayList<>();
//        for (int i = 0; i < result.size(); i++) {
//            for (int j = i +1; j < result.size(); j++) {
//                for (Edge edge1 : result.get(i).getEdges()) {
//                    for (Edge edge2 : result.get(j).getEdges()) {
//                        remove = false;
//                        if (isSameEdge(edge1, edge2)) {
//                            remove = true;
//                            break;
//                        }
//                    }
//                    if (!remove) {
//                        break;
//                    }
//                }
//                if (remove) toRemove.add(j);
//            }
//        }
//
//
//        List<SpanningTree> tmp = new ArrayList<>();
//        if (!toRemove.isEmpty()) {
//            for (int i : toRemove) {
//                tmp.add(result.get(i));
//            }
//        }
//        result.removeAll(tmp);
//
//        for (SpanningTree tree1 : result) {
//            if (tree1.getTotalWeight() > total) result.remove(tree1);
//        }

        return result;
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
        System.out.println(count);
        for (SpanningTree tree : testList) {
            System.out.println(tree.toString());
        }
        System.out.println(testList.size());



    }


    public Graph getCompleteGraph(int V) {
        Graph graph = new Graph(V);

        for (int i = 0; i < V - 1; i++)
            for (int j = i + 1; j < V; j++)
                graph.setUndirectedEdge(i, j, 1);

        return graph;
    }

    public void getPaths(Graph graph, Subgraph sub, List<SpanningTree> result, int target, int limit) {
        System.out.println(sub.getEdges().toString());
        if (sub.getVertices().size() == graph.size()) {
            System.out.println(sub.getEdges().toString());
            count++;
            SpanningTree tree = new SpanningTree();
            for (Edge edge : sub.getEdges()) {
                tree.addEdge(edge);
            }
            if (!result.contains(tree)) result.add(tree);
            //System.out.println(tree.getEdges().toString());
            return;
        }
        Subgraph tmp;
        Graph tmp1;
        for (Edge edge : graph.getIncomingEdges(target)) {
            if (sub.contains(edge.getSource())) continue;
            tmp = new Subgraph(sub);
            tmp1 = new Graph(graph.size());

            for (Edge edge1 : sub.getEdges()) {
                tmp1.setDirectedEdge(edge1.getSource(), edge1.getTarget(), edge1.getWeight());
            }
            if (tmp1.getAllEdges().size() > 0){
                if (check(edge.getSource(), tmp.getEdges().get(0).getTarget(), tmp1, limit)) {
                    tmp.addEdge(edge);
                } else {
                    int[] max1 = new int[1];
                    for (int vertex : tmp.getVertices()) {
                        getLongestPath(vertex, sub.getEdges().get(0).getTarget(), tmp1, new Subgraph(), max1);
                        int limit1 = limit - max1[0];
                        getPaths(graph, tmp, result, vertex, limit);
                    }
            }

            } else {
                tmp.addEdge(edge);
            }
            getPaths(graph, tmp, result, edge.getSource(), limit);
        }
        return;
    }

    private boolean check(int target, int OGTarget, Graph sub, int limit) {
        int[] max = new int[1];
        max[0] = 0;
        getLongestPath(target, OGTarget, sub, new Subgraph(), max);
        if (max[0] > limit) return false;
        else return true;
    }

    private void getLongestPath(int target, int OGTarget, Graph sub, Subgraph sub1, int[] max) {
        if (target == OGTarget) {
            if (sub1.getEdges().size() > max[0]) {
                max[0] = sub1.getEdges().size();
                return;
            }
        }

        for (Edge edge : sub.getIncomingEdges(target)) {
            if (sub1.getEdges().contains(edge)) continue;
            Subgraph temp = new Subgraph(sub1);
            temp.addEdge(edge);
            getLongestPath(target, OGTarget, sub, temp, max);
        }

        return;
    }
}