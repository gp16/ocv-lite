
package ui.dockables;

import engine.Argument;
import engine.Engine;
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
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.prompt.PromptSupport;
import ui.api.Dockable;
import ui.api.autoCompletionComboBox;
import ui.editors.ArgumentEditor;

public class Command extends JPanel implements Dockable {
    private Engine engine = Engine.getInstance();
    private JComboBox methodSelector;
    private JComboBox classSelector;
    private JPanel panel = new JPanel();
    private JTextField SaveName = new JTextField(10);
    private JButton excuteBotton = new JButton("execute");
    private ArgumentEditor[] editors = null;
    private TreeMap<String,Method> methods = null;
    private TreeMap<String,Class> Classes = null;
    public Command() {
        Classes = engine.getClasses();
        add(panel, BorderLayout.WEST);
        classSelector = new autoCompletionComboBox();
        methodSelector = new autoCompletionComboBox();
        SaveName.setEnabled(false);
        methodSelector.setEnabled(false);
        PromptSupport.setPrompt("save name", SaveName);
        methodSelector.addItem("Choose Command");
        methodSelector.setSelectedItem("Choose Command");
        classSelector.addItem("Choose Class");
        classSelector.setSelectedItem("Choose Class");
        
        // add classes to classes combo-box
        for (Map.Entry<String, Class> clazz : Classes.entrySet()) {
            String key = clazz.getKey();
            classSelector.addItem(key);
        }
        //class combo-box listner
        classSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    SaveName.setText("");
                    SaveName.setEnabled(false);
                    if (classSelector.getSelectedItem() != "Choose Class")
                    {
                        methodSelector.removeAllItems();
                        methodSelector.addItem("Choose Command");
                        methodSelector.setSelectedItem("Choose Command");
                        methods = engine.getMethod(Classes.get(classSelector.getSelectedItem()));
                        //add methods to methods combo-box
                        for (Map.Entry<String, Method> method : methods.entrySet()) {
                            String MethodName = method.getKey();
                            methodSelector.addItem(MethodName);
                        }
                        methodSelector.setEnabled(true);
                    }
                    else
                    {
                        methodSelector.removeAllItems();
                        methodSelector.addItem("Choose Command");
                        methodSelector.setSelectedItem("Choose Command");
                        methodSelector.setEnabled(false);
                    }
                }
            }
        });
        //method combo-box listner
        methodSelector.addItemListener(new ItemListener() {
            Parameter[] params = null;
            @Override
            public void itemStateChanged(ItemEvent e) {
                SaveName.setText("");
                SaveName.setEnabled(false);
                panel.removeAll();
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (methodSelector.getSelectedItem() != "Choose Command") {
                        Method method = methods.get(methodSelector.getSelectedItem().toString());
                        if(!(method.getReturnType().toString()).equals("void"))
                        {
                            SaveName.setEnabled(true);
                        }
                        params = method.getParameters();
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
        //execute button lisitner
        excuteBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(editors == null)
                {
                    return;
                }
                Argument arguments[] = new Argument[editors.length];
                for(int i=0; i<editors.length; i++) 
                {
                    if(editors[i] == null)
                    {
                        return;
                    }
                    else
                    {
                        arguments[i] = editors[i].getArgument();    
                    }
                }
                Method method = methods.get(methodSelector.getSelectedItem().toString());
                Object[] args=new Object[arguments.length];
                for(int i = 0 ; i < arguments.length ; i++)
                {
                    String ArgumentName = arguments[i].toString();
                    if(engine.isInMemory(ArgumentName))
                    {
                        args[i] = engine.getFromMemory(ArgumentName);
                    }
                    else
                    {
                        Type type = arguments[i].TYPE;
                        if(type == Type.INT)
                        {
                            args[i] = Integer.parseInt(ArgumentName);
                        }
                        else if (type == Type.FLOAT)
                        {
                            args[i] = Float.parseFloat(ArgumentName);
                        }
                        else if (type == Type.STR)
                        {
                            args[i] = ArgumentName;
                        }
                        else if (type == Type.BOOLEAN)
                        {
                            args[i] = Boolean.parseBoolean(ArgumentName);
                        }
                        else if(type == Type.OBJECT)
                        {
                            Class ArgumentType = method.getParameterTypes()[i];
                            if(ArgumentName.contains(","))
                            {
                                List<Object> CompositArguments = new ArrayList<>();
                                String[] CompositArgumentNames = ArgumentName.split(",");
                                Constructor[] CompositConstructors = ArgumentType.getConstructors();
                                Constructor CompositConstructor = null;
                                for(Constructor aCompositConstructor : CompositConstructors)
                                {
                                    if(aCompositConstructor.getParameterCount() == CompositArgumentNames.length)
                                    {
                                        CompositConstructor = aCompositConstructor;
                                        break;
                                    }
                                }
                                Class[] CompositParameterTypes=CompositConstructor.getParameterTypes();
                                for(int j = 0 ; j< CompositArgumentNames.length;j++)
                                {
                                    if(CompositParameterTypes[j].equals(String.class))
                                    {
                                        CompositArguments.add(CompositArgumentNames);
                                    }
                                    else if(CompositParameterTypes[j].equals(int.class))
                                    {
                                        CompositArguments.add(Integer.parseInt(CompositArgumentNames[j]));
                                    }
                                    else if(CompositParameterTypes[j].equals(double.class))
                                    {
                                        CompositArguments.add(Double.parseDouble(CompositArgumentNames[j]));
               
                                    }
                                    else if(CompositParameterTypes[j].equals(long.class))
                                    {
                                        CompositArguments.add(Long.parseLong(CompositArgumentNames[j]));
               
                                    }
                                    else if(CompositParameterTypes[j].equals(boolean.class))
                                    {
                                        CompositArguments.add( Boolean.parseBoolean(CompositArgumentNames[j]));
                                    }   
                                    else if(CompositParameterTypes[j].equals(float.class))
                                    {
                                        CompositArguments.add(Float.parseFloat(CompositArgumentNames[j]));
                                    }
                                }
                                try 
                                {
                                    args[i] = CompositConstructor.newInstance(CompositArguments.toArray());
                                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                    Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else
                            {
                                try {
                                    Constructor TypeConstructor = ArgumentType.getConstructor();
                                    args[i] = TypeConstructor.newInstance();
                                    engine.addToMemory(ArgumentName, args[i]);
                                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                                    Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
                //check for save name that the result will be saved with in mem.
                if(SaveName.getText().length() !=0)
                {
                    engine.save = SaveName.getText();
                    SaveName.setText("");
                }
                engine.ExecuteMethod(method, args);
                
                if(method == null)
                {
                    return; // command not found
                }
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