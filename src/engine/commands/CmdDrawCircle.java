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
 * @author Amr_Ayman
 */
public class CmdDrawCircle extends AbstractCommand{

    @Override
    protected Parameter[] getParamsOnce() {
    return new Parameter[] {
	  new Parameter("Image", Type.MAT_ID, 1, null, "Input image.", false, false),
          new Parameter("center x", Type.INT, 0, Integer.MAX_VALUE, "X for the center point", false, false),
          new Parameter("center y", Type.INT, 0, Integer.MAX_VALUE, "Y for the center point", false, false),
          new Parameter("radius", Type.INT, 1, Integer.MAX_VALUE, "radius of the circle to be drawn", false, false),
          new Parameter("Color R", Type.INT, 0,255, "Red value for the color", false, false),
          new Parameter("Color G", Type.INT, 0,255, "Green value for the color", false, false),
          new Parameter("Color B", Type.INT, 0,255, "Blue value for the color", false, false),
          new Parameter("thickness", Type.INT, 0, Integer.MAX_VALUE, "thicknes of the line to be drawn", false, false),
          };    
    }

    @Override
    protected Object executeSafe() {
        String Src=getArgImgId("Image",0);
        Mat image = Engine.getInstance().getImage(Src);
        double center_x = getArgInt("center x", 0);
        double center_y = getArgInt("center y", 0);
        int radius = getArgInt("radius", 0);
        double R = getArgInt("Color R", 0);
        double G = getArgInt("Color G", 0);
        double B = getArgInt("Color B", 0);
        int thickness = getArgInt("thickness",0);
        Core.circle(image, new Point(center_x,center_y), radius, new Scalar(B,G,R), thickness);
        return null;
    }

    @Override
    public String getName() {
    return "drawCircle";    
    }

    @Override
    public String getMan() {
    return "This function draws a circle around a point with a given radius, clolor and line thickness";    
    }
    
}
