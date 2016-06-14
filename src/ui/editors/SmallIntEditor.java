package ui.editors;

import engine.Argument;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SmallIntEditor implements ArgumentEditor {

    private Parameter param;
    private final JSlider numSlider;
    private final JLabel label;
    
    public SmallIntEditor() {
        numSlider = new JSlider();
        label = new JLabel();
    }

    @Override
    public Component[] getEditorComps() {
        return new Component[]{label, numSlider};
    }

    @Override
    public Argument getArgument() {
        Argument arg = new Argument(Type.INT, numSlider.getValue());
        return arg;
    }

    @Override
    public void setParameter(Parameter param) {
        this.param = param;
        label.setText(param.NAME + " :");
        numSlider.setMaximum(param.MAX);
	numSlider.setMinimum(param.MIN);
	numSlider.setMajorTickSpacing(10);
        numSlider.setMinorTickSpacing(1);
        numSlider.setPaintTicks(true);
        numSlider.setPaintLabels(true);
        numSlider.setToolTipText(param.MAN);
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