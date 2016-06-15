package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;




/**
 *
 * @author Elmohand Haroon
 */
public class CmdImgResize extends AbstractCommand{

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("Source", Type.MAT_ID, 1, null, "image name to get from memory", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null, "image name to save into memory", false, false),
        };
    }

    @Override
    protected Object executeSafe() {
        String Source = getArgImgId("Source", 0);
        String Destination = getArgImgId("Destination", 0);
        Mat Source_Mat = Engine.getInstance().getImage(Source);
        Mat Destination_Mat = new Mat();
        Size sz = new Size(100,100);
        Imgproc.resize(Source_Mat, Destination_Mat, sz);
        Engine.getInstance().allocImage(Destination, Destination_Mat);  
        return null;
    }

    @Override
    public String getName() {
        return "resize";
    }

    @Override
    public String getMan() {
        return "Resize Image";
    }

}
