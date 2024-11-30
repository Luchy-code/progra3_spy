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

	    // Agrega los Vértices
	    graph.addVertex("A");
	    graph.addVertex("B");
	    graph.addVertex("C");
	    graph.addVertex("D");
	    graph.addVertex("E");

	    // Agrega las Aristas
	    graph.addEdge(graph.getVertex("A"), graph.getVertex("B"), 0.1);
	    graph.addEdge(graph.getVertex("A"), graph.getVertex("C"), 0.3);
	    graph.addEdge(graph.getVertex("A"), graph.getVertex("D"), 0.4);
	    graph.addEdge(graph.getVertex("B"), graph.getVertex("D"), 0.2);
	    graph.addEdge(graph.getVertex("B"), graph.getVertex("E"), 0.5);
	    graph.addEdge(graph.getVertex("C"), graph.getVertex("D"), 0.6);
	    graph.addEdge(graph.getVertex("D"), graph.getVertex("E"), 0.7);
	}

	@Test
	public void testMinimumSpanningTree() {
	    MinimumGeneratingTree mstFinder = new MinimumGeneratingTree();
	    List<Arista> mst = mstFinder.minimumSpanningTree(graph);

	    assertNotNull(mst, "El árbol generador mínimo no puede ser null");
	    assertEquals(4, mst.size(), "Debe tener exactamente 4 aristas");

	    Double totalWeight = mst.stream().mapToDouble(Arista::getPeso).sum();

	    // Usamos un margen de error pequeño para la comparación de números flotantes
	    assertEquals(1.1, totalWeight, 0.0001, "El peso total debería ser 1.1, calculado: " + totalWeight);
	}

	@Test
	public void testDisconnectedGraph() {
	    Grafo disconnectedGraph = new Grafo();
	    disconnectedGraph.addVertex("A");
	    disconnectedGraph.addVertex("B");
	    disconnectedGraph.addVertex("C");

	    // Solo se agrega una arista entre A y B
	    disconnectedGraph.addEdge(disconnectedGraph.getVertex("A"), disconnectedGraph.getVertex("B"), 0.1);

	    MinimumGeneratingTree mstFinder = new MinimumGeneratingTree();
	    List<Arista> mst = mstFinder.minimumSpanningTree(disconnectedGraph);

	    // Si el grafo es disconexo, el árbol generador mínimo debería ser null
	    assertNull(mst, "El árbol generador mínimo debe ser null, ya que el grafo es disconexo");
	}

}

