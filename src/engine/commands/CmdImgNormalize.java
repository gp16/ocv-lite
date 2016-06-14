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
public class CmdImgNormalize extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() 
    {
        return new Parameter[]{
        new Parameter("Source", Type.MAT_ID, 1, null, "image name to get from memory", false, false),    
        new Parameter("Destination", Type.MAT_ID, 1, null, "image name to save into memory", false, false)};
 
    }

    @Override
    protected Object executeSafe() 
    {
           String Source=getArgImgId("Source", 0);
           String Destination=getArgImgId("Destination", 0);
           Mat Source_Image = Engine.getInstance().getImage(Source);
           Mat Destination_Image = new Mat();
           
           Core.normalize(Source_Image, Destination_Image, 32.0, 1.0, 32);
                   
           Engine.getInstance().allocImage(Destination, Destination_Image);
           return null; 
    }

    @Override
    public String getName() 
    {
         return "Normalize";
    }

    @Override
    public String getMan() 
    {
        return "Normalize Image";
    }
    
}
