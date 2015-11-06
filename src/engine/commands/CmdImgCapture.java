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
import org.opencv.imgcodecs.Imgcodecs;
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
        
        if (!camera.isOpened()) {
            System.out.println("error");
        } else {
            Mat frame = new Mat();
            while (true) {
                if (camera.read(frame)) {
                    try {
                        byte[] BufferdFrame = new byte[(int) (frame.total() * frame.channels())];
                        frame.get(0, 0, BufferdFrame);
                        InputStream in = new ByteArrayInputStream(BufferdFrame);
                        BufferedImage outputImage = ImageIO.read(in);
                        Engine.getInstance().allocImage(name, outputImage);
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
