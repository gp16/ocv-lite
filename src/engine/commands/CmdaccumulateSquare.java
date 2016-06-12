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
public class CmdaccumulateSquare extends AbstractCommand
{

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
	  new Parameter("Source Image", Type.MAT_ID, 1, null, "First input image.", false, false),
          new Parameter("Destination Image", Type.MAT_ID, 1, null, "Accumulator destination image", false, false),
        };
    }

    @Override
    protected Object executeSafe() {
        String Src=getArgImgId("Source Image",0);
        String Dst=getArgImgId("Destination Image",0);
        Mat src = Engine.getInstance().getImage(Src);
        Mat dst = Engine.getInstance().getImage(Dst);
        src.convertTo(src, CV_64F);
        dst.convertTo(dst, CV_64F);
        Imgproc.accumulateSquare(src, dst);
        Engine.getInstance().allocImage(Dst,dst);
    return null;
    }

    @Override
    public String getName() {
        return "accumulateSquare";
    }

    @Override
    public String getMan() {
        return "Adds the square of a source image";
    }
    
}
