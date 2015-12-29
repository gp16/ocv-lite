package ui.api;

import engine.Argument;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;

public class FloatEditor extends JPanel implements ArgumentEditor {

    private Parameter param;
    private final JSlider  floatSlider;
    private final JLabel label;
    
    
    public FloatEditor() {
        floatSlider = new JSlider();
        label = new JLabel();
        add(label); 
        add(floatSlider);
    }
    
    @Override
    public Component getEditorPanel() {
        return this;
    }

    @Override
    public Argument getArgument() {
        Argument arg = new Argument(Type.FLOAT , floatSlider.getValue());
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
