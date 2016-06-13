package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 *
 * @author AmrGamal
 */
public class CmdDivide extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
           new Parameter("Source", Type.MAT_ID, 1, null, "First image name to get from memory", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null, "Second image name to get from memory", false, false),
            new Parameter("Result", Type.MAT_ID, 1, null, "Result image name that will be saved in memory", false, false)};
    }

    @Override
    protected Object executeSafe() {
        String Source = getArgImgId("Source", 0);
        String Destination = getArgImgId("Destination", 0);
        String Result = getArgImgId("Result", 0);
        Mat Source_Mat = Engine.getInstance().getImage(Source);
        Mat Destination_Mat = Engine.getInstance().getImage(Destination);
        Mat result_Mat = new Mat();
        Core.divide(Source_Mat, Destination_Mat, result_Mat);
        Engine.getInstance().allocImage(Result, result_Mat);
        return null;
    }

    @Override
    public String getName() {
        return "divide";
    }

    @Override
    public String getMan() {
        return "divide two images.";
    }

}
