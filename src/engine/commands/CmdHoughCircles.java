/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.*;

/**
 *
 * @author Amr_Ayman
 * finds circles in a gray scale image, outputs a mat containing 3 values x, y, radius.
 */
public class CmdHoughCircles extends AbstractCommand
{

    @Override
    protected Parameter[] getParamsOnce() {
       return new Parameter[] {
	  new Parameter("src", Type.MAT_ID, 1, null, "grayscale input image.", false, false),
          new Parameter("circles", Type.MAT_ID, 1, null, "Output vector of found circles. "
                  + "Each vector is encoded as a 3-element floating-point vector (x, y, radius).", false, false),
          new Parameter("method", Type.INT, 1, Integer.MAX_VALUE, "Detection method to use. "
                  + "Currently, the only implemented method is CV_HOUGH_GRADIENT (1)", false, false),
          new Parameter("dp", Type.INT, 1, 2, "Inverse ratio of the accumulator resolution to the image resolution."
                  + " For example, if dp=1, the accumulator has the same resolution as the input image. "
                  + "If dp=2, the accumulator has half as big width and height", false, false),
          new Parameter("minDist", Type.INT, 1,Integer.MAX_VALUE, "Minimum distance between the centers of the detected circles", false, false),
          new Parameter("param1", Type.INT, 1, Integer.MAX_VALUE, "First method-specific parameter."
                  + " In case of CV_HOUGH_GRADIENT, it is the higher threshold of the two passed to the \"Canny\" edge detector", false, false),
          new Parameter("param2", Type.INT, 1, Integer.MAX_VALUE, "Second method-specific parameter. "
                  + "In case of CV_HOUGH_GRADIENT, it is the accumulator threshold for the circle centers at the detection stage", false, false),
          new Parameter("minRadius", Type.INT, 0, Integer.MAX_VALUE, "Minimum circle radius", false, false),
          new Parameter("maxRadius", Type.INT, 0, Integer.MAX_VALUE, "Maximum circle radius", false, false)
	  };}

    @Override
    protected Object executeSafe() 
    {
        
        String Src=getArgImgId("src",0);
        String Circles=getArgImgId("circles",0);
        int Method=getArgInt("method", 0);
        Mat src = Engine.getInstance().getImage(Src);
        Mat circles=new Mat();
        int method = 0;
        if(Method==1)
        {
            method=CV_HOUGH_GRADIENT;
        }
        double dp=getArgInt("dp", 0);
        double minDist=getArgInt("minDist", 0);
        double param1=getArgInt("param1", 0);
        double param2=getArgInt("param2", 0);
        int minRadius=getArgInt("minRadius", 0);
        int maxRadius=getArgInt("maxRadius", 0);
        Imgproc.HoughCircles(src, circles, method, dp, minDist, param1, param2, minRadius, maxRadius);
        Engine.getInstance().allocImage(Circles, circles);
        return null;
    }

    @Override
    public String getName() {
    return "HoughCircles";
    }

    @Override
    public String getMan() {
        return "Finds circles in a grayscale image using the Hough transform.";
    }
    
}
