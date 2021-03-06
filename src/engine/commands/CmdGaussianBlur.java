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
public class CmdGaussianBlur extends AbstractCommand{

    @Override
    protected Parameter[] getParamsOnce() {
      return new Parameter[]{
            new Parameter("Source", Type.MAT_ID, 1, null, "image name to get from memory", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null, "image name to save into memory", false, false),
            new Parameter("size_x", Type.INT, 1, Integer.MAX_VALUE, "Kernel size_x must be odd and bigger than 0", false,false),
            new Parameter("size_y", Type.INT, 1, Integer.MAX_VALUE, "Kernel size_y must be odd and bigger than 0", false ,false)
        };
    }

    @Override
    protected Object executeSafe() 
    {
        String Source = getArgImgId("Source", 0);
        String Destination = getArgImgId("Destination", 0);
        int size_x = getArgInt("size_x", 0);
        int size_y = getArgInt("size_y", 0);
        Mat Source_Mat = Engine.getInstance().getImage(Source);
        Mat Destination_Mat = new Mat();
        Imgproc.blur(Source_Mat, Destination_Mat, new Size(size_x, size_y));
        Engine.getInstance().allocImage(Destination, Destination_Mat);  
        return null;
    }

    @Override
    public String getName() {
    return "gaussianBlur";
    }

    @Override
    public String getMan() {
    return "Blurs an image using GaussianBlur";
    }
    
}
