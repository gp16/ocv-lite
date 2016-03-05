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

/**
 *
 * @author Amr_Ayman
 */
public class CmdEqualize extends AbstractCommand 
{

    @Override
    protected Parameter[] getParamsOnce() 
    {
        return new Parameter[]
        {
            new Parameter("src", Type.MAT_ID, 1, null, "image name to calculate the equalized histogram for", false, false),
            new Parameter("dst", Type.MAT_ID, 1, null, "image name for the equalized image", false, false)
        };
    }
/**
 *   this method executes the equalize Method on the inputted image
 *   and stores the result in memory
 */
    @Override
    protected Object executeSafe() 
    {
        String src=getArgImgId("src",0);
        String dst=getArgImgId("dst", 0);
        Mat Source = Engine.getInstance().getImage(src);
        Mat Destination =equalize(Source);
        Engine.getInstance().allocImage(dst, Destination);
        return null;
    }

    @Override
    public String getName() {
        return"equalize";
    }

    @Override
    public String getMan() {
        return"A method that improves the contrast in an image, "
                + "in order to stretch out the intensity range";
    }
/**
 * This Method improves the contrast of the input image,
 *   by applying the function equalizeHist on the qrayscale of that image.  
 */
    private Mat equalize(Mat src)
    {
        Mat dst=new Mat();
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);
        Imgproc.equalizeHist(dst, dst);
        return dst;
    }
}
