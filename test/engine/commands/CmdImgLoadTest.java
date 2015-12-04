/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.commands;

import engine.Engine;
import engine.ICommand;
import engine.Parameter;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CmdImgLoadTest {
    protected String path;
    protected File file;
    protected String imageName;
    protected ICommand cmdImgLoad;
    protected CmdImgLoad instance;
    
    public CmdImgLoadTest(String path, String imageName) {
        this.path = path;
        this.imageName = imageName;
    }
    
    @Before
    public void setUp() {
        instance = new CmdImgLoad();
        cmdImgLoad = Engine.getInstance().getCommand("load");       
        cmdImgLoad.execute(path,imageName);
    }
    
    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][]{{"test\\imgs\\mountain.jpg","mountain"}});
    }

    /**
     * Test of executeSafe method, of class CmdImgLoad.
     */
    
    @Test
    public void TestLoad() throws IOException {
        System.out.println("load");
        BufferedImage image = ImageIO.read(new File(path));
        byte [] imageInByte = ((DataBufferByte) image.getData().getDataBuffer()).getData();
        BufferedImage loadedImage = Engine.getInstance().getImage(imageName);
        byte [] loadedImageInByte = ((DataBufferByte) loadedImage.getData().getDataBuffer()).getData();
        assertArrayEquals(imageInByte, loadedImageInByte);
    }

    /**
     * Test of getName method, of class CmdImgLoad.
     */
    
    @Test
    public void testGetName() {
        System.out.println("getName");
        CmdImgLoad instance = new CmdImgLoad();
        String expResult = "load";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMan method, of class CmdImgLoad.
     */
    
    @Test
    public void testGetMan() {
        System.out.println("getMan");
        CmdImgLoad instance = new CmdImgLoad();
        String expResult = "load an image from hard disk";
        String result = instance.getMan();
        assertEquals(expResult, result);
    }
}
