package interpreter;

import engine.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AmrAyman
 */
public class Interpreter 
{
    Engine engine = Engine.getInstance();
    TreeMap<String,Method> Methods = engine.getMethods();
    TreeMap<String,Class> Classes = engine.getClasses();
    
   public boolean Interpret(String command)
   {
       String[] CommandParts = command.split(" ",2);
       String MethodPart = CommandParts[0];
       String ArgumentPart = CommandParts[1];
       Class clazz = InterpretClass(MethodPart);
       List<String> ArgumentNames = InterpretArguments(ArgumentPart);
       Method method = InterpretMethod(MethodPart,ArgumentNames.size());
       if(method != null)
       {
           if(method.getDeclaringClass().getSimpleName().equals(clazz.getSimpleName()))
           {
               Object[]Arguments = CastArguments(ArgumentNames, method);
               engine.ExecuteMethod(method, Arguments);
               return true;
           }
       }
       return false;
    }
   private Class InterpretClass(String MethodPart)
   {
       MethodPart = MethodPart.replace(".", " ");
       String [] ClazzAndMethod = MethodPart.split(" ",2);
       String ClassName = ClazzAndMethod[0];
       Class clazz = Classes.get(ClassName);
       return clazz;
   }
   private Method InterpretMethod(String MethodPart,int ArgumentLength)
   {
       
       MethodPart = MethodPart.replace(".", " ");
       String [] ClazzAndMethod = MethodPart.split(" ",2);
       String MethodName = ClazzAndMethod[1];
       Method method = Methods.get(MethodName+" :: "+ArgumentLength);
       
       return method;
   }
   private List<String> InterpretArguments(String ArgumentPart)
   {
       StringBuilder buffer = new StringBuilder();
       List<String>ArgumentNames = new ArrayList<>();
       char DefaultTerminal = ',';
       char BracketStarter = '[';
       char BracketTerminal = ']';
       char CommandTerminal = ';';
       char SaveTerminal = '\'';
       char CurrentTerminal = DefaultTerminal;
       for(int i = 0 ; i < ArgumentPart.length() ; i++)
       {
           char current = ArgumentPart.charAt(i);
           if(current != CurrentTerminal)
           {
               if(current == BracketStarter)
               {
                   CurrentTerminal = BracketTerminal;
               }
               else if(current == SaveTerminal)
               {
                   CurrentTerminal = SaveTerminal;
               }
               else if(current == CommandTerminal)
               {
                   if(buffer.length() != 0)
                   {
                       ArgumentNames.add(buffer.toString());
                       buffer.delete(0, buffer.length());
                   }
                   break;
               }
               else
               {
                   buffer.append(current);
               }
           }
           else if (current == CurrentTerminal)
           {
               if(CurrentTerminal == BracketTerminal)
               {
                   CurrentTerminal = DefaultTerminal;
                   ArgumentNames.add(buffer.toString());
                   buffer.delete(0, buffer.length());
               }
               else if(CurrentTerminal == SaveTerminal)
               {
                   CurrentTerminal = DefaultTerminal;
                   engine.save = buffer.toString();
                   buffer.delete(0, buffer.length());
               }
               else if(CurrentTerminal == DefaultTerminal)
               {
                   char previous = ArgumentPart.charAt(i-1);
                   if(previous != BracketTerminal)
                   {
                       ArgumentNames.add(buffer.toString());
                       buffer.delete(0, buffer.length());
                   }
               }
           }
       }
       return ArgumentNames;
   }
   private Object[] CastArguments(List<String>ArgumentNames,Method method)
   { 
       List<Object> Arguments = new ArrayList<>();
       
         Class[]  ArgumentTypes = method.getParameterTypes();
       
       
       Object Argument = null;
       for(int i = 0 ; i < ArgumentNames.size() ; i++)
       {
           if(engine.isInMemory(ArgumentNames.get(i)))
           {
               Argument = engine.getFromMemory(ArgumentNames.get(i));
           }
           else
           {
           if(ArgumentTypes[i].equals(String.class))
           {
               Argument = ArgumentNames.get(i);
           }
           else if(ArgumentTypes[i].equals(int.class))
           {
               Argument = Integer.parseInt(ArgumentNames.get(i));
           }
           else if(ArgumentTypes[i].equals(double.class))
           {
               Argument = Double.parseDouble(ArgumentNames.get(i));
           }
           else if(ArgumentTypes[i].equals(long.class))
           {
               Argument = Long.parseLong(ArgumentNames.get(i));
           }
           else if(ArgumentTypes[i].equals(boolean.class))
           {
               Argument = Boolean.parseBoolean(ArgumentNames.get(i));
           }
           else if(ArgumentTypes[i].equals(float.class))
           {
               Argument = Float.parseFloat(ArgumentNames.get(i));
           }
           else
           {
               if(ArgumentNames.get(i).contains(","))
               {
                   List<Object> CompositArguments = new ArrayList<>();
                   String[] CompositArgumentNames = ArgumentNames.get(i).split(",");
                  
                   Constructor[] CompositConstructors = ArgumentTypes[i].getConstructors();
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
                       Argument = CompositConstructor.newInstance(CompositArguments.toArray());
                   } 
                   catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                       Logger.getLogger(Interpreter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
               else
               {
               try {
                   Constructor TypeConstructor = ArgumentTypes[i].getConstructor();
                   Argument = TypeConstructor.newInstance();
                   engine.addToMemory(ArgumentNames.get(i), Argument);
               } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                   Logger.getLogger(Interpreter.class.getName()).log(Level.SEVERE, null, ex);
               }
               }
           }
           }
           Arguments.add(Argument);
       }
       return Arguments.toArray();
   }
}
