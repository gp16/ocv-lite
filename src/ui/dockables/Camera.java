/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dockables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import ui.api.Dockable;

/**
 *
 * @author Amr_Ayman
 */
public class Camera extends JPanel implements Dockable
{
    private final JPanel VideoView = new JPanel(new BorderLayout());
    private final JTextField AddressField = new JTextField();
    private final JTextField MatName = new JTextField();
    private final JButton AddressButton = new JButton("Set Address");
    private final VideoCapture vc =new VideoCapture();
    private BufferedImage video;
    private ImageIcon imageIcon;
    private int scaledImageWidth = 0;
    private int scaledImageHeight = 0;
    private final Thread runnableThread = new Thread(new Runnable() {
        @Override
        public void run() {
            OpenCamera(Integer.parseInt(AddressField.getText()));
        }
    });
    
/**
 *
 * Constructor to start the Camera UI.
 */
    public Camera()
    {
        BuildUI();
        AddressButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                runnableThread.start();
            }
        });
    }
    
/**
 *
 * this function is responsible for building the interface for the Camera UI.
 */
    private void BuildUI()
    {
        AddressField.setPreferredSize(new Dimension(150, 20));
        PromptSupport.setPrompt("Camera Address", AddressField);
        AddressField.setToolTipText("Usually the default camera address is '0'");
        MatName.setPreferredSize(new Dimension(150, 20));
        PromptSupport.setPrompt("name of this video", MatName);
        AddressButton.setPreferredSize(new Dimension(100,20));
        add(VideoView,CENTER_ALIGNMENT);
    }
    
/**
 *
 * This function is responsible for opening the camera using the camera address,
 * and starting the capturing process.
 */
    private void OpenCamera(int address)
    {
        vc.open(address);
        VideoCapture();
    }
    
/**
 *
 * This function is responsible for capturing live video feed from the camera,
 * and applying on them functions from both terminal and command UIs.
 */
    private void VideoCapture()
    {
        Mat VideoMat = new Mat();
        while(true)
        {
            vc.read(VideoMat);
            // should execute history/list of commands done on this image
            executeVideoCommands(VideoMat);
            imageIcon = new ImageIcon(MatToImageConverter(VideoMat));
            if(!isDisplayable())
            {
                break;
            }
            repaint();
        }
        if(getParent() == null)
        {
            vc.release();
        }
    }
    
/**
 *
 * This function will execute commands from both terminal and command UIs 
 * on the live video stream.
 */
    private void executeVideoCommands(Mat vm)
    {
    
    }
    
/**
 *
 * This function paints/draws the live video feed on the UI.
 */
        @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        if(imageIcon == null)
            return;
        
        BufferedImage image = (BufferedImage)imageIcon.getImage();
        
        if(image.getWidth() <= getWidth() && image.getHeight() <= getHeight()) {
            scaledImageWidth = image.getWidth();
            scaledImageHeight = image.getHeight();
        }
        else {
            double hscale = (double)getWidth() / image.getWidth();
            double wscale = (double)getHeight() / image.getHeight();

            if(hscale < wscale) {
                scaledImageWidth = getWidth();
                scaledImageHeight = (int) (hscale*image.getHeight());                
            }
            else if(wscale <= hscale) {
                scaledImageWidth = (int) (wscale*image.getWidth());
                scaledImageHeight = getHeight();
            }
        }
        g.drawImage(image, 0, 0, scaledImageWidth, scaledImageHeight, VideoView);
    }
    
/**
 *
 * This function converts OpenCV Mat to BufferedImage.
 */
    private BufferedImage MatToImageConverter(Mat mat)
    {
        MatOfByte Byte_Mat;
        BufferedImage Final_Image = null;
        try 
        {
            Byte_Mat = new MatOfByte();
            Highgui.imencode(".png", mat, Byte_Mat);
            byte[] Img = Byte_Mat.toArray();
            InputStream in = new ByteArrayInputStream(Img);
            Final_Image = ImageIO.read(in);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Final_Image;
    }
    
    @Override
    public Component[] getNavigationComponents() {
    return new Component[]{
      AddressField,MatName,AddressButton,  
    };    
    }

    @Override
    public JPanel getDockablePanel() {
    return this;    
    }
    
}
