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
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author AmrGamal
 */
public class CmdImgDetect extends AbstractCommand{

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("Source", Type.MAT_ID, 1, null, "image name to get from memory", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null, "image name to save into memory", false, false)};
    }

    @Override
    protected Object executeSafe() {
        String Source = getArgImgId("Source", 0);
        String Destination = getArgImgId("Destination", 0);
        CascadeClassifier faceDetector = new CascadeClassifier("C:/opencv/sources/data/lbpcascades/lbpcascade_frontalface.xml");
        Mat Source_Mat = Engine.getInstance().getImage(Source);
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(Source_Mat, faceDetections);
        for (Rect rect : faceDetections.toArray()) {
        Core.rectangle(Source_Mat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
            }
        Engine.getInstance().allocImage(Destination, Source_Mat);
        return true;
    }

    @Override
    public String getName() {
        return "detect";
    }

    @Override
    public String getMan() {
        return "Detect Image";
    }
    
}
