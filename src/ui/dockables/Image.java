package ui.dockables;

import engine.Engine;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ui.api.Dockable;

/**
 *
 * @author AmrGamal
 */
public class Image extends JPanel implements Dockable {

    private JComboBox ImageSelector;
    private ImageIcon imageIcon;
    private BufferedImage BufferedImage;

    public Image() {

        ImageSelector = new JComboBox(Engine.getInstance().getImagesNames());
        ImageSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                removeAll();
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    BufferedImage = Engine.getInstance()
                            .getImage(e.getItem().toString());
                    imageIcon = new ImageIcon(BufferedImage);
                    add(new JLabel(imageIcon));
                }
                revalidate();
            }
        });
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
