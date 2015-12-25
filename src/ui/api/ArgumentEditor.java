package ui.api;

import engine.Argument;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;


public interface ArgumentEditor {
    public Component getEditorPanel();
    public Argument getArgument();
    public void setParameter(Parameter param);
    public Parameter getParameter();
    public boolean isArgumentValid();
    public static ArgumentEditor createInstance(Parameter param) {
        if (param.TYPE == Type.CMD_ID) {
            ArgumentEditor editor = new CmdIdEditor();
            editor.setParameter(param);
            return editor;
        }
        else if (param.TYPE == Type.STR) {
            ArgumentEditor editor = new StringEditor();
            editor.setParameter(param);
            return editor;
        }
        else if (param.TYPE == Type.SYS_PATH) {
            ArgumentEditor editor = new SystemPathEditor();
            editor.setParameter(param);
            return editor;
        }
        else if (param.TYPE == Type.IMG_ID) {
            ArgumentEditor editor = new ImgIdEditor();
            editor.setParameter(param);
            return editor;
        }
        else if (param.TYPE == Type.FLOAT) {
            ArgumentEditor editor = new FloatEditor();
            editor.setParameter(param);
            return editor;
        }
        
        else if (param.TYPE == Type.INT) {
            ArgumentEditor editor = new IntEditor();
            editor.setParameter(param);
            return editor;
        }
        return null;
    }
    
}




















