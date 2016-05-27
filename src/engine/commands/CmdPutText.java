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
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

/**
 *
 * @author AmrGamal
 */
public class CmdPutText extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("source", Type.MAT_ID, 1, null, "source image", false, false),
            new Parameter("destination", Type.MAT_ID, 1, null, "destination image", false, false),
            new Parameter("Text", Type.STR, 1, null, "Text to be written on the image", false, false),
            new Parameter("org_x", Type.INT, 0, Integer.MAX_VALUE, "x value of the point that the text will start from", false, false),
            new Parameter("org_y", Type.INT, 0, Integer.MAX_VALUE, "y value of the point that the text will start from", false, false),
            new Parameter("Color_B", Type.INT, 0, 255, "Blue color values of the text", false, false),
            new Parameter("Color_G", Type.INT, 0, 255, "Green color values of the text", false, false),
            new Parameter("Color_R", Type.INT, 0, 255, "Red color values of the text", false, false),
            new Parameter("Font Scale", Type.INT, 0, Integer.MAX_VALUE, "scale that the text size will be", false, false),
            new Parameter("Font Face", Type.INT, 1, 8, "Font face/style that the text will be written by. "
                    + "1->FONT_HERSHEY_PLAIN,2->FONT_HERSHEY_DUPLEX,3->FONT_ITALIC"
                    + "4->FONT_HERSHEY_COMPLEX,5->FONT_HERSHEY_TRIPLEX,6->FONT_HERSHEY_COMPLEX_SMALL,"
                    + "7->FONT_HERSHEY_SCRIPT_SIMPLEX,8->FONT_HERSHEY_SCRIPT_COMPLEX", false, false),
            new Parameter("thickness", Type.INT, 1, Integer.MAX_VALUE, "thickness of the text to be put on the image", false, false)

        };
    }

    @Override
    protected Object executeSafe() {
        String Src = getArgImgId("source", 0);
        String Dst = getArgImgId("destination", 0);
        Mat src = Engine.getInstance().getImage(Src);
        Mat dst=new Mat(src.size(),src.type());
        src.copyTo(dst);
        String text = getArgString("Text", 0);
        double org_x=getArgInt("org_x", 0);
        double org_y=getArgInt("org_y", 0);
        double color_R = getArgInt("Color_R", 0);
        double color_G = getArgInt("Color_G", 0);
        double color_B = getArgInt("Color_B", 0);
        int FontFace = getArgInt("Font Face", 0);
        int thickness = getArgInt("thickness", 0);
        double FontScale = getArgInt("Font Scale", 0);
        int face = 0;
        switch(FontFace)
        {
            case 1:
                face = Core.FONT_HERSHEY_PLAIN;
                break;
            case 2:
                face = Core.FONT_HERSHEY_DUPLEX;
                break;
            case 3:
                face = Core.FONT_ITALIC;
                break;
            case 4:
                face = Core.FONT_HERSHEY_COMPLEX;
                break;
            case 5:
                face = Core.FONT_HERSHEY_TRIPLEX;
                break;
            case 6:
                face = Core.FONT_HERSHEY_COMPLEX_SMALL;
                break;
            case 7:
                face = Core.FONT_HERSHEY_SCRIPT_SIMPLEX;
                break;
            case 8:
                face = Core.FONT_HERSHEY_SCRIPT_COMPLEX;
                break;
                
        }
        Core.putText(dst, text, new Point(org_x,org_y), face, FontScale, new Scalar(color_B,color_G,color_R), thickness);
        Engine.getInstance().allocImage(Dst, dst);
        return null;
    }

    @Override
    public String getName() {
        return "PutText";
    }

    @Override
    public String getMan() {
        return "draw Text on an inputted image";
    }

}
