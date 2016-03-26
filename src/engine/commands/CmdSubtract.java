package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 *
 * @author Helmy
 */
public class CmdSubtract extends AbstractCommand
{

    @Override
    protected Parameter[] getParamsOnce()
    {
	return new Parameter[]{
	    new Parameter("src1", Type.MAT_ID, 1, null, "Source matrix 1", false, false),
	    new Parameter("src2", Type.MAT_ID, 1, null, "Source matrix 2", false, false),
	    new Parameter("destination", Type.MAT_ID, 1, null, "Destination matrix", false, false),
	};
    }

    @Override
    protected Object executeSafe()
    {
	Mat src1 = Engine.getInstance().getImage(getArgImgId("src1", 0));
	Mat src2 = Engine.getInstance().getImage(getArgImgId("src2", 0));
	Mat destination = new Mat();
	
	Core.subtract(src1, src2, destination);
	
	Engine.getInstance().allocImage(getArgImgId("destination", 0), destination);

	return null;
    }

    @Override
    public String getName()
    {
	return "subtract";
    }

    @Override
    public String getMan()
    {
	return "subtracts two images.";
    }
    
}
