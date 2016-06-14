package ui.editors;

import engine.Argument;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StringEditor implements ArgumentEditor {

    private final JTextField argumentValue;
    private final JLabel label;
    private Parameter param;

    public StringEditor() {
        argumentValue = new JTextField();
        label = new JLabel();
    }

    @Override
    public Component[] getEditorComps() {
        return new Component[]{label, argumentValue};
    }

    @Override
    public Argument getArgument() {
        Argument arg = new Argument(Type.STR, argumentValue.getText());
        return arg;
    }

    @Override
    public void setParameter(Parameter param) {
        this.param = param;
        label.setText(param.NAME + " :");
        argumentValue.setColumns(20);
        argumentValue.setToolTipText(param.MAN);
    }

    @Override
    public Parameter getParameter() {
        return param;
    }

    @Override
    public boolean isArgumentValid() {
        return true;
    }

}
