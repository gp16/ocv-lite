/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.commands;

import engine.Argument;
import engine.Engine;
import engine.ICommand;
import engine.Type;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

/**
 *
 * @author Amr_Ayman
 */
    @RunWith(Parameterized.class)
public class CmdImgGrayTest 
{
        protected String RGB;
        protected File file;
        protected String GRAY;
        protected ICommand cmdImgGray;
        protected ICommand cmdImgLoad;
        protected CmdImgGray instance;
    
    public CmdImgGrayTest(String GRAYname, String RGBname) 
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.GRAY = GRAYname;
        this.RGB = RGBname;
    }    
    @Before
    public void setUp() {
        instance = new CmdImgGray();
        cmdImgLoad = Engine.getInstance().getCommand("load");
        cmdImgLoad.execute(
                new Argument(Type.SYS_PATH,"test\\Imgs\\mountain.jpg"),
                new Argument(Type.MAT_ID,"RGB")
        ); 
        cmdImgGray = Engine.getInstance().getCommand("gray");     
        cmdImgGray.execute(new Argument(Type.MAT_ID,RGB),
                new Argument(Type.MAT_ID,GRAY));
    } 
    
    @Parameterized.Parameters
    public static Collection parameters() 
    {
        return Arrays.asList(new Object[][]{{"GRAY","RGB"}});
    }
    /**
     * Test class CmdImgGray
     * @throws java.io.IOException
     */
    @Test
    public void TestGray() throws IOException 
    {
        System.out.println("gray");
        Mat result = Engine.getInstance().getImage(GRAY);
        ColorSpace color=ColorSpace.getInstance(ColorSpace.CS_GRAY);
        assertTrue(Convert_To_Buffer(result).getColorModel().getColorSpace().equals(color));   
    }
    /**
     * Test of getName method, of class CmdImgGray.
     */
    @Test
    public void testGetName() 
    {
        System.out.println("getName");
        String expResult = "gray";
        String result = instance.getName();
        assertEquals(expResult, result);
    }
    /**
     * Test of getMan method, of class CmdImgGray.
     */
    @Test
    public void testGetMan() 
    {
        System.out.println("getMan");
        String expResult = "Convert Image To Gray";
        String result = instance.getMan();
        assertEquals(expResult, result);
    }
    /**
     * Convert a mat to buffered image
     */
    private BufferedImage Convert_To_Buffer(Mat image) throws IOException
    {
        MatOfByte Byte_Mat = new MatOfByte();
        Highgui.imencode(".jpg", image, Byte_Mat);
        byte[] Img = Byte_Mat.toArray();
        InputStream in = new ByteArrayInputStream(Img);
        BufferedImage Final_Image = ImageIO.read(in);
        return Final_Image;
 
    }
}
