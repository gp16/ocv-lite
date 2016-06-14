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
public class CmdBitwiseAnd extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() 
    {
        return new Parameter[]{
        new Parameter("Source", Type.MAT_ID, 1, null, "image name to get from memory", false, false),
        new Parameter("Source2", Type.MAT_ID, 1, null, "second image name to get from memory", false, false),
        new Parameter("Destination", Type.MAT_ID, 1, null, "image name to save into memory", false, false)};
 
    }

    @Override
    protected Object executeSafe() 
    {
           String Source=getArgImgId("Source", 0);
           String Source2=getArgImgId("Source2", 0);
           String Destination=getArgImgId("Destination", 0);
           Mat Source_Image = Engine.getInstance().getImage(Source);
           Mat Source_Image2 = Engine.getInstance().getImage(Source2);
           Mat Destination_Image = new Mat();
           
           Core.bitwise_and(Source_Image, Source_Image2, Destination_Image);
                   
           Engine.getInstance().allocImage(Destination, Destination_Image);
           return null; 
    }

    @Override
    public String getName() 
    {
         return "Bitwise_And";
    }

    @Override
    public String getMan() 
    {
        return "Bitwise_And Between Tow Images";
    }
    
}
