package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class FordFulkersonTest {
    @Test
    public void test() {
        NetworkFlowQuiz mfa = new NetworkFlowQuiz();
        Graph graph;
        Set<Subgraph> mf;

//        graph = getGraph0();
//        mf = test(mfa, graph, 0, graph.size() - 1);

        //assertEquals(5, (int) mf);

        graph = getGraph0();
        mf = test(mfa, graph, 0, graph.size() - 1);
        for (Subgraph sub : mf) {
            System.out.println(sub.getEdges());
        }


        //assertEquals(2, (int) mf);
    }

//    @Test
//    public void testUnDirected() {
//        NetworkFlow mfa = new FordFulkerson();
//        Graph graph;
//        double mf;
//
//        graph = getGraph0();
//        mf = test(mfa, graph, 0, graph.size() - 1);
//        assertEquals(5, (int) mf);
//
//        graph = getGraph1();
//        mf = test(mfa, graph, 0, graph.size() - 1);
//        assertEquals(2, (int) mf);
//    }

    Set<Subgraph> test(NetworkFlowQuiz mfa, Graph graph, int source, int target) {
        Set<Subgraph> m = mfa.getAugmentingPaths(graph, source, target);
        return m;
    }

    Graph getGraph0() {
        Graph graph = new Graph(6);
        int s = 0, t = 5;

        graph.setDirectedEdge(s, 1, 4);
        graph.setDirectedEdge(s, 2, 2);
        graph.setDirectedEdge(1, 3, 3);
        graph.setDirectedEdge(2, 3, 2);
        graph.setDirectedEdge(2, 4, 3);
        graph.setDirectedEdge(3, 2, 1);
        graph.setDirectedEdge(3, t, 2);
        graph.setDirectedEdge(4, t, 4);

        return graph;
    }

    public Graph getGraph1() {
        Graph graph = new Graph(4);
        int s = 0, t = 3;

        graph.setDirectedEdge(2, t, 1);
        graph.setDirectedEdge(1, t, 1);
        graph.setDirectedEdge(1, 2, 1);
        graph.setDirectedEdge(s, 2, 1);
        graph.setDirectedEdge(s, 1, 1);

        return graph;
    }
}