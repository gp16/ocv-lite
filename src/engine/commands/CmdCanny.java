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
 *This command gets an image from the memory,convert it in canny detection ,
 * then saves it in the memory.
 * @author AmrGamal
 * @version 1.0
 * @since 2015
 */
public class CmdCanny extends AbstractCommand{

    @Override
    /**
    * @return returns array of n objects of type Parameter.
    * first is an image name from memory.
    * second is an image name that the canny detect image will be saved with 
    * in the memory.
    */
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("Source", Type.MAT_ID, 1, null, "image name to get from memory", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null, "image name to save into memory", false, false),
            new Parameter("Threshold1", Type.INT, 1, 255, "threshold value", false, false),
            new Parameter("Threshold2", Type.INT, 1, 255, "threshold value", false, false),
        };
    }

    @Override
    protected Object executeSafe() {
        String Source = getArgImgId("Source", 0);
        String Destination = getArgImgId("Destination", 0);
        int Threshold1 = getArgInt("Threshold1", 0);
        int Threshold2 = getArgInt("Threshold2", 0);
        Mat Source_Mat = Engine.getInstance().getImage(Source);
        Mat Destination_Mat = new Mat();
        Imgproc.Canny(Source_Mat, Destination_Mat, Threshold1, Threshold2);
        Engine.getInstance().allocImage(Destination, Destination_Mat);  
        return null;
    }

    @Override
    public String getName() {
        return "canny";
    }

    @Override
    public String getMan() {
        return "Canny detection in image";
    }

}
