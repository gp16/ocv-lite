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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
/**
 *This command gets an image from the memory, convert it to gray scale ,
 * then saves it in the memory.
 * @author AmrAyman
 * @version 1.0
 * @since 2015
 */
public class CmdImgGray extends AbstractCommand{
    
    @Override
    /**
    * @return returns array of n objects of type Parameter.
    * first is an image name from memory.
    * second is an image name that the gray image will be saved with 
    * in the memory.
    */
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("NameToGray", Type.STR, 1, 0, "image name to get from memory", false, false),
        new Parameter("NameToSave", Type.STR, 1, 0, "image name to get from memory", false, false)};
 }

    @Override
    protected Object executeSafe() {
        
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);                                
            String NameToGray=stringArgs.get("NameToGray").get(0);                       // Name of the image that will be converted to gray
            String NameToSave=stringArgs.get("NameToSave").get(0);                       // Name of the gray image that will be saved im memory
            BufferedImage memImg = Engine.getInstance().getImage(NameToGray);             //get the RGB image from memory
            byte[] memImgData = ((DataBufferByte) memImg.getRaster().getDataBuffer()).getData();//byte array containing the raster data of the RGB image
            Mat RGB_Mat=new Mat(memImg.getHeight(), memImg.getWidth(), CvType.CV_8UC3);        //Mat for RGB image of the size of the RGB image
            RGB_Mat.put(0, 0, memImgData);                                                         //put byte data in to the Mat
            Mat Gray_Mat = new Mat(memImg.getHeight(),memImg.getWidth(),CvType.CV_8UC1);       //Mat for Gray image of the size of RGB image
            Imgproc.cvtColor(RGB_Mat, Gray_Mat, Imgproc.COLOR_RGB2GRAY);                         //OpenCV function that converts from any color space to gray color space
            MatOfByte Byte_Mat = new MatOfByte();                                         //Byte Mat
            Highgui.imencode(".jpg", Gray_Mat, Byte_Mat);                                   // compresses an image (Mat) to jpg, and put that in byte Mat
            byte[] GrayImgData = Byte_Mat.toArray();                                            //convert bytemat to byte array
            InputStream in = new ByteArrayInputStream(GrayImgData);                            //read byte array as a stream
            BufferedImage GrayImg = ImageIO.read(in);                                     //read the stream as a buffered image
            Engine.getInstance().allocImage(NameToSave, GrayImg);                         //save gray image in the memory
            return null;
            } 
        catch (IOException ex) 
        {
            Logger.getLogger(CmdImgGray.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getName() {
        return "toGray";
    }

    @Override
    public String getMan() {
        return "Convert Image To Gray";
    }
    
}
