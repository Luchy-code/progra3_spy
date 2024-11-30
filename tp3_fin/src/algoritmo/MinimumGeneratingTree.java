package algoritmo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import grafo.Arista;
import grafo.Grafo;
import grafo.Vertice;

public class MinimumGeneratingTree {
    
    private ArrayList<Arista> sortedEdges;

    public MinimumGeneratingTree (){ }

    public List<Arista> minimumSpanningTree(Grafo graphOriginal) {
        sortedEdges = new ArrayList<>();
        List<Arista> mst = new ArrayList<>();

        for (List<Arista> aristas : graphOriginal.getAdjacencyList().values()) {
            sortedEdges.addAll(aristas);
        }
        Collections.sort(sortedEdges, Comparator.comparingDouble(Arista::getPeso));
        int numEdges = 0;
    
       Set<Vertice> allVertices = graphOriginal.getAdjacencyList().keySet();

        FindUnion findUnion = new FindUnion(allVertices);

        for (Arista edge : sortedEdges) {
            Vertice src = edge.getSrc();
            Vertice dest = edge.getDest();
            if (!findUnion.connected(src, dest)){
            	mst.add(edge);
            	numEdges++;
            	findUnion.union(src, dest);
            }
        }

        if (graphOriginal.getNumVertices() - 1 > numEdges) {
            return null;
        }

        return mst;

    }

}