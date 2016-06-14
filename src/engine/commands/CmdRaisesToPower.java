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
public class CmdRaisesToPower extends AbstractCommand {

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
           
           Core.pow(Source_Image, 2, Destination_Image);
                   
           Engine.getInstance().allocImage(Destination, Destination_Image);
           return null; 
    }

    @Override
    public String getName() 
    {
         return "Raises To Power";
    }

    @Override
    public String getMan() 
    {
        return "Raises Every Pixel In Image To Power";
    }
    
}
