package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
/**
* This command frees an image from engine memory 
* @author  Elmohand Haroon
* @version 1.0
* @since   2015
*/
public class CmdImgFree extends AbstractCommand 
{
    
    /**
    * @return array of one object representing the image name to be freed
    */  
    @Override
    protected Parameter[] getParamsOnce() 
    {
        return new Parameter[] 
        {
            new Parameter("imageName", Type.STR, 1, 0, "name of image to be deleted", false, false)
        };
    }

    @Override
    protected Object executeSafe() 
    {
    try 
    {
        String name = stringArgs.get("imageName").get(0);
        
        Engine.getInstance().deallocImage(name);
    }
    catch (Exception e )
    {
       System.out .println(e);
    }
     return null;
    }

    @Override
    public String getName() 
    {
        return("free");
    }

    @Override
    public String getMan() 
    {
        return("Delete image from memory");
    }
    
}
