package ui.editors;

import engine.Argument;
import engine.Type;
import java.awt.Component;
import java.awt.Dimension;
import java.lang.reflect.Parameter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

/**
* Assigns a FileChooser to any command with SYS_PATH Parameter. 
* @author  Abdelrahman Mohsen
* @version 1.0
* @since   2015 
*/

public class FloatEditor extends JPanel implements ArgumentEditor {

    private java.lang.reflect.Parameter param;
    private final JSpinner  numSpinner;
    private final JLabel label;

    public FloatEditor() {
        numSpinner = new JSpinner();
        numSpinner.setPreferredSize(new Dimension(55, 20));
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
        Argument arg = new Argument(Type.FLOAT, numSpinner.getValue());
        return arg;
    }

    @Override
    public void setParameter(Parameter param) {
        this.param = param;
        label.setText(param.getType().getSimpleName() + " :");
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
