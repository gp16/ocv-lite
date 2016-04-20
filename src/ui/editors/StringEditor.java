package ui.editors;

import engine.Argument;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StringEditor extends JPanel implements ArgumentEditor {

    private final JTextField argumentValue;
    private final JLabel label;
    private java.lang.reflect.Parameter param;

    public StringEditor() {
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
        Argument arg = new Argument(Type.STR, argumentValue.getText());
        return arg;
    }

    @Override
    public void setParameter(java.lang.reflect.Parameter param) {
        this.param = param;
       label.setText("String" + " :");
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
