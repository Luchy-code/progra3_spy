package interfaz;

import java.util.ArrayList;
import java.util.List;





import javax.swing.*;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Point;
import java.awt.geom.Path2D;



public class Interfaz extends JPanel{

    private int largo; 
    private int altura; 

    private int positionPanelX, positionPanelY;
    
    
    private JPanel panelElementsLeft;
    
    private JPanel titulo;
    private JTextField JTextTitle;

    private JMapViewer mapViewer;
    private JPanel panelMap;
    
    private JButton botonAggConexEspias;
    private JButton botonAlgoritmo;
   
    private JPanel panelBox;
    private JPanel panelConexAristas;

    private Coordinate argentina;
    private JTextArea textAreaVertices;
    private JButton botonAggEspia;


    private List<JComboBox<String>> listComboBoxSpy;
    private List<JComboBox<Integer>> listComboBoxWeight;


    private JPanel panelBotones;

    private String notSelected = "Not selected";
    private Panel panelDividirEspias;
    private JComboBox<Integer> comboBoxDivideCountry;
    private JButton botonDividirEspias;



    public Interfaz(int largo, int altura) {
        this.largo = largo;
        this.altura = altura;
        initialize();
    }

    private void initialize() {
        setLayout(null);
        setPreferredSize(new Dimension (largo, altura));
        setBackground(Color.GRAY);
        
        generarMapPanel();

        generarPanel();
        generarTitulo();
        generatarSpyBox(); 

        generarBotonesDeGrafo();
    }

    private void generarPanel() {
        panelElementsLeft = new JPanel();
        panelElementsLeft.setBackground(Color.black);
        panelElementsLeft.setLayout(null);
        panelElementsLeft.setBounds(largo-415,0,500,altura);
        add(panelElementsLeft);
    }
    
    private void generarMapPanel() {
        panelMap = new JPanel();
        positionPanelX = 20;
        positionPanelY = -5;
        panelMap.setBounds(positionPanelX, positionPanelY, largo/2, altura);
        panelMap.setBackground(Color.GREEN);


        generatarMap();
        panelMap.add(mapViewer);

        add(panelMap);
    }
    private void generatarMap() {
        mapViewer = new JMapViewer();
        mapViewer.setBorder(null);
        mapViewer.setZoomControlsVisible(false);
        mapViewer.setPreferredSize(new Dimension(largo / 2, altura));
        mapViewer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        argentina = new Coordinate(-40.2, -63.616);
        mapViewer.setDisplayPosition(argentina, 5);

    }
    
    private void generarTitulo() {
        titulo = new JPanel();
        titulo.setBounds(0, 5, 400, 50);
        JTextTitle = new JTextField();
        JTextTitle.setFont(new Font("Unispace", Font.BOLD, 27));
        JTextTitle.setText("Puente de Espias");
        
        JTextTitle.setEditable(false);
        JTextTitle.setBorder(null);
        titulo.add(JTextTitle);
        panelElementsLeft.add(titulo);
    }

    private void generatarSpyBox() {
        panelBox = new JPanel();
        panelBox.setBounds(0, 60, 400, 300);
        panelBox.setLayout(new BorderLayout());
        
        JLabel labelInstructions = new JLabel("Ingresar los nombres de los espias separado por comas (,)");
        panelBox.add(labelInstructions, BorderLayout.NORTH);
        
        textAreaVertices = new JTextArea();
        panelBox.add(new JScrollPane(textAreaVertices), BorderLayout.CENTER);

        botonAggEspia = new JButton("Ubicar la posicion de los espias en el mapa");
        panelBox.add(botonAggEspia, BorderLayout.SOUTH);
        panelElementsLeft.add(panelBox);
    }

    public void aggVerticeAlMapa (String vertexName, Coordinate coordinate){
        addVertexToMap(vertexName, coordinate);
    }


    private void addVertexToMap(String vertexName, Coordinate coordinate) {
    	DotImg marker=new DotImg(vertexName, coordinate, "/img/espiaIcono.png");
    	mapViewer.addMapMarker(marker); 
        mapViewer.revalidate();
        mapViewer.repaint();
    }

