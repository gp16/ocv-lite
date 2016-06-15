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
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author AmrGamal
 */
public class CmdInRange extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("Source", Type.MAT_ID, 1, null, "image name to get from memory", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null, "image name to save into memory", false, false),
            new Parameter("HMin", Type.INT, 0, 179, "HMin", false, false),
            new Parameter("SMin", Type.INT, 0, 255, "SMin", false, false),
            new Parameter("VMin", Type.INT, 0, 255, "VMin", false, false),
            new Parameter("HMax", Type.INT, 0, 179, "HMax", false, false),
            new Parameter("SMax", Type.INT, 0, 255, "SMax", false, false),
            new Parameter("VMax", Type.INT, 0, 255, "VMax", false, false)

        };
    }

    @Override
    protected Object executeSafe() {
        String Source = getArgImgId("Source", 0);
        String Destination = getArgImgId("Destination", 0);
        Mat Source_Mat = Engine.getInstance().getImage(Source);
        Mat Destination_Mat = new Mat();
        int Hmin = getArgInt("HMin", 0);
        int Smin = getArgInt("SMin", 0);
        int Vmin = getArgInt("VMin", 0);
        int Hmax = getArgInt("HMax", 0);
        int Smax = getArgInt("SMax", 0);
        int Vmax = getArgInt("VMax", 0);
        Core.inRange(Source_Mat, new Scalar(Hmin, Smin, Vmin), new Scalar(Hmax, Smax, Vmax), Destination_Mat);
        Engine.getInstance().allocImage(Destination, Destination_Mat);
        return null;
    }

    @Override
    public String getName() {
        return "inRange";
    }

    @Override
    public String getMan() {
        return "Detect Color in Image";
    }

}
