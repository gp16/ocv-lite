package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 *
 * @author Elmohand Haroon
 */
public class CmdImgFlip extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() 
    {
        return new Parameter[]{
        new Parameter("NameToFlip", Type.MAT_ID, 1, null, "image name to get from memory", false, false),
        new Parameter("NameToSave", Type.MAT_ID, 1, null, "image name to save into memory", false, false)};
 
    }

    @Override
    protected Object executeSafe() 
    {
           String NameToFlip=getArgImgId("NameToFlip", 0);
           String NameToSave=getArgImgId("NameToSave", 0);
           Mat Source_Image = Engine.getInstance().getImage(NameToFlip);
           Mat Destination_Image = new Mat();
           
           Core.flip(Source_Image, Destination_Image, -1);
           
           Engine.getInstance().allocImage(NameToSave, Destination_Image);
           return null; 
    }

    @Override
    public String getName() 
    {
         return "toFlip";
    }

    @Override
    public String getMan() 
    {
        return "Flip Image";
    }
    
}
