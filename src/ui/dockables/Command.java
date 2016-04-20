
package ui.dockables;

import engine.Argument;
import engine.Engine;
import engine.Methods;
import engine.Type;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.prompt.PromptSupport;
import ui.api.Dockable;
import ui.editors.ArgumentEditor;

public class Command extends JPanel implements Dockable {
   private Methods method = new Methods();
    private JComboBox methodSelector;
    private JComboBox classSelector;
    private JPanel panel = new JPanel();
    private JTextField SaveName = new JTextField(10);
    private JButton excuteBotton = new JButton("execute");
    private ArgumentEditor[] editors = null;
    HashMap<String,Method>   methods = null;
    public Command() {
        add(panel, BorderLayout.WEST);
        
        classSelector = new JComboBox();
        methodSelector = new JComboBox();
        SaveName.setEnabled(false);
        methodSelector.setEnabled(false);
        PromptSupport.setPrompt("save name", SaveName);
        methodSelector.addItem("Choose Command");
        methodSelector.setSelectedItem("Choose Command");
        classSelector.addItem("Choose Class");
        classSelector.setSelectedItem("Choose Class");
        HashMap<String,Class> classes = Engine.getInstance().getAllClasses();
        for (Map.Entry<String, Class> entrySet : classes.entrySet()) {
            String key = entrySet.getKey();
            Class value = entrySet.getValue();
            classSelector.addItem(key);
        }
        
        classSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    SaveName.setEnabled(false);
                    if (classSelector.getSelectedItem() != "Choose Class")
                    {
                        methodSelector.removeAllItems();
                        methodSelector.addItem("Choose Command");
                        methodSelector.setSelectedItem("Choose Command");
                        methods = Engine.getInstance().getClassMethods(classes.get(classSelector.getSelectedItem()));
                        for (Map.Entry<String, Method> entrySet : methods.entrySet()) {
                            String key = entrySet.getKey();
                            Method value = entrySet.getValue();
                            
                                methodSelector.addItem(key);
                            
                        }
                        methodSelector.setEnabled(true);
                    }
                else{
                        methodSelector.removeAllItems();
                        methodSelector.addItem("Choose Command");
                        methodSelector.setSelectedItem("Choose Command");
                        methodSelector.setEnabled(false);
                    }
                }
            }
        });
        
        methodSelector.addItemListener(new ItemListener() {
            java.lang.reflect.Parameter[] params = null;
            @Override
            public void itemStateChanged(ItemEvent e) {
                SaveName.setEnabled(false);
                panel.removeAll();
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (methodSelector.getSelectedItem() != "Choose Command") {
                        Method comm = methods.get(methodSelector.getSelectedItem().toString());
                        if(!"void".equals(comm.getReturnType().toString()))
                {
                    SaveName.setEnabled(true);
                }
                      
                        //excuteBotton.setToolTipText(comm.getMan());
                        params = comm.getParameters();
                        editors = new ArgumentEditor[params.length];
                        for (int i = 0; i < params.length; i++) {
                            ArgumentEditor argumentEditor = ArgumentEditor.createInstance(params[i]);
                            if (argumentEditor != null) {
                                panel.add(argumentEditor.getEditorPanel());
                            }
                            editors[i] = argumentEditor;
                        }
                        
                        panel.setLayout(new GridLayout(params.length + 2, 1));
                        panel.revalidate();
                        panel.add(excuteBotton);
                        panel.revalidate();
                    }
                }
            }
        });
        
        excuteBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(editors == null)
                    return;
                
                Argument arguments[] = new Argument[editors.length];
                
                for(int i=0; i<editors.length; i++) {
                    if(editors[i] == null)
                        return;
                    
                    arguments[i] = editors[i].getArgument();
                }
                
                
                Method comm = methods.get(methodSelector.getSelectedItem().toString());
             
                //editor.setParameter(param);
            
            
          
                
                Object[] args=new Object[arguments.length];
                for (int i = 0; i < args.length; i++) {
                    Type type = arguments[i].TYPE;
                    if(Engine.getInstance().contains(arguments[i].toString()))
                {
                    
                    args[i] = Engine.getInstance().getObject(arguments[i].toString());
                }
                else{
                    if(type == Type.INT)
                    {
                        args[i] = Integer.parseInt(arguments[i].toString());
                    }
                    else if (type == Type.FLOAT)
                    {
                    args[i] = Float.parseFloat(arguments[i].toString());
                    }
                    else if (type == Type.STR)
                    {
                        args[i] = arguments[i].toString();
                    }
                    else if(type == Type.OBJECT)
                    {
                        Class t=comm.getParameterTypes()[i];
                        if(arguments[i].toString().contains(","))
                        {
                        String[] split=arguments[i].toString().split(",");
                        Object[] splittedArgs = new Object[split.length];
                        Constructor[] allConstructors = t.getConstructors();
                            for (Constructor allConstructor : allConstructors) {
                                if(allConstructor.getParameterCount() == split.length)
                                {
                                    Class[] constTypes= allConstructor.getParameterTypes();
                                    for(int j = 0; j < constTypes.length; j ++)
                                    {
                                        if(constTypes[j] == String.class)
                                        {
                                            splittedArgs[j] = split[j];
                                        }
                                        else if (constTypes[j].toString().contains("int"))
                                        {
                                            splittedArgs[j] = Integer.parseInt(split[j]);
                                        }
                                        else if(constTypes[j].toString().contains("double"))
                                        {
                                             splittedArgs[j] = Double.parseDouble(split[j]);
                                        }
                                        else if(constTypes[j].toString().contains("long"))
                                        {
                                             splittedArgs[j] = Long.parseLong(split[j]);
                                         }
                                        else if(constTypes[j].toString().contains("float"))
                                        {
                                            splittedArgs[j] = Float.parseFloat(split[j]);
                                        }
                                    }
                                    try {
                                        args[i] = t.getConstructor(constTypes).newInstance(splittedArgs);
                                        
                                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                                        Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    break;
                                }
                            }
                        }
                       else
                        {
                            try {
                                args[i] = t.getConstructor().newInstance();
                                
                                Engine.getInstance().allocObject(arguments[i].toString(), args[i]);
                            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                        
                    }
                    
                }
                }
                try {
                 Object result =   comm.invoke(method.getInstance(comm), args);
                 
                 if(SaveName.getText().length()!=0)
                {
                    
                    Engine.getInstance().allocObject(SaveName.getText(), result);
                }
               
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                }
                
               
                
                if(comm == null)
                    return; // command not found
               // String command = 
               // comm.execute(arguments);
            }
            
        });
    }
    
    @Override
    public Component[] getNavigationComponents() {
        return new Component[]{
           classSelector,methodSelector,SaveName,};
    }
    
    @Override
    public JPanel getDockablePanel() {
        return this;
    }
    
}