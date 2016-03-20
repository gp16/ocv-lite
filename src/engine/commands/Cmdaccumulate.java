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
import static org.opencv.core.CvType.CV_64F;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Amr_Ayman
 */
public class Cmdaccumulate extends AbstractCommand
{

    @Override
    protected Parameter[] getParamsOnce() 
    {
      return new Parameter[] 
      {
	  new Parameter("src", Type.MAT_ID, 1, null, "input image.", false, false),
          new Parameter("dst", Type.MAT_ID, 1, null, "Accumulator image with "
                  + "the same number of channels as the input image", false, false),
         // new Parameter("mask", Type.MAT_ID, 1, null, "mask", true, false)
      };   
    }

    @Override
    protected Object executeSafe() 
    {
        String Src=getArgImgId("src",0);
        String Dst=getArgImgId("dst",0);
        Mat src = Engine.getInstance().getImage(Src);
        Mat dst=Engine.getInstance().getImage(Dst);
        src.convertTo(src, CV_64F);
        dst.convertTo(dst, CV_64F);
        Imgproc.accumulate(src, dst);
        Engine.getInstance().allocImage(Dst, dst);
        return null;
    }

    @Override
    public String getName() {
    return "accumulate";
    }

    @Override
    public String getMan() {
    return "Adds an image to the accumulator";
    }
    
}
