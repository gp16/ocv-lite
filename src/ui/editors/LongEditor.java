/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.editors;

import engine.Argument;
import engine.Type;
import java.awt.Component;
import java.lang.reflect.Parameter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

/**
 *
 * @author Amr_Ayman
 */
public class LongEditor extends JPanel implements ArgumentEditor{

    private java.lang.reflect.Parameter param;
    private final JSpinner  numSpinner;
    private final JLabel label;
    
    
    public LongEditor() {
        numSpinner = new JSpinner();
        label = new JLabel();
        add(label); 
        add(numSpinner);
    }
    
    @Override
    public Component getEditorPanel() {
        return this;
    }

    @Override
    public Argument getArgument() {
        Argument arg = new Argument(Type.INT , numSpinner.getValue());
        return arg;
    }

    @Override
    public void setParameter(java.lang.reflect.Parameter param) {
        this.param = param;
        label.setText(param.getType() + " :");
        //numSpinner.setToolTipText(param.MAN);
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
