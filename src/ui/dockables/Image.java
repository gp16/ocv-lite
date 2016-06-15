package ui.dockables;

import engine.Engine;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
    private JLabel xLabel = new JLabel("X: ");
    private JLabel yLabel = new JLabel("Y: ");
    private JButton refresh;
    private int scaledImageWidth = 0;
    private int scaledImageHeight = 0;
    
    public Image() {

        ImageSelector = new JComboBox();
        String imagesNames[] = Engine.getInstance().getImagesNames();
        ImageSelector.addItem("Choose");
        for (int i = 0; i < imagesNames.length; i++) {
            ImageSelector.addItem(imagesNames[i]);
        }
        ImageSelector.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ImageSelector.removeAllItems();
                String imagesNames[] = Engine.getInstance().getImagesNames();
                ImageSelector.addItem("Choose");
                for (int i = 0; i < imagesNames.length; i++) {
                    ImageSelector.addItem(imagesNames[i]);
                }
            }
        });
        ImageSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        if (ImageSelector.getSelectedItem() != "Choose") {
                            Image = Engine.getInstance().getImage(e.getItem().toString());
                            imageIcon = new ImageIcon(Convert_To_Buffer(Image));
                        }
                    }
                    
                    repaint();
                } catch (IOException ex) {
                    Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int realImageWidth = ((BufferedImage)imageIcon.getImage()).getWidth();
                int realImageHeight = ((BufferedImage)imageIcon.getImage()).getWidth();
                
                int relativeX = (int) (e.getX() * (double)realImageWidth / (double)scaledImageWidth);
                int relativeY = (int) (e.getY() * (double)realImageHeight / (double)scaledImageHeight);

                xLabel.setText("X: " + relativeX);
                yLabel.setText("Y: " + relativeY);
            }
        });
        refresh = new JButton("refresh");
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                        if (ImageSelector.getSelectedItem() != "Choose") {
                            Image = Engine.getInstance().getImage(ImageSelector.getSelectedItem().toString());
                            imageIcon = new ImageIcon(Convert_To_Buffer(Image));
                        }
                        
                        repaint();
                } catch (IOException ex) {
                    Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        
        if(imageIcon == null)
            return;
        
        BufferedImage image = (BufferedImage)imageIcon.getImage();
        
        if(image.getWidth() <= getWidth() && image.getHeight() <= getHeight()) {
            scaledImageWidth = image.getWidth();
            scaledImageHeight = image.getHeight();
        }
        else {
            double hscale = (double)getWidth() / image.getWidth();
            double wscale = (double)getHeight() / image.getHeight();

            if(hscale < wscale) {
                scaledImageWidth = getWidth();
                scaledImageHeight = (int) (hscale*image.getHeight());                
            }
            else if(wscale <= hscale) {
                scaledImageWidth = (int) (wscale*image.getWidth());
                scaledImageHeight = getHeight();
            }
        }
        g.drawImage(image, 0, 0, scaledImageWidth, scaledImageHeight, null);
    }
    /**
     * Convert a mat to buffered image
     */
    private BufferedImage Convert_To_Buffer(Mat image) throws IOException {
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
            ImageSelector,
            refresh,
            xLabel = new JLabel("x: "),
            yLabel,};
    }

    @Override
    public JPanel getDockablePanel() {
        return this;
    }

}