    private DefaultComboBoxModel<String> createComboBoxSpy(List<String> selectSpy, String nameSpy) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement(notSelected);
        for (String spy : selectSpy ) {
            if(nameSpy != spy){
                model.addElement(spy);
            }
        }
        return model;
    }
    
    private DefaultComboBoxModel<Integer> createComboBoxModel(int limited) {
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        for (int i = 1 ; i <= limited; i++) {
            model.addElement(i);
        }
        return model;
    }


    public void generateSpyEdge(List<String> espiaSelect){
        listaDeAristaDeEspias(espiaSelect);
    }
    
    private void listaDeAristaDeEspias(List<String> espiaSelect){
        panelConexAristas = new JPanel();
        panelConexAristas.setBounds(0, 365, 400, 528);
        panelElementsLeft.add(panelConexAristas);
        panelConexAristas.setLayout(null);

        int positionX = 0;

        listComboBoxSpy = new ArrayList<>();
        listComboBoxWeight = new ArrayList<>();

        for (String nombreEspia : espiaSelect) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 1));
            rowPanel.setBounds(0, positionX, 400, 20);
            rowPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            positionX += 22;

            JLabel label = new JLabel(nombreEspia);
            rowPanel.add(label);

            // Create ComboBox to select other spies and connection weights
            JComboBox<String> comboBox1 = new JComboBox<>(createComboBoxSpy(espiaSelect, nombreEspia));
            rowPanel.add(comboBox1);

            JComboBox<Integer> comboBox2 = new JComboBox<>(createComboBoxModel(10));
            rowPanel.add(comboBox2);

            listComboBoxSpy.add(comboBox1);
            listComboBoxWeight.add(comboBox2);

            panelConexAristas.add(rowPanel);
            revalidate();
            repaint();

        }
            
    }

    private void generarBotonesDeGrafo() {
        
        panelBotones = new JPanel();
        panelBotones.setBounds(0,altura-50,400,40);
        panelElementsLeft.add(panelBotones);

        botonAggConexEspias = new JButton("Agregar conexiones");
        botonAggConexEspias.setEnabled(false);
        panelBotones.add(botonAggConexEspias);

        botonAlgoritmo = new JButton("Correr Algoritmo");
        botonAlgoritmo.setEnabled(false);
        panelBotones.add(botonAlgoritmo);

        revalidate();
        repaint();
    }

    public void createCheckboxDivideCountry(int limited){

        panelBox.setLayout(null); 
        panelBox.setBounds(0,60,400,300);


        panelDividirEspias = new Panel();
        panelDividirEspias.setBounds(0,10,400,70);
        panelBox.add(panelDividirEspias);

        JLabel headerLabel = new JLabel("Cual es el nivel de peligro de ecriptacion de mensajes");
        panelDividirEspias.add(headerLabel);


        comboBoxDivideCountry = new JComboBox<>(createComboBoxDivideCountry(limited)); 

        panelDividirEspias.add(comboBoxDivideCountry); 


        botonDividirEspias = new JButton("Generar grafo y texto");
        panelDividirEspias.add(botonDividirEspias);

        

    }

    public JButton obtenerBotonDividirEspias() {
		return botonDividirEspias;
	}

	private DefaultComboBoxModel<Integer> createComboBoxDivideCountry(int limited) {
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();

        if(limited == 1){
            model.addElement(1);
        } else{
            for (int i = 2; i <= limited; i++) {
                model.addElement(i);
            }
        }
        return model;
    }
    
    public void removeCheckBoxElements(){
        panelBox.removeAll();
        revalidate();
        repaint();
    }

    public void removePreviewsMapPolygons(){
        mapViewer.removeAllMapPolygons();
        mapViewer.revalidate();
        mapViewer.repaint();
    }

    public void createMapPolyMark2(List<Coordinate> route ){
        mapViewer.addMapPolygon(new MapPolyLine(route));
        mapViewer.addMapPolygon(new MapPolyLine(route));
        mapViewer.revalidate();
        mapViewer.repaint();
    }

    public class MapPolyLine extends MapPolygonImpl {
        public MapPolyLine(List<? extends ICoordinate> points) {
            super(null, null, points);
        }
    
        @Override
        public void paint(Graphics g, List<Point> points) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(getColor());
            g2d.setStroke(getStroke());
            Path2D path = buildPath(points);
            g2d.draw(path);
            g2d.dispose();
        }
    
        private Path2D buildPath(List<Point> points) {
            Path2D path = new Path2D.Double();
            if (points != null && points.size() >= 1) {
                Point firstPoint = points.get(0);
                path.moveTo(firstPoint.getX(), firstPoint.getY());
                for (int i = 0; i < points.size(); i++) { 
                    Point p = points.get(i);
                    path.lineTo(p.getX(), p.getY());
                }
            }
            return path;
        }
    }
    public void createStringOfTheGraph(List<String> kruskal, List<String> originalGraph) {
    	panelConexAristas.removeAll();
    	panelConexAristas.revalidate();
    	panelConexAristas.repaint();
        // Esto es para mostrar el grafo Original
        addGraphToPanel(originalGraph, "Este es el gráfico original.");

        // Esto es para mostrar el grafo de Kruskal
        addGraphToPanel(kruskal, "Este es el gráfico de Kruskal.");


        panelConexAristas.revalidate();
        panelConexAristas.repaint();
    }
    private void addGraphToPanel(List<String> graphRepresentation, String header) {
        
        int positionY = panelConexAristas.getComponentCount() * 20;; // Posición inicial para la primera fila.
    
        JPanel headerPanel = new JPanel(new GridLayout(1, 1));
        JLabel headerLabel = new JLabel(header);
        headerPanel.add(headerLabel);
        headerPanel.setVisible(true);
        headerPanel.setBounds(0, positionY, 400, 20);
        panelConexAristas.add(headerPanel);
    
        positionY += 20; // Se incrementa para que el texto quede bien
    
        for (String line : graphRepresentation) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 1));
            JLabel label = new JLabel(line.toString());
            rowPanel.setBounds(0, positionY, 400, 20);
            rowPanel.add(label);
            panelConexAristas.add(rowPanel);
            positionY += 20; // Incrementa la position para la siguiente fila
        }
    
        panelConexAristas.revalidate(); // RRevalidamos los componetes para que se vean
        panelConexAristas.repaint(); // Repintamos del panel para que se vea
    }

    
    public String getNotSelected() {
        return notSelected;
    }

    public List<JComboBox<String>> getListComboBoxSpy() {
        return listComboBoxSpy;
    }

    public List<JComboBox<Integer>> getListComboBoxWeight() {
        return listComboBoxWeight;
    }


    public JMapViewer getMapViewer() {
        return this.mapViewer; 
    }

    public JButton getButtonAddSpy() {
        return botonAggEspia;
    }

    public JTextArea getTextAreaVertices() {
        return textAreaVertices;
    }

    public JButton getButtonAddSpyConnectionGraph() {
        return botonAggConexEspias;
    }

    public JButton getButtonAlgorithm() {
        return botonAlgoritmo;
    }
    public JComboBox<Integer> getComboBoxDivideCountry() {
        return comboBoxDivideCountry;
    }


}
