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
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Amr_Ayman
 */
public class CmdcopyMakeBorder extends AbstractCommand
{

    @Override
    protected Parameter[] getParamsOnce() {
    return new Parameter[] {
	  new Parameter("src", Type.MAT_ID, 1, null, "input image.", false, false),
          new Parameter("dst", Type.MAT_ID, 1, null, "outout image", false, false),
          new Parameter("top", Type.INT, 1, Integer.MAX_VALUE, "Number of pixels for the top border", false, false),
          new Parameter("bottom", Type.INT, 1, Integer.MAX_VALUE, "Number of pixels for the bottom border", false, false),
          new Parameter("left", Type.INT, 1,Integer.MAX_VALUE, "Number of pixels for the left border", false, false),
          new Parameter("right", Type.INT, 1, Integer.MAX_VALUE, "Number of pixels for the right border", false, false),
          new Parameter("borderType", Type.INT, 1, 8, "1->BORDER_CONSTANT,"
                  + "2->BORDER_DEFAULT,3->BORDER_ISOLATED,4->BORDER_REFLECT,"
                  + "5->BORDER_REFLECT101,6->BORDER_REPLICATE,"
                  + "7->BORDER_TRANSPARENT,8->BORDER_WRAP", false, false),
          new Parameter("Scalar B", Type.INT, 0, 255, "Blue color of the borders in case of BORDER_CONSTANT only", true, false),
          new Parameter("Scalar G", Type.INT, 0, 255, "Green color of the borders in case of BORDER_CONSTANT only", true, false),
          new Parameter("Scalar R", Type.INT, 0, 255, "Red color of the borders in case of BORDER_CONSTANT only", true, false)
	  };    
    }

    @Override
    protected Object executeSafe() {
        String Src=getArgImgId("src",0);
        String Dst=getArgImgId("dst",0);
        int top=getArgInt("top",0);
        int bottom=getArgInt("bottom",0);
        int left=getArgInt("left",0);
        int right=getArgInt("right",0);
        int border=getArgInt("borderType",0);
        int borderType=0;
        switch (border)
        {
            case 1:
                borderType=Imgproc.BORDER_CONSTANT;
                break;
            case 2:
                borderType=Imgproc.BORDER_DEFAULT;
                break;
            case 3:
                borderType=Imgproc.BORDER_ISOLATED;
                break;
            case 4:
                borderType=Imgproc.BORDER_REFLECT;
                break;
            case 5:
                borderType=Imgproc.BORDER_REFLECT101;
                break;
            case 6:
                borderType=Imgproc.BORDER_REPLICATE;
                break;
            case 7:
                borderType=Imgproc.BORDER_TRANSPARENT;
                break;
            case 8:
                borderType=Imgproc.BORDER_WRAP;
                break;
        }
        Mat src = Engine.getInstance().getImage(Src);
        Mat dst=new Mat();
        if(borderType==Imgproc.BORDER_CONSTANT)
        {
            int B=getArgInt("Scalar B",0);
            int G=getArgInt("Scalar G",0);
            int R=getArgInt("Scalar R",0);
          Imgproc.copyMakeBorder(src, dst, top, bottom, left, right, borderType, new Scalar(B,G,R));
        }
        else
        {
           Imgproc.copyMakeBorder(src, dst, top, bottom, left, right, borderType);
         
        }
        Engine.getInstance().allocImage(Dst, dst);
        return null;
    }

    @Override
    public String getName() {
        return "copyMakeBorder";
    }

    @Override
    public String getMan() {
        return "Forms a border around an image";
    }
    
}
