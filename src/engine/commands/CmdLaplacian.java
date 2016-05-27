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
public class CmdLaplacian extends AbstractCommand
{

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
	  new Parameter("src", Type.MAT_ID, 1, null, "grayscale input image.", false, false),
          new Parameter("dst", Type.MAT_ID, 1, null, "Output image.", false, false),
          new Parameter("ddepth", Type.INT, -1, Integer.MAX_VALUE, "resolution of the distination image,'-1' indicates that the depth is the same as the source.", false, false),
          };
    }

    @Override
    protected Object executeSafe() {
        String Src=getArgImgId("src",0);
        String Dst=getArgImgId("dst",0);
        int ddepth = getArgInt("ddepth", 0);
        Mat src = Engine.getInstance().getImage(Src);
        Mat dst=new Mat();
        Imgproc.Laplacian(src, dst, ddepth);
        Engine.getInstance().allocImage(Dst,dst);
     return null;
    }

    @Override
    public String getName() {
     return "laplacian";
    }

    @Override
    public String getMan() {
    return "Applies the Laplacian filter of an image";
    }
    
}
