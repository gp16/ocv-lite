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
import org.opencv.objdetect.CascadeClassifier;

/**
 *This command gets an image from the memory, detect face on it ,
 * then saves it in the memory.
 * @author AmrGamal
 * @version 1.0
 * @since 2015
 */
public class CmdFaceDetection extends AbstractCommand{

    @Override
    /**
    * @return returns array of n objects of type Parameter.
    * first is an image name from memory.
    * second is an image name that will be detected then saves it 
    * in the memory.
    */
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("Source", Type.MAT_ID, 1, null, "image name to get from memory", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null, "image name to save into memory", false, false),
            new Parameter("Classifier", Type.SYS_PATH, 1, null, "Name of the file from which the classifier is loaded.", false, false)	
	};
    }

    @Override
    protected Object executeSafe() {
        String Source = getArgImgId("Source", 0);
        String Destination = getArgImgId("Destination", 0);
	String classifier = getArgPath("Classifier", 0);
        CascadeClassifier faceDetector = new CascadeClassifier(classifier);
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
        return "face detection";
    }

    @Override
    public String getMan() {
        return "Detect faces in Image";
    }
    
}