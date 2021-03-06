package ui.editors;

import engine.Argument;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
* Assigns a FileChooser to any command with SYS_PATH Parameter. 
* @author  Abdelrahman Mohsen
* @version 1.0
* @since   2015 
*/

public class FloatEditor implements ArgumentEditor {

    private Parameter param;
    private final JTextField floatInput;
    private final JLabel label;

    public FloatEditor() {
        floatInput = new JTextField(10);
        label = new JLabel();
    }

    @Override
    public Component[] getEditorComps() {
        return new Component[]{label, floatInput};
    }

    @Override
    public Argument getArgument() {
        Argument arg = new Argument(Type.FLOAT, floatInput.getText());
        return arg;
    }

    @Override
    public void setParameter(Parameter param) {
        this.param = param;
        label.setText(param.NAME + " :");
        floatInput.setToolTipText(param.MAN);
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
