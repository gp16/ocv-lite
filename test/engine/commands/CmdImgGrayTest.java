/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.commands;

import engine.Engine;
import engine.ICommand;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Amr_Ayman
 */
    @RunWith(Parameterized.class)
public class CmdImgGrayTest 
{
        protected String RGBname;
        protected File file;
        protected String GRAYname;
        protected ICommand cmdImgGray;
        protected ICommand cmdImgLoad;
        protected CmdImgGray instance;
    
    public CmdImgGrayTest(String GRAYname, String RGBname) 
    {
        this.GRAYname = GRAYname;
        this.RGBname = RGBname;
    }    
    @Before
    public void setUp() {
        instance = new CmdImgGray();
        cmdImgLoad = Engine.getInstance().getCommand("load");
        cmdImgLoad.execute("test\\Imgs\\mountain.jpg","RGB"); 
        cmdImgGray = Engine.getInstance().getCommand("toGray");     
        cmdImgGray.execute(RGBname,GRAYname);
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
        System.out.println("toGray");
        BufferedImage RGBimage = Engine.getInstance().getImage(RGBname);
        byte [] RGBimageBytes = ((DataBufferByte) RGBimage.getData().getDataBuffer()).getData();
        BufferedImage GRAYimage = Engine.getInstance().getImage(GRAYname);
        byte [] GRAYimageBytes = ((DataBufferByte) GRAYimage.getData().getDataBuffer()).getData();
        assertFalse(Arrays.equals(RGBimageBytes, GRAYimageBytes));
        ColorSpace color=ColorSpace.getInstance(ColorSpace.CS_GRAY);
        assertTrue(GRAYimage.getColorModel().getColorSpace().equals(color));   
    }
    /**
     * Test of getName method, of class CmdImgGray.
     */
    @Test
    public void testGetName() 
    {
        System.out.println("getName");
        String expResult = "toGray";
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
}
