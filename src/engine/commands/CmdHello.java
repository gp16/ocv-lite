package engine.commands;

import engine.AbstractCommand;
import engine.Parameter;
import engine.Type;

public class CmdHello extends AbstractCommand {

    @Override
    public Object executeSafe()
    {	
	String personName = getArgString("person", 0);
	int times = getArgInt("times", 0);
	
	for(int i=0; i<times; i++) {
	    System.out.println("Hello, " + personName + "\n");
	}
	
	return null;
    }

    @Override
    protected Parameter[] getParamsOnce()
    {
	return new Parameter[] {
	  new Parameter("person", Type.STR, 1, null, "Name of the person to be greeted", false, false),
	  new Parameter("times", Type.INT, 1, 5, "How many times this person should be greeted", false, false),
	};
    }
    
    @Override
    public String getName()
    {
	return "hello";
    }

    @Override
    public String getMan()
    {
	return "Greets a person n times";
    }
}

