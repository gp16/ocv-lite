package ui.dockables;

import engine.Engine;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import ui.api.Dockable;

/**
 *
 * @author AmrGamal
 */
public class Image extends JPanel implements Dockable {

    private final JComboBox ImageSelector;
    private ImageIcon imageIcon;
    private Mat Image;

    public Image() {
        
        ImageSelector = new JComboBox(Engine.getInstance().getImagesNames());
        ImageSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    removeAll();
                    Image = Engine.getInstance().getImage(e.getItem().toString());
                    imageIcon = new ImageIcon(Convert_To_Buffer(Image));
                    add(new JLabel(imageIcon));
                    revalidate();
                } catch (IOException ex) {
                    Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    /**
     * Convert a mat to buffered image
     */
    private BufferedImage Convert_To_Buffer(Mat image) throws IOException
    {
        MatOfByte Byte_Mat = new MatOfByte();
        Highgui.imencode(".jpg", image, Byte_Mat);
        byte[] Img = Byte_Mat.toArray();                      
        InputStream in = new ByteArrayInputStream(Img);
        BufferedImage Final_Image = ImageIO.read(in);
        return Final_Image;
 
    }
    @Override
    public Component[] getNavigationComponents() {

        return new Component[]{
            ImageSelector,};

    }

    @Override
    public JPanel getDockablePanel() {
        return this;
    }

}
