﻿package grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Grafo {
    private int numVertices;
    private List<Vertice> vertices;

    private Map<Vertice, List<Arista>> adjacencyList;

    public Grafo() {
        this.numVertices = 0;
        this.vertices = new ArrayList<>();
        this.adjacencyList = new HashMap<>();
    }

    public Vertice addVertex(String label) {
        for (Vertice existingVertex : vertices) {
            if (existingVertex.getLabel().equals(label)) {
                throw new IllegalArgumentException("Vertex with label '" + label + "' already exists");
            }
        }
        Vertice vertex = new Vertice(label);
        vertices.add(vertex);
        adjacencyList.put(vertex, new ArrayList<>());
        numVertices++;
        return vertex;
    }

    public void addEdge(Vertice source, Vertice destination, Double weight) {
        if(weight <= 0){
            throw new IllegalArgumentException("The graph cannot be equal to or less than 0");
        }

        for (Arista edge : adjacencyList.get(source)) {
            if (edge.getDest().equals(destination)) {
                
                throw new IllegalArgumentException("This Edge has already been added to the graph.");
            }
        }

        if(source.equals(destination)){
            throw new IllegalArgumentException("The chart will not accept that the Source and Destination are equal");
        }

        if (!vertices.contains(source) || !vertices.contains(destination)) {
            throw new IllegalArgumentException("The source or destination vertex does not exist in the graph");
        }
        
        adjacencyList.get(source).add(new Arista(source,destination, weight));
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertice> vertices) {
        this.vertices = vertices;
    }

    public Vertice getVertex (String nombreEpia){
        for (Vertice list : vertices) {
            if(list.getLabel() == nombreEpia){
                return list;
            }
        }
        return null;
    }

    public Map<Vertice, List<Arista>> getAdjacencyList() {
        return adjacencyList;
    }

    public int getNumVertices() {
        return numVertices;
    }


    public List<Arista> getAllEdges() {
        List<Arista> allEdges = new ArrayList<>();
        for (Map.Entry<Vertice, List<Arista>> entry : adjacencyList.entrySet()) {
            allEdges.addAll(entry.getValue());
        }
        return allEdges;
    }


    public List<String> getAllTheEdgesInStrings() {
        List<String> representation = new ArrayList<>();
        
        for (Map.Entry<Vertice, List<Arista>> entry : adjacencyList.entrySet()){
            List<Arista> edges = entry.getValue();
            for (Arista edge : edges) {
                representation.add(edge.getSrc().getLabel());
                representation.add(edge.getDest().getLabel());
            }
        }
        return representation;
    }

    public List<String> generateAdjacencyMap() {
        List<String> representation = new ArrayList<>();

        for (Map.Entry<Vertice, List<Arista>> entry : adjacencyList.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                StringBuilder line = new StringBuilder();
                line.append(entry.getKey().getLabel()).append(" --> ");
                List<Arista> edges = entry.getValue();
                for (Arista edge : edges) {
                    line.append(edge.getDest().getLabel()).append("(").append(edge.getPeso()).append(") ");
                }
                representation.add(line.toString());
            }
        }

        return representation;
    }
   


   
    public void print() {
        for (Map.Entry<Vertice, List<Arista>> entry : adjacencyList.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                System.out.print(entry.getKey().getLabel() + " --> ");
                List<Arista> edges = entry.getValue();
                for (Arista edge : edges) {
                    System.out.print(edge.getDest().getLabel() + "(" + edge.getPeso() + ") ");
                }
                System.out.println();
            }
        }
    }
}