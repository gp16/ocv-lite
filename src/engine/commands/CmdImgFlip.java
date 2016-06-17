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
        new Parameter("Source", Type.MAT_ID, 1, null, "image name to get from memory", false, false),
        new Parameter("Destination", Type.MAT_ID, 1, null, "image name to save into memory", false, false),
        new Parameter("flipCode", Type.INT, -1, 1, "Flip types : \n" +
                        "      1-: horizontal and vertical flipping of the image\n" +
                        "      0: Vertical flipping of the image\n" +
                        "      1: Horizontal flipping of the image", false, false)
                };
    }

    @Override
    protected Object executeSafe() 
    {
           String Source=getArgImgId("Source", 0);
           String Destination=getArgImgId("Destination", 0);
           int flipCode = getArgInt("flipCode", 0);
           Mat Source_Image = Engine.getInstance().getImage(Source);
           Mat Destination_Image = new Mat();
           Core.flip(Source_Image, Destination_Image, flipCode);
           Engine.getInstance().allocImage(Destination, Destination_Image);
           return null; 
    }

    @Override
    public String getName() 
    {
         return "flip";
    }

    @Override
    public String getMan() 
    {
        return "Flip Image";
    }
    
}
