package ui.argumenteditors;

import engine.Argument;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SystemPathEditor extends JPanel implements ArgumentEditor {

    private final JFileChooser chooseImage;
    private final JButton openChooser;
    private final JLabel label;
    private Parameter param;

    public SystemPathEditor() {
        chooseImage = new JFileChooser();
        openChooser = new JButton("Select file");
        openChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseImage.showOpenDialog(null);
            }
        });
        label = new JLabel();
        add(label);
        add(openChooser);
    }

    @Override
    public Component getEditorPanel() {
        return this;
    }

    @Override
    public Argument getArgument() {
        Argument arg = new Argument(Type.SYS_PATH, chooseImage.getSelectedFile().getAbsolutePath());
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
