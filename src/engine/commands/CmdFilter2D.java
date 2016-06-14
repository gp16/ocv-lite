package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class CmdFilter2D extends AbstractCommand {
    
    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
            new Parameter("Source", Type.MAT_ID, 1, null, "input 8-bit image.", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null, "output image of"
                    + " the same size and the same number of channels as src.", false, false),
            new Parameter("ddepth",Type.INT, -1, Integer.MAX_VALUE, "the output"
                    + " image depth (-1 to use src.depth()).", false, false),
            new Parameter("kernel_size", Type.INT, 1, Integer.MAX_VALUE, "It is the"
                    + " kernel to be scanned through the image.", false, false)
        };
    }
    
    @Override
    protected Object executeSafe() {
        String source = getArgImgId("Source", 0);
        String dest = getArgImgId("Destination", 0);
        int ddepth = getArgInt("ddepth", 0);
        int kernel_size = getArgInt("kernel_size", 0);
        
        
        Mat src = Engine.getInstance().getImage(source);
        Mat dst = new Mat(src.rows(),src.cols(),src.type());
        
        Mat kernel = new Mat(kernel_size,kernel_size, CvType.CV_32F){
            {
                put(0,0,0);
                put(0,1,0);
                put(0,2,0);
                
                put(1,0,0);
                put(1,1,1);
                put(1,2,0);
                
                put(2,0,0);
                put(2,1,0);
                put(2,2,0);
            }
        };
        
        Imgproc.filter2D(src, dst, ddepth, kernel);
        Engine.getInstance().allocImage(dest, dst);
        
        return null;
    }
    
    @Override
    public String getName() {
        return "filter2D";
    }
    
    @Override
    public String getMan() {
        return "Convolves an image with the kernel.";
    }
    
}
