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
import java.awt.image.BufferedImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author Amr_Ayman
 */
public class CmdImgCapture extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("name", Type.STR, 1, 0, "image name to save in memory", false, false)};
    }

    @Override
    protected Object executeSafe() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String name=stringArgs.get("name").get(0);
        VideoCapture camera=new VideoCapture(0);
        if (!camera.isOpened()) 
        {
            System.out.println("error: camera is not opened");
        } 
        else 
        {
            Mat frame = new Mat();
            while (true) 
            {
                if (camera.read(frame)) 
                {                    
                    byte[] BufferdFrame = new byte[(int) (frame.total() * frame.channels()*frame.elemSize())];
                    frame.get(0, 0, BufferdFrame);
                    BufferedImage image=new BufferedImage(frame.cols(), frame.rows(), BufferedImage.TYPE_3BYTE_BGR);
                    image.getRaster().setDataElements(0, 0, frame.cols(),frame.rows(),BufferdFrame);
                    Engine.getInstance().allocImage(name, image);
                    break;                   
                }
            }
        camera.release();        
        }
        return null;
    }

    @Override
    public String getName() {
        return "capture";
    }

    @Override
    public String getMan() {
        return "capture an image from the camera";
    }
    
}