package interfaz;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import java.awt.*;
import javax.swing.ImageIcon;

	public class DotImg extends MapMarkerDot {
	    private String nombreEspia;
	    private Image icono;

	    public  DotImg(String nombreEspia, Coordinate markeradd, String rutaIcono) {
	        super(markeradd);
	        this.nombreEspia = nombreEspia;
	        this.icono = new ImageIcon(getClass().getResource(rutaIcono)).getImage();
	    }

	    @Override
	    public void paint(Graphics g, Point position, int radius) {
	    	 g.setFont(new Font("Arial", Font.PLAIN, 12));
	        // Dibujar la imagen del espía
	        if (icono != null) {
	        	g.drawImage(icono, position.x - radius, position.y - radius, 20, 20, null);  // Ajusta el tamaño de la imagen
	        }

	        // Dibujar el nombre del espía debajo del marcador
	        g.setColor(Color.BLACK);
	        g.drawString(nombreEspia, position.x - radius, position.y + radius + 12);  // Ajusta la posición del texto
	    }
	}

