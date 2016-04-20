package engine;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
/**
 * Represents a TYPE that a command expects.
 * 
 * @see ICommand#getParams() 
 */
public class Parameter {
   public Type[] TYPE = null;
    public java.lang.reflect.Parameter[] getParameters(Method method)
    {
        java.lang.reflect.Parameter[] parameters = method.getParameters();
        return parameters;
    }
    
    public Class[] getParamTypes(Method method)
    {
       Class[] paramTypes = method.getParameterTypes();
       TYPE=new Type[paramTypes.length];
        for (int i = 0; i<TYPE.length; i++) {
            if(paramTypes[i].toString().contains("int"))
            {
                TYPE[i]=Type.INT;
            }
            else if(paramTypes[i].toString().contains("double"))
            {
                TYPE[i]=Type.INT;
            }
            else if(paramTypes[i].toString().contains("float"))
            {
                TYPE[i]=Type.FLOAT;
            }
            else if(paramTypes[i].toString().contains("long"))
            {
                TYPE[i]=Type.FLOAT;
            }
            else if(paramTypes[i].toString().contains("String"))
            {
                TYPE[i]=Type.STR;
            }
        }
       return paramTypes;
    }
    
    public Class getParamType(java.lang.reflect.Parameter parameter)
    {
        return parameter.getType();
    }
    
    public int getParamCount(Method method)
    {
        int paramCount = method.getParameterCount();
        return paramCount;
    }
    public Constructor[] getParamConstructors(Class paramType)
    {
        Constructor[] constructors = paramType.getConstructors();
        return constructors;
    }
    
    
}
