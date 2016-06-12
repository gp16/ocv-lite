package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 *
 * @author AmrAyman
 */
public class CmdRandShuffle extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("Source Image", Type.MAT_ID, 1, null, "image name to get from memory", false, false)
        };
    }

    @Override
    protected Object executeSafe() {
        String Source = getArgImgId("Source Image", 0);
        Mat Source_Mat = Engine.getInstance().getImage(Source);
        Core.randShuffle(Source_Mat);
        Engine.getInstance().allocImage(Source, Source_Mat);  
        return null;
    }

    @Override
    public String getName() {
        return "randShuffle";
    }

    @Override
    public String getMan() {
        return "shuffle pixels randomly in image.";
    }

}
