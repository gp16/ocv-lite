package ui.dockables;

import engine.Engine;
import engine.ICommand;
import engine.Parameter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import ui.api.Dockable;

public class Command extends JPanel implements Dockable {
    
    private JComboBox commandSelector;
    private JPanel panel = new JPanel(); 
    
    public Command()
    {
        add(panel,BorderLayout.WEST);
        commandSelector = new JComboBox(Engine.getInstance().getCommandsNames());
        commandSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                panel.removeAll();
               if (e.getStateChange() == ItemEvent.SELECTED ) {
                   ICommand comm = Engine.getInstance()
                           .getCommand(e.getItem().toString());
                   Parameter [] params = comm.getParams();
                   panel.setLayout(new GridLayout(params.length, 2));
                   panel.revalidate();
               }
            }
        });
    }

    @Override
    public Component[] getNavigationComponents()
    {
	return new Component[] {
            commandSelector,
	};
    }

    @Override
    public JPanel getDockablePanel()
    {
	return this;
    }
    
}
