package interpreter;

import engine.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elmohand Haroon, AmrAyman, Abdelrahman Mohsen
 */
public class Interpreter {
   private final Parameter parameters = new Parameter();
   private final Methods methods = new Methods();
   private final Caster caster=new Caster();
    public Method get_Method(String MethodName,int argsCount) 
    {
        Method Correctmethod=null;
        List<Method> method =  Engine.getInstance().getMethod(MethodName);
        for (Method currentMethod : method) {
            int paramCount= parameters.getParamCount(currentMethod);
            if(paramCount == argsCount)
            {
                Correctmethod = currentMethod;
            }
        }
        if(Correctmethod == null)
        {
            System.out.println("There is no command with this name ");
            return null;
        }
        else
        {
            return Correctmethod;
        }  
    }
    
    public Object[] getArguments(String source)
    {
        List<Object> arguments = new ArrayList<>();
        StringBuffer buffer=new StringBuffer();
        char current;
        char endCommand=';';
        char terminator=',';
        char lookBefore;
        if(source.contains(" "))
        {
            String[] args = source.split(" ");
            source = args[0];
            Engine.getInstance().setSavingName(args[1]);
        }
        for(int i=0;i<source.length();i++)
        {
            current=source.charAt(i);
            if(current!=endCommand)
            {
                
                if(current==terminator)
                {
                    if(terminator==')')
                    {
                        buffer.append(current);
                        terminator=',';
                    }
                    lookBefore=source.charAt(i-1);
                    if(lookBefore!=')')
                    {
                        arguments.add(buffer.toString());
                        buffer.delete(0, buffer.length());
                    }
                }
                else if(current!=terminator)
                {
                    if(current=='(')
                    {
                        buffer.append(current);
                        terminator=')';
                    }
                    else{
                    buffer.append(current);
                    }
                }
            }
            else
            {
                if(buffer.length() != 0)
                {
                    arguments.add(buffer.toString());
                    buffer.delete(0, buffer.length());
                    
                }
                else{
                    
                    break;
                }
            }
        }
        return arguments.toArray();
    }
    private Object[] CastArguments(Object[] stringArguments,Method method)
    {
        
        String[] bracketArgs=null;
        Constructor bracket_constructor=null;
        List<Object> CorrectArgs = new ArrayList<>();
        List<Object> bracket_args = new ArrayList<>();
        Class[] paramTypes = parameters.getParamTypes(method);
        for(int i=0;i<stringArguments.length;i++)
        {
            if(stringArguments[i].toString().contains("(")&&stringArguments[i].toString().contains(")"))
            {
                stringArguments[i]=stringArguments[i].toString().replace("(", "");
                stringArguments[i]=stringArguments[i].toString().replace(")", "");
                bracketArgs = stringArguments[i].toString().split(",");
                
                Constructor[] constructors = parameters.getParamConstructors(paramTypes[i]);
                for(int j=0;j<constructors.length;j++)
                {
                    if(constructors[j].getParameterCount() == bracketArgs.length)
                    {
                        bracket_constructor = constructors[j];
                        break;
                    }
                }
                
                Class[]bracketParam_types = bracket_constructor.getParameterTypes();
                
                for(int j=0;j<bracketArgs.length;j++)
                {
                    bracket_args.add(caster.Cast(bracketParam_types[j], bracketArgs[j]));
                }
                try {
                    CorrectArgs.add(bracket_constructor.newInstance(bracket_args.toArray()));
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(Interpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else
            {
                
                CorrectArgs.add(caster.Cast(paramTypes[i], (String) stringArguments[i]));
            }
           
        }
        return CorrectArgs.toArray();
    }
    private Object[] checkForObjects(String MethodName,int argLength)
    {
        Object[] MethodAndObject = new Object[2];
        if(MethodName.contains("."))
        {
            String []M_And_O=MethodName.replace(".", " ").split(" ");
            
            MethodAndObject[0]=Engine.getInstance().getObject(M_And_O[0]);
            MethodAndObject[1]=get_Method(M_And_O[1], argLength);
        }
        else
        {
            MethodAndObject[0]="-1"; //empty
            MethodAndObject[1]=get_Method(MethodName, argLength);
        }
        return MethodAndObject;
    }
    public Object executeCommand(String source)
    {
        
        String[] MethodAndArgs = source.split(" ", 2);
        Object[] arguments=getArguments(MethodAndArgs[1]);
        
        Object[] MethodAndObject = checkForObjects(MethodAndArgs[0], arguments.length);
       Method method = (Method) MethodAndObject[1];
       Object[] args = CastArguments(arguments, method);
       Object result = null;
        if(MethodAndObject[0]=="-1")
        {
            
           result = methods.invoke(methods.getInstance(method), method, args);
        }
        else{
            result = methods.invoke(MethodAndObject[0], method, args);
        }
        if(Engine.getInstance().getSavingName()!= null)
        {
           Engine.getInstance().allocObject(Engine.getInstance().getSavingName(), result);
           Engine.getInstance().setSavingName(null);
        }
       return null;
    }
}
