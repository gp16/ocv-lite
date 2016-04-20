/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import interpreter.Interpreter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amr_Ayman
 */
public class Methods {
  private final  HashMap<String,Object> instances = new HashMap<>();
    
    public String getMethodName(Method method)
    {
        return method.getName();
    }
    
    
    public Method[] getAllMethods(Class clazz)
    {
        Method[] method = clazz.getDeclaredMethods();
        List<Method> PublicMethods=new ArrayList<>();
        for (Method method1 : method) {
            String[] PublicCheck = method1.toString().split(" ");
            if(PublicCheck[0].equals("public"))
            {
                PublicMethods.add(method1);
            }
            
        }
        Method[] publicMethods = new Method[PublicMethods.size()];
        for(int i=0;i<PublicMethods.size();i++)
        {
            publicMethods[i]=PublicMethods.get(i);
        }
       return publicMethods;
    }
    
    
    public Method getMethod(Class clazz,String MethodName,Class[] parameterTypes)
    {
        Method method=null;
        try {
        method = clazz.getMethod(MethodName, parameterTypes);
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
        }
        return method;
    }
    
        public Object getInstance(Method method)
    {
        Object clazz = null;
        try {
            if(!instances.containsKey(method.getDeclaringClass().toString()))
            {
                clazz = method.getDeclaringClass().newInstance();
                instances.put(method.getDeclaringClass().toString(), clazz);
            }
            else
            {
                clazz=instances.get(method.getDeclaringClass().toString());
            }
            
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
        }
       return clazz;
    }
        
        
    public Object invoke(Object instance,Method method,Object[] args)
    {
        Object result=null;
        try {
           result = method.invoke(instance, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
