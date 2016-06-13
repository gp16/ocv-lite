package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class CmdBoxFilter extends AbstractCommand {
    
    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("src", Type.MAT_ID, 1, null, "input image.", false, false),
            new Parameter("dst",Type.MAT_ID, 1, null, "output image", false, false),
            new Parameter("ddepth",Type.INT, 1, Integer.MAX_VALUE, "the output"
                    + " image depth (-1 to use src.depth()).", false, false),
            new Parameter("size_x", Type.INT, 1, Integer.MAX_VALUE, "Kernel "
                    + "size_x must be odd and bigger than 0", false,false),
            new Parameter("size_y", Type.INT, 1, Integer.MAX_VALUE, "Kernel "
                    + "size_y must be odd and bigger than 0", false ,false)
        };
    }
    
    @Override
    protected Object executeSafe() {
        String Src = getArgImgId("src", 0);
        String dest = getArgImgId("dst", 0);
        int ddepth = getArgInt("ddepth",0);
        int size_x = getArgInt("size_x", 0);
        int size_y = getArgInt("size_y", 0);
        
        Mat src = Engine.getInstance().getImage(Src);
        Mat dst = new Mat();
        
        
        Imgproc.boxFilter(src, dst, ddepth, new Size(size_x, size_y));
        
        Engine.getInstance().allocImage(dest, dst);
        
        return null;
    }
    
    @Override
    public String getName() {
        return "Boxfilter";
    }
    
    @Override
    public String getMan() {
        return "Transforms an image to compensate for lens distortion.";
    }
    
}
