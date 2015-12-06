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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 *This command captures an image and saves it in the memory.
 * @author AmrAyman
 * @version 1.0
 * @since 2015
 */
public class CmdImgCapture extends AbstractCommand {

    @Override
    /**
    * @return returns array of n objects of type Parameter. 
    * first is an image name that the image will be saved with in the memory.
    */
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("name", Type.IMG_ID, 1, null, "image name to save in memory", false, false)};
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
                    try {
                        MatOfByte bytemat = new MatOfByte();
                        Highgui.imencode(".jpg", frame, bytemat);
                        byte[] bytes = bytemat.toArray();
                        InputStream in = new ByteArrayInputStream(bytes);
                        BufferedImage image = ImageIO.read(in);                  
                        Engine.getInstance().allocImage(name, image);
                        break;                   
                    } catch (IOException ex) {
                        Logger.getLogger(CmdImgCapture.class.getName()).log(Level.SEVERE, null, ex);
                    }
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