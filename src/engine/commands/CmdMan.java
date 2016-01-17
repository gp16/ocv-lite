/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.commands;

import engine.AbstractCommand;
import engine.Parameter;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import engine.Engine;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Amr_Ayman
 */
public class CmdMan extends AbstractCommand
{
    private final JTextArea Man;
    private final JFrame manFrame;
    private final JScrollPane scroll;
    public CmdMan()
    {
        manFrame = new JFrame("manuel");
        manFrame.setLayout(new BorderLayout());
        Man=new JTextArea();
        scroll = new JScrollPane(Man);
        manFrame.setSize(300, 300);
        manFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Man.setEditable(false);
        Man.setLineWrap(true);
        Man.setForeground(Color.white);
        Man.setBackground(Color.black);
        manFrame.add(scroll);
        
        

    }
    @Override
    protected Parameter[] getParamsOnce() {
        return null;
    }

    @Override
    protected Object executeSafe() 
    {
        String[] commands=Engine.getInstance().getDescriptionCommand();
        String[] description=Engine.getInstance().getDescription();
        manFrame.setVisible(true);
        for(int counter=0;counter<commands.length;counter++)
        {
            Man.append(commands[counter] + "  -->  " + description[counter] + "\n");
        }
        return null;

    }

    @Override
    public String getName() {
        return "man";
    }

    @Override
    public String getMan() {
        return "manuel of all commands";
    }

}
