package engine;

/**
 * 
 * An interface to encapsulate OpenCV functionalities.
 */
public interface ICommand
{
    /**
     * Defines the name of the command, which is used to access it.
     * 
     * @return a String representing the name or Id of the game
     */
    public String getName();
    
    /**
     * Defines the manual of the command.
     * @return A brief description about the command (not including parameters)
     */
    public String getMan();
    
    /**
     * Defines the parameters of the command, this means, its signature.
     * 
     * @return An array of parameters that describe what arguments the command
     * expect.
     * 
     * @see #execute(Object... arguments)
     * @see Parameter
     */
    public Parameter[] getParams();
    
    /**
     * Defines command behavior.
     * 
     * @param arguments The arguments to be passed to the command
     * @return Not used
     */
    public Object execute(Argument... arguments);
}