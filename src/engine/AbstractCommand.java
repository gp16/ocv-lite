
package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractCommand implements ICommand {
    private  Parameter[] params;
    protected HashMap<String, List<String>> stringArgs = new HashMap<>();
    protected HashMap<String, List<Double>> floatArgs = new HashMap<>();
    protected HashMap<String, List<Integer>> intArgs = new HashMap<>();
    
    protected abstract Parameter[] getParamsOnce();
    
    public String getFullMan() {
        StringBuilder buffer = new StringBuilder();
        
        buffer.append(getName() + ": " + getMan());
        buffer.append("\n");
        buffer.append("Parameters...\n");
        
        Parameter[] params = getParamsOnce();
        
        for(int i=0; i<params.length; i++)
        {
            buffer.append(params[i].NAME + ": ");
            buffer.append(params[i].MAN);
            
            buffer.append("\n");
        }
        return buffer.toString();
    }
    
    @Override
    public final Parameter[] getParams()
    {
	if(params == null)
	    params = getParamsOnce();
	
	if(params == null) 
	    params = new Parameter[0]; 
	
	return params;
    }
    
    @Override
    public Object execute(Object... arguments) {
	loadArguments(arguments);
	return executeSafe();
    }
    
    protected abstract Object executeSafe();
    
    private void loadArguments(Object[] arguments)
    {
	if(arguments == null || arguments.length <= 0)
	    return;
	
	unloadArguments();

	int argI = 0;
	
	paramloop: for (Parameter param : getParams())
	{
	    while (argI < arguments.length)
	    {
		
		if (checkType(param.TYPE, arguments[argI]))
		{
		    //TODO: take min and max into consideration as they are
		    // currently ignored
		    loadArgument(param.NAME, arguments[argI], param.TYPE);
		    
		    argI++;
		    if (!param.RECURR)
		    {
			break;
		    }
		}
		else if (param.RECURR && argI > 0 && checkType(param.TYPE, arguments[argI - 1]))
		{
		    break;
		}
		else if (param.OPT)
		{
		    break;
		}
		else
		{
		    //TODO: throw exception
		    System.out.println("Unexpected argument!");
		    break paramloop;
		}
	    }
	}
	
	if(argI < arguments.length) {
	    // Some arguments where not loaded
	    //TODO: better error handling
	    System.out.println("Too much arguments!");
	}
    }
    
    private void unloadArguments() {
	// delete old args
	stringArgs.clear();
	intArgs.clear();
	floatArgs.clear();	
    }
    
    private boolean checkType(Type type, Object obj) {
	
	if(type == Type.STR && obj instanceof String)
	    return true;
	
	if(type == Type.INT && obj instanceof Integer)
	    return true;
	
	if(type == Type.FLOAT && obj instanceof Double)
	    return true;
	
	return false;
    }
    
    private void loadArgument(String name, Object argument, Type type) {
	// TODO: eliminate duplicate code using generics
	
	if(type == Type.STR) {
	    List<String> argWrapper = stringArgs.get(name);
	    
	    if(argWrapper == null)
	    {
		argWrapper = new ArrayList<>();
		argWrapper.add((String)argument);		
		stringArgs.put(name, argWrapper);
	    }
	    else
	    {
		argWrapper.add((String)argument);
	    }
	}
	else if(type == Type.INT) {
	    List<Integer> argWrapper = intArgs.get(name);
	    
	    if(argWrapper == null)
	    {
		argWrapper = new ArrayList<>();
		argWrapper.add((Integer)argument);
		intArgs.put(name, argWrapper);
	    }
	    else
	    {
		argWrapper.add((Integer)argument);
	    }
	}
	else if(type == Type.FLOAT) {
	    List<Double> argWrapper = floatArgs.get(name);
	    
	    if(argWrapper == null)
	    {
		argWrapper = new ArrayList<>();
		argWrapper.add((Double)argument);
		floatArgs.put(name, argWrapper);
	    }
	    else
	    {
		argWrapper.add((Double)argument);
	    }
	}
    }
}