package ui.api;

import engine.Argument;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ImgIdEditor extends JPanel implements ArgumentEditor{

    private Parameter param;
    private final JTextField imageID;
    private final JLabel label;
    
    public ImgIdEditor(){
        imageID = new JTextField(10);
        label = new JLabel();
        add(label);
        add(imageID);
    }
    
    @Override
    public Component getEditorPanel() {
        return this;
    }

    @Override
    public Argument getArgument() {
        Argument arg = new Argument(Type.MAT_ID, imageID.getText());
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
