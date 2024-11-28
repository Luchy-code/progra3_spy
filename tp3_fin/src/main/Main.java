package main;


import algoritmo.MinimumGeneratingTree;
import controller.Controller;
import grafo.Grafo;
import interfaz.Interfaz_Principal;

public class Main {
	public static void main(String[] args) {
		
		Interfaz_Principal view = new Interfaz_Principal();
		Grafo graph = new Grafo();
		MinimumGeneratingTree algorithm = new MinimumGeneratingTree();
		// @SuppressWarnings("unused")
		@SuppressWarnings("unused")
		Controller controller = new Controller(view, view.getDesigningRegions(), graph, algorithm);

	}

}
