/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dockables;

import interpreter.Interpreter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import ui.api.Dockable;

/**
 *
 * @author Amr_Ayman
 */
public final class Terminal extends JPanel implements Dockable
{
    private Interpreter interpreter;
    private final JTextField CodeEntery;
    private final JTextArea command;
    private final JScrollPane scroll;
    
 public Terminal()
 {
     setLayout(new BorderLayout());
     CodeEntery=new JTextField();
     command=new JTextArea();
     scroll = new JScrollPane (command);
     command.setEditable(false);
     command.setLineWrap(true);
     CodeEntery.setEnabled(true);
     command.setForeground(Color.white);
     command.setBackground(Color.black);
     CodeEntery.setForeground(Color.white);
     CodeEntery.setBackground(Color.black);
     CodeEntery.setPreferredSize( new Dimension( 0, 25 ) );
     add(scroll,BorderLayout.CENTER);
     add(CodeEntery,BorderLayout.SOUTH);
     interpret();
 }
 
 public void Append(String text)
 {
     command.append("-> "+text + "\n");
 }
 
public void interpret()
{
    interpreter = new Interpreter();
    CodeEntery.addActionListener((ActionEvent e) -> {
    if(interpreter.executeCommand(CodeEntery.getText())!=null)
    {
        Append(CodeEntery.getText());
        Append(interpreter.executeCommand(CodeEntery.getText()));
    }
    else
    {
        Append(CodeEntery.getText());
    }
    CodeEntery.setText("");
    });
}

    @Override
    public Component[] getNavigationComponents() 
    {
        return null;
    }

    @Override
    public JPanel getDockablePanel() 
    {
        return this;
    }
    
}
