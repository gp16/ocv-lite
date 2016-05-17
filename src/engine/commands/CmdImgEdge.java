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
 *This command gets an image from the memory, convert it to edge detector ,
 * then saves it in the memory.
 * @author AmrGamal
 * @version 1.0
 * @since 2015
 */
public class CmdImgEdge extends AbstractCommand {

    @Override
    /**
    * @return returns array of n objects of type Parameter.
    * first is an image name from memory.
    * second is an image name that the edge image will be saved with 
    * in the memory.
    */
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("Source", Type.MAT_ID, 1, null, "image name to get from memory", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null, "image name to save into memory", false, false)};
    }

    @Override
    protected Object executeSafe() {
        String Source = getArgImgId("Source", 0);
        String Destination = getArgImgId("Destination", 0);
        Mat Source_Mat = Engine.getInstance().getImage(Source);
        Mat Destination_Mat = new Mat();
        Imgproc.cvtColor(Source_Mat, Destination_Mat, Imgproc.COLOR_RGB2GRAY);
        Imgproc.blur(Destination_Mat, Destination_Mat, new Size(3, 3));
        Imgproc.Canny(Destination_Mat, Destination_Mat, 10, 100, 3, false);
        Engine.getInstance().allocImage(Destination, Destination_Mat);
        return null;
    }

    @Override
    public String getName() {
        return "edge";
    }

    @Override
    public String getMan() {
        return "Convert Image To edge";
    }

}
