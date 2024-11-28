package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algoritmo.MinimumGeneratingTree;
import grafo.Arista;
import grafo.Grafo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
public class AGM_Test {
    private Grafo graph;

    @BeforeEach
    public void setUp() {
        graph = new Grafo();


        // Agrega a los Vertices
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");

        // Agrega a los Aristas
        graph.addEdge(graph.getVertex("A"), graph.getVertex("B"), 1);
        graph.addEdge(graph.getVertex("A"), graph.getVertex("C"), 3);
        graph.addEdge(graph.getVertex("A"), graph.getVertex("D"), 4);
        graph.addEdge(graph.getVertex("B"), graph.getVertex("D"), 2);
        graph.addEdge(graph.getVertex("B"), graph.getVertex("E"), 5);
        graph.addEdge(graph.getVertex("C"), graph.getVertex("D"), 6);
        graph.addEdge(graph.getVertex("D"), graph.getVertex("E"), 7);
    }


    @Test
    public void testMinimumSpanningTree() {
        MinimumGeneratingTree mstFinder = new MinimumGeneratingTree();
        List<Arista> mst = mstFinder.minimumSpanningTree(graph);

        assertNotNull(mst, "El arbol generador minimo no tiene que ser null");
        assertEquals(4, mst.size(), "Tiene que tener al menso 4 Aristas");

        int totalWeight = mst.stream().mapToInt(Arista::getPeso).sum();
        assertEquals(11, totalWeight, "El peso total tendria que ser 11");
    }

    @Test
    public void testDisconnectedGraph() {
        Grafo disconnectedGraph = new Grafo();
        disconnectedGraph.addVertex("A");
        disconnectedGraph.addVertex("B");
        disconnectedGraph.addVertex("C");

        disconnectedGraph.addEdge(disconnectedGraph.getVertex("A"), disconnectedGraph.getVertex("B"), 1);

        MinimumGeneratingTree mstFinder = new MinimumGeneratingTree();
        List<Arista> mst = mstFinder.minimumSpanningTree(disconnectedGraph);

        assertNull(mst, "Tiene que dar null ya que el grafo es disconexo");
    }
}

