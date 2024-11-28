package interfaz;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Interfaz_Principal {

    private int width;
    private int height;

    private JFrame frame;


    private Interfaz mainView;


    private URL image = getClass().getResource("/img/espiaIcono.png");

    public Interfaz_Principal(){
        initialize();
    }

    private void initialize() {

        height=850;
        width=900;

        frame = new JFrame();

        frame.setTitle("Bridge of Spies");
        

        ImageIcon icon = new ImageIcon(image);
        frame.setIconImage(icon.getImage());


        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainView = new Interfaz(width, height);
        mainView.setVisible(true);
        frame.add(mainView);
        frame.pack();


        mainView.requestFocus();
    }

    public Interfaz getDesigningRegions() {
        return mainView;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}
