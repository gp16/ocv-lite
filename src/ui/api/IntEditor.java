package ui.api;

import engine.Argument;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;

public class IntEditor extends JPanel implements ArgumentEditor {

    private Parameter param;
    private final JSpinner  numSpinner;
    private final JLabel label;
    
    
    public IntEditor() {
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
        Argument arg = new Argument(Type.FLOAT , numSpinner.getValue());
        return arg;
    }

    @Override
    public void setParameter(Parameter param) {
        this.param = param;
        label.setText(param.NAME + " :");
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
