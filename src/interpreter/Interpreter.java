package interpreter;

import engine.ICommand;
import engine.Engine;
import java.util.StringTokenizer;

/**
 *
 * @author Elmohand Haroon ,......
 */
public class Interpreter {

    String user_command = "";

    public Interpreter(String user_command) 
    {
        this.user_command += user_command;
    }

    public ICommand get_command() 
    {
        
        StringTokenizer stringTokenizer = new StringTokenizer(user_command, " ");
        String command = "";
        command += stringTokenizer.nextToken();
        ICommand icomm=  Engine.getInstance().getCommand(command);
        
        if(icomm.equals(null))
        {
            System.out.println("There is no command with this name ");
            return null;
        }
        else
        {
            
            return icomm;
        
        }  
        
   }

}
