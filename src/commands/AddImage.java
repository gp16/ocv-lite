/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import engine.Engine;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

/**
 *
 * @author Amr_Ayman
 */
public class AddImage {
    public void AddImage(String imageName,Mat mat)
    {
        BufferedImage image = Convert_To_Buffer(mat);
        Engine.getInstance().allocImage(imageName, image);
    }
        /**
     * Convert a mat to buffered image
     */
    private BufferedImage Convert_To_Buffer(Mat image)
    {
        BufferedImage Final_Image = null;
        try {
            MatOfByte Byte_Mat = new MatOfByte();
            Highgui.imencode(".jpg", image, Byte_Mat);
            byte[] Img = Byte_Mat.toArray();
            InputStream in = new ByteArrayInputStream(Img);
            Final_Image = ImageIO.read(in);
            
        } catch (IOException ex) {
            Logger.getLogger(AddImage.class.getName()).log(Level.SEVERE, null, ex);
        }
return Final_Image;
    }
}
