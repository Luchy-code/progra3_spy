package test;

import org.junit.jupiter.api.Test;


import grafo.Grafo;
import grafo.Vertice;

import static org.junit.jupiter.api.Assertions.*;


import java.util.List;

public class GrafoTest {

    @Test
    public void testAddVertex() {
        Grafo graph = new Grafo();
        Vertice vertexA = graph.addVertex("A");

        assertNotNull(vertexA);
        assertEquals("A", vertexA.getLabel());

        // Intentar agregar un otra vez un vertice tendria que mandar un error
        assertThrows(IllegalArgumentException.class, () -> graph.addVertex("A"));
    }

    @Test
    public void testAddEdge() {
        Grafo graph = new Grafo();
        Vertice vertexA = graph.addVertex("A");
        Vertice vertexB = graph.addVertex("B");

        // Intentar agregar una Arista con un peso menor o igual a cero tendria que mandar un error
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(vertexA, vertexB, -0.1));

        // Intentar agregar una Arista que ya fue agregada tendria que mandar un error
        graph.addEdge(vertexA, vertexB, 1.0);
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(vertexA, vertexB, 0.5));

        // Intentar agregar una Arista que tiene la misma destinacion tendria que mandar un error
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(vertexA, vertexA, 0.5));
    }

    @Test
    public void testAddEdgeWithNonexistentVertices() {
        Grafo graph = new Grafo();
        Vertice vertexA = graph.addVertex("A");
        Vertice vertexB = new Vertice("B"); // Not added to the graph

        // Intentar agregar una Arista con Vertice que no existen en el grafo tendria que mandar un erro
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(vertexA, vertexB, 1.0));
    }

    @Test
    public void testGenerateAdjacencyMap() {
        Grafo graph = new Grafo();
        Vertice vertexA = graph.addVertex("A");
        Vertice vertexB = graph.addVertex("B");

        graph.addEdge(vertexA, vertexB, 1.0);
        List<String> adjacencyMap = graph.generateAdjacencyMap();

        assertTrue(adjacencyMap.contains("A --> B(1.0) "));
    }

   
}

