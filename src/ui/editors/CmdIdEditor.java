package ui.editors;

import engine.Argument;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import javax.swing.*;

public class CmdIdEditor implements ArgumentEditor {

    private final JComboBox commandSelector;
    private final JLabel label;
    private Parameter param;

    public CmdIdEditor() {
        commandSelector = new JComboBox(Engine.getInstance().getCommandsNames());
        label = new JLabel();
    }

    @Override
    public Component[] getEditorComps() {
        return new Component[]{label, commandSelector};
    }

    @Override
    public Argument getArgument() {
        Argument arg = new Argument(Type.CMD_ID, commandSelector.getSelectedItem());
        return arg;
    }

    @Override
    public void setParameter(Parameter param) {
        this.param = param;
        label.setText(param.NAME + " :");
        commandSelector.setToolTipText(param.MAN);
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
