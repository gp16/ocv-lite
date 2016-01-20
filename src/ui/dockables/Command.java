
package ui.dockables;

import engine.Argument;
import engine.Engine;
import engine.ICommand;
import engine.Parameter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import ui.api.Dockable;
import ui.editors.ArgumentEditor;

public class Command extends JPanel implements Dockable {
    
    private JComboBox commandSelector;
    private JPanel panel = new JPanel();
    private JButton excuteBotton = new JButton("execute");
    private ArgumentEditor[] editors = null;
    
    public Command() {
        add(panel, BorderLayout.WEST);
        
        commandSelector = new JComboBox(Engine.getInstance().getCommandsNames());
        commandSelector.addItem("Choose Command");
        commandSelector.setSelectedItem("Choose Command");
        commandSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                panel.removeAll();
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (commandSelector.getSelectedItem() != "Choose Command") {
                        ICommand comm = Engine.getInstance()
                                .getCommand(e.getItem().toString());
                        
                        Parameter[] params = comm.getParams();
                        editors = new ArgumentEditor[params.length];
                        
                        for (int i = 0; i < params.length; i++) {
                            ArgumentEditor argumentEditor = ArgumentEditor.createInstance(params[i]);
                            
                            if (argumentEditor != null) {
                                panel.add(argumentEditor.getEditorPanel());
                            }
                            editors[i] = argumentEditor;
                        }
                        
                        panel.setLayout(new GridLayout(params.length + 1, 1));
                        panel.revalidate();
                        panel.add(excuteBotton);
                        panel.revalidate();
                    }
                }
            }
        });
        
        excuteBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(editors == null)
                    return;
                
                Argument arguments[] = new Argument[editors.length];
                
                for(int i=0; i<editors.length; i++) {
                    if(editors[i] == null)
                        return;
                    
                    arguments[i] = editors[i].getArgument();
                }
                
                
                ICommand comm = Engine.getInstance().getCommand(
                        commandSelector.getSelectedItem().toString());
                
                
                
                if(comm == null)
                    return; // command not found
                
                comm.execute(arguments);
            }
            
        });
    }
    
    @Override
    public Component[] getNavigationComponents() {
        return new Component[]{
            commandSelector,};
    }
    
    @Override
    public JPanel getDockablePanel() {
        return this;
    }
    
}