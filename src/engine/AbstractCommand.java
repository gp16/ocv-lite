
package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides common functionalities like generating full man, caching parameters,
 * loading arguments before execution, etc,.
 * 
 */
public abstract class AbstractCommand implements ICommand {
    private  Parameter[] params;
    protected HashMap<String, List<String>> stringArgs = new HashMap<>();
    protected HashMap<String, List<Double>> floatArgs = new HashMap<>();
    protected HashMap<String, List<Integer>> intArgs = new HashMap<>();
    protected Map<String, List<String>> imgIdArgs = new HashMap<>();
    protected Map<String, List<String>> cmdIdArgs = new HashMap<>();
    protected Map<String, List<String>> pathArgs = new HashMap<>();
    
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
    public Object execute(Argument... arguments) {
	loadArguments(arguments);
	return executeSafe();
    }
    
    protected abstract Object executeSafe();
    
    private void loadArguments(Argument[] arguments)
    {
	if(arguments == null || arguments.length <= 0)
	    return;
	
	unloadArguments();

	int argI = 0;
	
	paramloop: for (Parameter param : getParams())
	{
	    while (argI < arguments.length)
	    {
		
		if (checkArgument(param, arguments[argI]))
		{
		    loadArgument(param.NAME, arguments[argI], param.TYPE);
		    
		    argI++;
		    if (!param.RECURR)
		    {
			break;
		    }
		}
		else if (param.RECURR && argI > 0 && checkArgument(param, arguments[argI - 1]))
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
	imgIdArgs.clear();
	cmdIdArgs.clear();
	pathArgs.clear();
    }
    
    private boolean checkArgument(Parameter param, Argument arg) {
	if(param.TYPE == arg.TYPE) {
	    // check max and min
	    if(arg.TYPE == Type.STR) {
		if(param.MAX != null && arg.toString().length() > param.MAX)
		    return false;
		if(param.MIN != null && arg.toString().length() < param.MIN)
		    return false;
	    }
	    else if(arg.TYPE == Type.INT || arg.TYPE == Type.FLOAT)
	    {
		if(param.MAX != null && arg.toDouble() > param.MAX)
		    return false;
		if(param.MIN != null && arg.toDouble() < param.MIN)
		    return false;
	    }
	    return true;
	}
	
	return false;
    }
    
    private void loadArgument(String name, Argument argument, Type type) {	
	if(type == Type.STR)
	    insertToMap(name, argument.toString(), stringArgs);
	else if(type == Type.INT)
	    insertToMap(name, argument.toInt(), intArgs);
	else if(type == Type.FLOAT)
	    insertToMap(name, argument.toDouble(), floatArgs);
	else if(type == Type.IMG_ID)
	    insertToMap(name, argument.toString(), imgIdArgs);
	else if(type == Type.CMD_ID)
	    insertToMap(name, argument.toString(), cmdIdArgs);
	else if(type == Type.SYS_PATH)
	    insertToMap(name, argument.toString(), pathArgs);
    }
    
    private <T> void insertToMap(String name, T argument, Map<String, List<T>> argsMap) {
	List<T> argWrapper = argsMap.get(name);

	if(argWrapper == null)
	{
	    argWrapper = new ArrayList<>();
	    argWrapper.add(argument);
	    argsMap.put(name, argWrapper);
	}
	else
	{
	    argWrapper.add(argument);
	}
    }
}
