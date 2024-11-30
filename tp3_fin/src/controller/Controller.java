package controller;

import javax.swing.*;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import algoritmo.MinimumGeneratingTree;
import coordinates.Coordinates;
import grafo.Arista;
import grafo.Grafo;
import interfaz.Interfaz;
import interfaz.Interfaz_Principal;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

	@SuppressWarnings("unused")
	private Interfaz_Principal interfaz_Principal;
	private Interfaz interfaz;
	private Grafo grafo;
	private MinimumGeneratingTree algritmo;

	private JButton botonAggEspia;

	private List<String> listaDeEspias = new ArrayList<>();

	private JMapViewer mapViewer;

	private MouseAdapter addLocationsListener;

	private Map<String, Coordinates> ubicacionesDeEspias = new HashMap<>();
	
	private JButton botonAggConexEspias;
	private JButton botonAlgoritmo;

	private List<JComboBox<String>> listComboBoxSpy;
	private List<JComboBox<Double>> listComboBoxWeight;

	private Map<String, Coordinates> coordsEspias = new HashMap<>();

	private List<Arista> _agm;

	public Controller(Interfaz_Principal interfaz_Principal, Interfaz interfaz, Grafo grafo,
			MinimumGeneratingTree algoritmo) {
		this.interfaz_Principal = interfaz_Principal;
		this.interfaz = interfaz;
		this.grafo = grafo;
		this.algritmo = algoritmo;
		añadirEspias();
	}

	private void añadirEspias() {
		botonAggEspia = interfaz.getButtonAddSpy();

		botonAggConexEspias = interfaz.getButtonAddSpyConnectionGraph();

		botonAlgoritmo = interfaz.getButtonAlgorithm();

		botonAggEspia.addActionListener(e -> {
			String text = interfaz.getTextAreaVertices().getText();
			if (text != null && !text.trim().isEmpty()) {
				String[] vertices = text.split(",");
				for (String vertex : vertices) {
					vertex = vertex.trim();
					if (!vertex.isEmpty()) {
						listaDeEspias.add(vertex);
						grafo.addVertex(vertex);
					}
				}
				botonAggEspia.setEnabled(false);
			}

			if (!listaDeEspias.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Click en el mapa para ubicar a los espias");
				interfaz.generateSpyEdge(listaDeEspias);
				aggConexiones(listaDeEspias);
			} else {
				JOptionPane.showMessageDialog(null, "Por favor, ingresar un vertice mas", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		botonAggConexEspias.addActionListener(e -> {
			listComboBoxSpy = interfaz.getListComboBoxSpy();
			listComboBoxWeight = interfaz.getListComboBoxWeight();

			for (int i = 0; i < listComboBoxWeight.size(); i++) {
				if (listComboBoxSpy.get(i).getSelectedItem().toString() != interfaz.getNotSelected()) {
					String src = listaDeEspias.get(i);
					String dest = listComboBoxSpy.get(i).getSelectedItem().toString();
					Double weight = (Double) listComboBoxWeight.get(i).getSelectedItem();

					grafo.addEdge(grafo.getVertex(src), grafo.getVertex(dest), weight);
				}
			}

			List<Arista> mst = grafo.getAllEdges();

			botonAlgoritmo.setEnabled(true);
			interfaz.removePreviewsMapPolygons();

			for (Arista edge : mst) {
				Coordinates src = coordsEspias.get(edge.getSrc().getLabel());
				Coordinates dest = coordsEspias.get(edge.getDest().getLabel());

				Coordinate srcCoordinate = new Coordinate(src.getLatitude(), src.getLongitude());
				Coordinate destCoordinate = new Coordinate(dest.getLatitude(), dest.getLongitude());

				List<Coordinate> route = Arrays.asList(srcCoordinate, destCoordinate, destCoordinate, srcCoordinate);
				interfaz.createMapPolyMark2(route);
			}
			botonAggConexEspias.setEnabled(false);
			botonAlgoritmo.setEnabled(true);
		});

		botonAlgoritmo.addActionListener(e -> {

			_agm = algritmo.minimumSpanningTree(grafo);

			if (_agm != null) {
				interfaz.removeCheckBoxElements();
				botonAlgoritmo.setEnabled(false);
				ejecutarAlgoritmo();
			} else {
				JOptionPane.showMessageDialog(null, "El grafo debe estar conectado",
						"El Grafo no esta conectado ", JOptionPane.ERROR_MESSAGE);
			}

		});

	}

	private void aggConexiones(List<String> espias) {

		List<String> spyToAdd = new ArrayList<>();
		spyToAdd.addAll(espias);
		mapViewer = interfaz.getMapViewer();

		addLocationsListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Coordinate clickCoordinate = (Coordinate) mapViewer.getPosition(e.getPoint());

				String nombreEspia = obtenerSigEspiaSinConexion(spyToAdd);
				//System.err.println(nombreEspia);
				interfaz.aggVerticeAlMapa(nombreEspia, clickCoordinate);

				if (nombreEspia != null) {
					Coordinates coords = obtenerCoords(nombreEspia, e);
					asignarCoords(nombreEspia, coords);
					coordsEspias.put(coords.getName(), coords);
					spyToAdd.remove(nombreEspia);

					if (spyToAdd.isEmpty()) {
						desahabilitarAggConexiones();
					}
				}
			}

		};
		interfaz.getMapViewer().addMouseListener(addLocationsListener);
	}

	private void desahabilitarAggConexiones() {
		if (addLocationsListener != null) {
			JOptionPane.showMessageDialog(null, "Perfecto, los espias estan ubicados", "Informacion",
					JOptionPane.INFORMATION_MESSAGE);
			interfaz.getMapViewer().removeMouseListener(addLocationsListener);
			addLocationsListener = null;
			botonAggConexEspias.setEnabled(true);
		}
	}

	private Coordinates obtenerCoords(String thisSpyName, MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		JMapViewer mapViewer = interfaz.getMapViewer();
		ICoordinate geoCoords = mapViewer.getPosition(x, y);
		return new Coordinates(thisSpyName, geoCoords.getLat(), geoCoords.getLon());
	}

	private String obtenerSigEspiaSinConexion(List<String> listaEspia) {
		for (String spy : listaEspia) {
			if (!ubicacionesDeEspias.containsKey(spy)) {
				return spy;
			}
		}
		return null;
	}

	private void asignarCoords(String nombreEspia, Coordinates coords) {
		if (nombreEspia != null && !nombreEspia.isEmpty() && coords != null) {
			ubicacionesDeEspias.put(nombreEspia, coords);
		} else {
			JOptionPane.showMessageDialog(null, "Error: el nombre del espias o las coordenadas son incorrectas", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void ejecutarAlgoritmo() {
		interfaz.removePreviewsMapPolygons();
		
		List<String> listSpy = new ArrayList<>();
		
		for (Arista edge : _agm) {
			listSpy.add(edge.getSrc().getLabel() + " --> " + edge.getDest().getLabel() + " (" + edge.getPeso()
					+ ") ");

			Coordinates src = coordsEspias.get(edge.getSrc().getLabel());
			Coordinates dest = coordsEspias.get(edge.getDest().getLabel());

			Coordinate srcCoordinate = new Coordinate(src.getLatitude(), src.getLongitude());
			Coordinate destCoordinate = new Coordinate(dest.getLatitude(), dest.getLongitude());

			List<Coordinate> route = Arrays.asList(srcCoordinate, destCoordinate, destCoordinate, srcCoordinate);
			interfaz.createMapPolyMark2(route);

		}

		interfaz.createStringOfTheGraph(listSpy, grafo.generateAdjacencyMap());
		

	}

}
