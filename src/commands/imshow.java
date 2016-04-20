/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

/**
 *
 * @author Amr_Ayman
 */
public class imshow {
    private JLabel label;
    public void imshow(Mat mat)
    {
        buildFrame("frame",mat.cols(),mat.rows());
            BufferedImage image=Convert_To_Buffer(mat);
            DisplayImage(image);
    }
    private void buildFrame(String title,int cols,int rows)
    {
        JFrame frame=new JFrame(title);
        JPanel panel=new JPanel();
        label=new JLabel();
        frame.setSize(cols, rows);
        panel.setSize(cols, rows);
        label.setSize(cols,rows);
        panel.add(label);
        frame.add(panel);
        frame.setVisible(true);
    }
    private void DisplayImage(Image image)
    {
        label.setIcon(new ImageIcon(image));
    }
    private BufferedImage Convert_To_Buffer(Mat mat)
    {
        BufferedImage Final_Image = null;
        try {
            MatOfByte Byte_Mat = new MatOfByte();
            Highgui.imencode(".jpg", mat, Byte_Mat);
            byte[] Img = Byte_Mat.toArray();
            InputStream in = new ByteArrayInputStream(Img);
            Final_Image = ImageIO.read(in);
            
        } catch (IOException ex) {
            Logger.getLogger(imshow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Final_Image;
    }
}
