/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.commands;

import engine.AbstractCommand;
import engine.Parameter;
import engine.Type;
import engine.Engine;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
/**
 *
 * @author AmrGamal
 */
public class CmdImgSave extends AbstractCommand{

    @Override
    protected Parameter[] getParamsOnce() 
    {
     return new Parameter[]{
         new Parameter("Path", Type.STR, 1, 0, "path to save image in", false, false),
         new Parameter("ImageName", Type.STR, 1, 0, "Image Name", false, false)
                           };
   }

    @Override
    protected Object executeSafe() 
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String path=stringArgs.get("Path").get(0);
        String ImageName=stringArgs.get("ImageName").get(0);
        BufferedImage Image=Engine.getInstance().getImage(ImageName);
        byte[] pixels = ((DataBufferByte) Image.getRaster().getDataBuffer()).getData();
        Mat image = new Mat(Image.getHeight(), Image.getWidth(), CvType.CV_8UC3);
        image.put(0, 0, pixels);
        Imgcodecs.imwrite(path,image );
        
        return null;
    }

    @Override
    public String getName() 
    {
        return "save";
    }

    @Override
    public String getMan() 
    {
        return "Save Image To HardDrive From memory";
    }
    
}
