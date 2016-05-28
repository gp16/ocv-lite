package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Core;
import org.opencv.core.Mat;


public class CmdAddWeighted extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("source1", Type.MAT_ID, 1, null, 
                    "first input array.", false, false),
            new Parameter("alpha", Type.INT, 0, Integer.MAX_VALUE,
                    " weight of the first array elements.", false, false),
            new Parameter("source2", Type.MAT_ID, 1, null, 
                    "second input array of the same "
                            + "size and channel number as src1", false, false),
            new Parameter("beta", Type.INT, 0, Integer.MAX_VALUE,
                    "weight of the second array elements.", false, false),
            new Parameter("gamma", Type.INT, 0, Integer.MAX_VALUE,
                    "scalar added to each sum", false, false),
            new Parameter("destination", Type.MAT_ID, 1, null, 
                    "output array that has the same size and number of "
                            + "channels as the input arrays.", false, false)
            
            };
    }

    @Override
    protected Object executeSafe() {
        String Source_1 = getArgImgId("source1", 0);
        double alpha = getArgInt("alpha", 0);
        String Source_2 = getArgImgId("source2", 0);
        double beta = getArgInt("beta", 0);
        double gamma = getArgInt("gamma", 0);
        String dest = getArgImgId("destination", 0);
        
        Mat source_1 = Engine.getInstance().getImage(Source_1);
        Mat source_2 = Engine.getInstance().getImage(Source_2);
        Mat dst = new Mat();
        
        Core.addWeighted(source_1, alpha, source_2, beta, gamma, dst);
        Engine.getInstance().allocImage(dest, dst);
        return null;
    }

    @Override
    public String getName() {
        return "addWeighted";
    }

    @Override
    public String getMan() {
        return "Blend two images of the same size and type";
    }
    
}
