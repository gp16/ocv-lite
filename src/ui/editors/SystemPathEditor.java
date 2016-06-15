package ui.editors;

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

public class SystemPathEditor implements ArgumentEditor {

    private final JFileChooser chooseImage;
    private final JButton openChooser;
    private final JLabel label;
    private Parameter param;

    public SystemPathEditor() {
        chooseImage = new JFileChooser();
        openChooser = new JButton("Select file");
        chooseImage.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        openChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseImage.showOpenDialog(null);
                
                String path = chooseImage.getSelectedFile().getAbsolutePath();
                System.out.println(path);
                
                if(path.length() > 10)
                    path = "..." + path.substring(path.length() - 10);
                
                openChooser.setText(path);
            }
        });
        
        label = new JLabel();
    }

    @Override
    public Component[] getEditorComps() {
        return new Component[]{label, openChooser};
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
        chooseImage.setToolTipText(param.MAN);
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
