package ui.editors;

import engine.Argument;
import engine.Parameter;
import engine.Type;
import java.awt.Component;
import java.lang.reflect.Method;

public interface ArgumentEditor {

    public Component getEditorPanel();

    public Argument getArgument();

    public void setParameter(java.lang.reflect.Parameter param);

    public java.lang.reflect.Parameter getParameter();

    public boolean isArgumentValid();

    public static ArgumentEditor createInstance(java.lang.reflect.Parameter param) 
    {
       Class TYPE = param.getType();
         
        
             
         
          if (TYPE == String.class) {
            ArgumentEditor editor = new StringEditor();
            editor.setParameter(param);
            return editor;
         
        } else if (TYPE.toString().contains("float")) {
            ArgumentEditor editor = new FloatEditor();
            editor.setParameter(param);
            return editor;
        } else if (TYPE.toString().contains("int")) {
                ArgumentEditor editor = new IntEditor();
                editor.setParameter(param);
                return editor;
        }
        else if(TYPE.toString().contains("double"))
        {
            ArgumentEditor editor = new DoubleEditor();
            editor.setParameter(param);
                return editor;
        }
        else if(TYPE.toString().contains("long"))
        {
            ArgumentEditor editor = new LongEditor();
            editor.setParameter(param);
                return editor;
        }
        else{
            ArgumentEditor editor = new ObjectEditor();
            editor.setParameter(param);
                return editor;
        }
         
        
    }

}
