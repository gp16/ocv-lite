package ui.dockables;

import engine.Engine;
import engine.ICommand;
import engine.Parameter;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.*;
import ui.api.ArgumentEditor;
import ui.api.CmdIdEditor;
import ui.api.Dockable;
import ui.api.StringEditor;

public class Command extends JPanel implements Dockable {
    
    private JComboBox commandSelector;
    private JPanel panel = new JPanel();
    private JButton executeButton;
    
    public Command()
    {
        add(panel,CardLayout.class);
        commandSelector = new JComboBox(Engine.getInstance().getCommandsNames());
        commandSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                panel.removeAll();
               if (e.getStateChange() == ItemEvent.SELECTED ) {
                   ICommand comm = Engine.getInstance()
                           .getCommand(e.getItem().toString());
                   Parameter [] params = comm.getParams();
                   for (int i=0; i < params.length; i++) {
                    ArgumentEditor argumentEditor = ArgumentEditor.createInstance(params[i]);
                 if(argumentEditor != null)
                    panel.add(argumentEditor.getEditorPanel());
                  }
                   panel.setLayout(new GridLayout(params.length, 2));
                   panel.revalidate();
               }
            }
        });
    }
/*
    public void execute() {
        executeComm = new ();
            executeButton.addActionListener((ActionEvent e) -> {
        executeComm.execute();
    });
    }
    */
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
