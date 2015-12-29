package ui.argumenteditors;

import engine.Argument;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import javax.swing.*;

public class CmdIdEditor extends JPanel implements ArgumentEditor {

    private final JComboBox commandSelector;
    private final JLabel label;
    private Parameter param;

    public CmdIdEditor() {
        commandSelector = new JComboBox(Engine.getInstance().getCommandsNames());
        label = new JLabel();
        add(label);
        add(commandSelector);
    }

    @Override
    public Component getEditorPanel() {
        return this;
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
