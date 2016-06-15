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
public class CmdDrawHoughCircles extends AbstractCommand{
    @Override
    protected Parameter[] getParamsOnce() {
    return new Parameter[] {
	  new Parameter("Image", Type.MAT_ID, 1, null, "Input image.", false, false),
          new Parameter("Circles", Type.MAT_ID, 0, null, "result from the houghCircle command", false, false),
          new Parameter("Color R", Type.INT, 0,255, "Red value for the color", false, false),
          new Parameter("Color G", Type.INT, 0,255, "Green value for the color", false, false),
          new Parameter("Color B", Type.INT, 0,255, "Blue value for the color", false, false),
          new Parameter("thickness", Type.INT, 0, Integer.MAX_VALUE, "thicknes of the line to be drawn", false, false),
          };    
    }

    @Override
    protected Object executeSafe() {
        String Src=getArgImgId("Image",0);
        String Circles=getArgImgId("Circles",0);
        Mat image = Engine.getInstance().getImage(Src);
        Mat circles = (Mat) Engine.getInstance().getStruct(Circles);
        double R = getArgInt("Color R", 0);
        double G = getArgInt("Color G", 0);
        double B = getArgInt("Color B", 0);
        int thickness = getArgInt("thickness",0);
        double Center_X = 0;
        double Center_Y = 0;
        int radius = 0;
        for(int i =0;i<circles.rows();i++)
        {
            double[] data = circles.get(i, 0);
            Center_X = data[0];
           Center_Y = data[1];
           radius = (int) data[2];
        }
        Core.circle(image, new Point(Center_X,Center_Y), radius, new Scalar(B,G,R), thickness);
        return null;
    }

    @Override
    public String getName() {
    return "drawCircles";    
    }

    @Override
    public String getMan() {
    return "This function draws a circle around a point and radius generated from circles mat, and clolor and line thickness";    
    }
}
