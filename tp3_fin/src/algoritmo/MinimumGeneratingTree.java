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
    
    //This is Edges
    private ArrayList<Arista> sortedEdges;





    public MinimumGeneratingTree (){
    }



    public List<Arista> minimumSpanningTree(Grafo graphOriginal) {
        sortedEdges = new ArrayList<>();
        List<Arista> mst = new ArrayList<>();


        //Add all the edges into a list and collect all the vertices
        for (List<Arista> aristas : graphOriginal.getAdjacencyList().values()) {
            sortedEdges.addAll(aristas);
        }
        Collections.sort(sortedEdges, Comparator.comparingInt(Arista::getPeso));
        int numEdges = 0;
        
       //Initialize disjoint set for cycle detection
       Set<Vertice> allVertices = graphOriginal.getAdjacencyList().keySet();


        FindUnion findUnion = new FindUnion(allVertices);


        //Iterate over ordered edges
        for (Arista edge : sortedEdges) {
            Vertice src = edge.getSrc();;
            Vertice dest = edge.getDest();
            // int weight = edge.getWeight();

            // Check if including this edge forms a cycle
            if (!findUnion.connected(src, dest)){

                //The edge case is added with its weight
                mst.add(edge);

                // System.out.println(src.getLabel() + " --> " + dest.getLabel() + " == " + edge.getWeight());
                numEdges++;

                //Merge the start and destination sets
                findUnion.union(src, dest);

            }
        }

        if (graphOriginal.getNumVertices() - 1 > numEdges) {
            return null;
        }

        //We return the entire graph with its corresponding edge.
        return mst;

    }

}