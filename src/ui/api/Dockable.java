package ui.api;

import java.awt.Component;
import javax.swing.JPanel;

public interface Dockable
{
    public Component[] getNavigationComponents();
    public JPanel getDockablePanel();
}
