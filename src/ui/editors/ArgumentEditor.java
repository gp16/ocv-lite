package ui.editors;

import engine.Argument;
import java.awt.Component;

public interface ArgumentEditor {

    public Component getEditorPanel();

    public Argument getArgument();

    public void setParameter(java.lang.reflect.Parameter param);

    public java.lang.reflect.Parameter getParameter();

    public boolean isArgumentValid();

    public static ArgumentEditor createInstance(java.lang.reflect.Parameter param) 
    {
       Class TYPE = param.getType();
       if (TYPE == String.class) 
       {
           ArgumentEditor editor = new StringEditor();
            editor.setParameter(param);
            return editor;
       } 
       else if (TYPE.equals(float.class)) 
       {
            ArgumentEditor editor = new FloatEditor();
            editor.setParameter(param);
            return editor;
        } 
       else if (TYPE.equals(int.class)) 
       {
                ArgumentEditor editor = new IntEditor();
                editor.setParameter(param);
                return editor;
        }
        else if(TYPE.equals(double.class))
        {
            ArgumentEditor editor = new DoubleEditor();
            editor.setParameter(param);
                return editor;
        }
        else if(TYPE.equals(long.class))
        {
            ArgumentEditor editor = new LongEditor();
            editor.setParameter(param);
                return editor;
        }
        else if(TYPE.equals(boolean.class))
        {
            ArgumentEditor editor = new BooleanEditor();
            editor.setParameter(param);
                return editor;
        }
        else
        {
            ArgumentEditor editor = new ObjectEditor();
            editor.setParameter(param);
                return editor;
        }
    }
}
