package ui.editors;

import engine.Argument;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImgIdEditor extends JPanel implements ArgumentEditor {

    private Parameter param;
    private final JComboBox imageID;
    private final JLabel label;

    public ImgIdEditor() {
        imageID = new JComboBox(Engine.getInstance().getImagesNames());
        label = new JLabel();
        imageID.setEditable(true);
        add(label);
        add(imageID);
    }

    @Override
    public Component getEditorPanel() {
        return this;
    }

    @Override
    public Argument getArgument() {
        Argument arg = new Argument(Type.MAT_ID, imageID.getSelectedItem());
        return arg;
    }

    @Override
    public void setParameter(Parameter param) {
        this.param = param;
        label.setText(param.NAME + " :");
        imageID.setToolTipText(param.MAN);
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
