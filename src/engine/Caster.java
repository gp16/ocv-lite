/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amr_Ayman
 * casting arguments dynamically to there respective type 
 */
public class Caster 
{
    Parameter parameter=new Parameter();
    
    public Object Cast(Class type, String argument)
    {
        Object arg = null;
        if(Engine.getInstance().contains(argument))
        {
          arg=Engine.getInstance().getObject(argument);
        }
        
        else
        {
            if(type.toString().contains("int"))
            {
                arg= Integer.parseInt(argument);
            }
            else if(type.toString().contains("double"))
            {
                arg= Double.parseDouble(argument);
            }
            else if(type.toString().contains("float"))
            {
                arg= Float.parseFloat(argument);
            }
            else if(type.toString().contains("long"))
            {
                arg= Long.parseLong(argument);
            }
            else if(type.toString().contains("String"))
            {
                arg= argument;
            }
            else
            {
                   try {
                    Constructor constructor= type.getConstructor();
                    
                    arg = constructor.newInstance();
                    Engine.getInstance().allocObject(argument, arg);
                } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(Caster.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
           
            
        }
         return arg;
    }
    
}
