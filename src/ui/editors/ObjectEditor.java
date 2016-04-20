/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.editors;

import engine.Argument;
import engine.Type;
import java.awt.Component;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Amr_Ayman
 */
public class ObjectEditor extends JPanel implements ArgumentEditor{

    private final JTextField argumentValue;
    private final JLabel label;
    private java.lang.reflect.Parameter param;

    public ObjectEditor() {
      //  System.out.println(Arrays.toString(param.getType().getConstructors()));
        argumentValue = new JTextField(10);
        label = new JLabel();
        add(label);
        add(argumentValue);
    }

    @Override
    public Component getEditorPanel() {
        return this;
    }

    @Override
    public Argument getArgument() {
        Argument arg = new Argument(Type.OBJECT, argumentValue.getText());
        return arg;
    }

    @Override
    public void setParameter(java.lang.reflect.Parameter param) {
        this.param = param;
        label.setText(param.getType() + " :");
    //    argumentValue.setToolTipText(param.MAN);
    }

    @Override
    public java.lang.reflect.Parameter getParameter() {
        return param;
    }

    @Override
    public boolean isArgumentValid() {
        return true;
    }
    
}
