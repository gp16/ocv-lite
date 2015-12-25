package ui.api;

import engine.Argument;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SystemPathEditor extends JPanel implements ArgumentEditor{
    
    private final JFileChooser chooseImage;
    private final JLabel label;
    private Parameter param;
    
    public SystemPathEditor(){
        chooseImage = new JFileChooser();
        label = new JLabel();
        add(label);
        add(chooseImage);
    }
    
    @Override
    public Component getEditorPanel() {
        return this;
    }

    @Override
    public Argument getArgument() {
        Argument arg = new Argument(Type.STR, chooseImage.getIcon(null));
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