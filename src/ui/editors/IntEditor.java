package ui.editors;

import engine.Argument;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class IntEditor implements ArgumentEditor {

    private Parameter param;
    private final JSpinner  numSpinner;
    private final JLabel label;
    
    
    public IntEditor() {
        numSpinner = new JSpinner();
        label = new JLabel();
    }
    
    @Override
    public Component[] getEditorComps() {
        return new Component[]{label, numSpinner};
    }

    @Override
    public Argument getArgument() {
        Argument arg = new Argument(Type.INT , numSpinner.getValue());
        return arg;
    }

    @Override
    public void setParameter(Parameter param) {
        this.param = param;
        Dimension d = numSpinner.getPreferredSize();
        d.width = 50;
        numSpinner.setPreferredSize(d);
        label.setText(param.NAME + " :");
        numSpinner.setToolTipText(param.MAN);
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
