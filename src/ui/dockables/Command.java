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
    JButton excuteBotton = new JButton("execute");

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
                        Argument[] arrArguments = new Argument[2];
                        for (int i = 0; i < params.length; i++) {
                            ArgumentEditor argumentEditor = ArgumentEditor.createInstance(params[i]);
                            arrArguments[i] = argumentEditor.getArgument();
                            if (argumentEditor != null) {
                                panel.add(argumentEditor.getEditorPanel());
                            }
                        }
                        excuteBotton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(java.awt.event.ActionEvent e) {
                                comm.execute(arrArguments);

                            }

                        });

                        panel.setLayout(new GridLayout(params.length, 2));
                        panel.revalidate();
                        panel.add(excuteBotton);
                        panel.revalidate();
                    }
                }
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
