package ui.editors;

import engine.Argument;
import engine.Parameter;
import engine.Type;
import java.awt.Component;

public interface ArgumentEditor {

    public Component[] getEditorComps();

    public Argument getArgument();

    public void setParameter(Parameter param);

    public Parameter getParameter();

    public boolean isArgumentValid();

    public static ArgumentEditor createInstance(Parameter param) {
        if (param.TYPE == Type.CMD_ID) {
            ArgumentEditor editor = new CmdIdEditor();
            editor.setParameter(param);
            return editor;
        } else if (param.TYPE == Type.STR) {
            ArgumentEditor editor = new StringEditor();
            editor.setParameter(param);
            return editor;
        } else if (param.TYPE == Type.SYS_PATH) {
            ArgumentEditor editor = new SystemPathEditor();
            editor.setParameter(param);
            return editor;
        } else if (param.TYPE == Type.MAT_ID) {
            ArgumentEditor editor = new ImgIdEditor();
            editor.setParameter(param);
            return editor;
        } else if (param.TYPE == Type.FLOAT) {
            ArgumentEditor editor = new FloatEditor();
            editor.setParameter(param);
            return editor;
        } else if (param.TYPE == Type.INT) {
            int intRange = param.MAX - param.MIN;
            if (intRange <= 10 && intRange >= 1) {
                ArgumentEditor editor = new SmallIntEditor();
                editor.setParameter(param);
                return editor;
            } 
            else {
                ArgumentEditor editor = new IntEditor();
                editor.setParameter(param);
                return editor;
            }
        }

        return null;
    }

}
