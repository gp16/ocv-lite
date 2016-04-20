package ui.dockables;

import engine.Engine;
import java.awt.BorderLayout;
import java.awt.Component;
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
    private BufferedImage Image;
    private JLabel xLabel = new JLabel("X: ");
    private JLabel yLabel = new JLabel("Y: ");
    private JButton refresh;
    private JPanel panel = new JPanel();

    public Image() {
        add(panel, BorderLayout.WEST);
        ImageSelector = new JComboBox();
        String imagesNames[] = Engine.getInstance().getImagesNames();
        ImageSelector.addItem("Choose");
        for (int i = 0; i < imagesNames.length; i++) {
            ImageSelector.addItem(imagesNames[i]);
        }
//        ImageSelector.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                ImageSelector.removeAllItems();
//                String imagesNames[] = Engine.getInstance().getImagesNames();
//                ImageSelector.addItem("Choose");
//                for (int i = 0; i < imagesNames.length; i++) {
//                    ImageSelector.addItem(imagesNames[i]);
//                }
//            }
//        });
        ImageSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                
                    panel.removeAll();
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        if (ImageSelector.getSelectedItem() != "Choose") {
                            Image = Engine.getInstance().getImage(ImageSelector.getSelectedItem().toString());
                            imageIcon = new ImageIcon(Image);
                            panel.add(new JLabel(imageIcon));
                        }
                    }
                    panel.revalidate();
                    panel.repaint();
                
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xLabel.setText("X: " + e.getX());
                yLabel.setText("Y: " + e.getY());
            }
        });
        
        refresh = new JButton("refresh");
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                    panel.removeAll();
                        if (ImageSelector.getSelectedItem() != "Choose") {
                    Image = Engine.getInstance().getImage(ImageSelector.getSelectedItem().toString());
                    imageIcon = new ImageIcon(Image);
                    panel.add(new JLabel(imageIcon));
                        }
                    
                    panel.revalidate();
                    panel.repaint();
                
            }
        });
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
