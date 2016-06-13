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
 * @author Elmohand_Haroon
 */
public class CmdMedianBlur extends AbstractCommand{

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
	  new Parameter("src", Type.MAT_ID, 1, null, "input image.", false, false),
          new Parameter("dst",Type.MAT_ID, 1, null, "output image", false, false),
          new Parameter("ksize",Type.INT, 3,Integer.MAX_VALUE , "size must be odd and greater than 1", false, false),
          };
    }

    @Override
    protected Object executeSafe() {
        String Src = getArgImgId("src", 0);
        String Dst = getArgImgId("dst", 0);
        Mat src = engine.Engine.getInstance().getImage(Src);
        Mat dst = new Mat();
        int ksize = getArgInt("ksize", 0);
        Imgproc.medianBlur(src, dst, ksize);
        Engine.getInstance().allocImage(Dst,dst);
        return null;
    }

    @Override
    public String getName() {
    return "medianBlur";
    }

    @Override
    public String getMan() {
    return "Blurs an image using the median filter.";
    }
    
}
