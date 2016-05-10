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
import java.util.Map;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    private Engine engine = Engine.getInstance();

    public Image() {
        TreeMap<String,BufferedImage> IMGS = engine.getAllImages();
        add(panel, BorderLayout.WEST);
        ImageSelector = new JComboBox();
        ImageSelector.addItem("Choose");
        for (Map.Entry<String, BufferedImage> entrySet : IMGS.entrySet()) {
                String ImageName = entrySet.getKey();
                ImageSelector.addItem(ImageName);
                
        }
        ImageSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                
                    panel.removeAll();
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        if (ImageSelector.getSelectedItem() != "Choose") {
                            Image = IMGS.get(ImageSelector.getSelectedItem().toString());
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
            @Override
            public void actionPerformed(ActionEvent e) {
               
                    panel.removeAll();
                        if (ImageSelector.getSelectedItem() != "Choose") {
                    Image = IMGS.get(ImageSelector.getSelectedItem().toString());
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
