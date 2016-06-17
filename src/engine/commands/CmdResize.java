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
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Amr_Ayman
 */
public class CmdResize extends AbstractCommand
{

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
	  new Parameter("src", Type.MAT_ID, 1, null, "input image.", false, false),
          new Parameter("dst",Type.MAT_ID, 1, null, "output image", false, false),
          new Parameter("dsize x",Type.INT, 1,Integer.MAX_VALUE , "Destination size x", false, false),
          new Parameter("dsize y",Type.INT, 1, Integer.MAX_VALUE, "Destination size y", false, false),
          };
    }

    @Override
    protected Object executeSafe() {
        String Src = getArgImgId("src", 0);
        String Dst = getArgImgId("dst", 0);
        Mat src = Engine.getInstance().getImage(Src);
        Mat dst = new Mat();
        double dsize_x = getArgInt("dsize x", 0);
        double dsize_y = getArgInt("dsize y", 0);
        Imgproc.resize(src, dst, new Size(dsize_x,dsize_y));
        Engine.getInstance().allocImage(Dst,dst);
        return null;
    }

    @Override
    public String getName() {
        return"resize";
    }

    @Override
    public String getMan() {
      return"resize an image.";  
    }
    
}
