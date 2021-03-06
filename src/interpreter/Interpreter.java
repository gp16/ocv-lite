package interpreter;

import engine.ICommand;
import engine.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Elmohand Haroon, AmrAyman, Abdelrahman Mohsen
 */
public class Interpreter {

    public ICommand get_command(String command) 
    {
        ICommand icomm=  Engine.getInstance().getCommand(command);
        if(icomm == null)
        {
            System.out.println("There is no command with this name ");
            return null;
        }
        else
        {
            return icomm;
        }  
    }
    
    public Argument[] getArguments(String source)
    {
        List<Argument> arguments=new ArrayList<>();
        Character terminator=null;
        Type type=null;
        StringBuilder buffer=new StringBuilder();
        Character current;
        for(int counter=0;counter<source.length();counter++)
        {
            current=source.charAt(counter);
            if(Objects.equals(current, terminator))
            {
                Object value;
                if(type==Type.INT)
                {
                    value=Integer.parseInt(buffer.toString());
                }
                else if(type==Type.FLOAT)
                {
                    value=Double.parseDouble(buffer.toString());
                }
                else
                {
                    value=buffer.toString();
                }
                Argument argument=new Argument(type, value); 
                arguments.add(argument);
                buffer.delete(0,buffer.length());
                terminator=null; 
                type=null;
            }
            else //if current != terminator
            {
                if(type==null)
                {
                    if (current=='\'')
                    {
                        type=Type.STR;
                        terminator='\'';
                    }
                    else if(current=='_'||Character.isLetter(current))
                    {
                        buffer.append(current);
                        type=Type.MAT_ID;
                        terminator=' ';   
                    }
                    else if (Character.isDigit(current))
                    {
                        buffer.append(current);
                        type=Type.INT;
                        terminator=' ';
                    }
                }
                else //if type !=null
                {
                    if(current=='\'')
                    {
                        Character prefix=buffer.charAt(0);
                        buffer.delete(0,buffer.length());
                        if(prefix=='p')
                        {
                            type=Type.SYS_PATH;
                        }
                        else if(prefix=='c')
                        {
                            type=Type.CMD_ID;
                        }
                        terminator='\'';
                    }
                    else if(current=='.')
                    {
                        if(type==Type.INT)
                        {
                            type=Type.FLOAT;
                        }
                        buffer.append(current);
                    }
                    else
                    {
                        buffer.append(current);
                    }
                }
            }
        }
        return arguments.toArray(new Argument[0]);
    }
    
    public String executeCommand(String source)
    {
        String[] cmdAndArgs = source.split(" ", 2);
        ICommand icomm = get_command(cmdAndArgs[0]);
	Argument[] arguments = getArguments(cmdAndArgs[1]);
        if(icomm.execute(arguments)!=null)
        {
            return icomm.execute(arguments).toString();
        }
        return null;
    }
}
