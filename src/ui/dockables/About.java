
package ui.dockables;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ui.Dockable;

public class About extends JPanel implements Dockable {
    public About()
    {
	add(new JLabel("ocv-lite is a project the provides wrappers for open-cv"));
    }

    @Override
    public Component[] getNavigationComponents()
    {
	return new Component[] {
	    new JLabel("Version 0.1"),
	};
    }

    @Override
    public JPanel getDockablePanel()
    {
	return this;
    }
    
}
