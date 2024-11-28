package algoritmo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import grafo.Vertice;

public class FindUnion {

	private Map<Vertice, Vertice> parentMap = new HashMap<>();

	public FindUnion(Set<Vertice> arrayList) {

		for (Vertice vertex : arrayList) {
			parentMap.put(vertex, vertex);
		}

	}

	public Vertice find(Vertice vertex) {
		if (parentMap.get(vertex) != vertex) {
			parentMap.put(vertex, find(parentMap.get(vertex)));
		}
		return parentMap.get(vertex);
	}

	public void union(Vertice src, Vertice dest) {
		Vertice srcParent = find(src);
		Vertice destParent = find(dest);
		if (!srcParent.equals(destParent)) {
			parentMap.put(srcParent, destParent);
		}

	}

	public boolean connected(Vertice vertex1, Vertice vertex2) {
		Vertice fatherSrc = find(vertex1);
		Vertice fatherDest = find(vertex2);

		return fatherSrc.equals(fatherDest);
	}

}