/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.commands;

import engine.Engine;
import engine.ICommand;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author amrgamal
 */
@RunWith(Parameterized.class)
public class CmdImgSaveTest {
    protected String path;
    protected File file;
    protected String imageName;
    protected ICommand cmdImgSave;
    protected ICommand cmdImgLoad;
    protected CmdImgSave instance;
    
    public CmdImgSaveTest(String path, String imageName) {
        this.path = path;
        this.imageName = imageName;
    }
    
    @Before
    public void setUp() {
        instance = new CmdImgSave();
        cmdImgLoad = Engine.getInstance().getCommand("load");
        cmdImgLoad.execute("test\\imgs\\mountain.jpg","mountain");
        cmdImgSave = Engine.getInstance().getCommand("save");       
        cmdImgSave.execute(path,imageName);
    }
    
    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][]{{"test\\imgs\\saved.jpg","mountain"}});
    }

    /**
     * Test of class CmdImgSave.
     * @throws java.io.IOException    */
    
    @Test
    public void TestSave() throws IOException {
        System.out.println("save");
        BufferedImage MemImage = Engine.getInstance().getImage(imageName);
        byte [] MemBytes = ((DataBufferByte) MemImage.getData().getDataBuffer()).getData();
        BufferedImage SavedImage = ImageIO.read(new File(path));
        byte [] SavedBytes = ((DataBufferByte) SavedImage.getData().getDataBuffer()).getData();        
        assertArrayEquals(MemBytes, SavedBytes);
    }  

    /**
     * Test of getName method, of class CmdImgSave.
     */
    
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "save";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMan method, of class CmdImgSave.
     */
    
    @Test
    public void testGetMan() {
        System.out.println("getMan");
        String expResult = "Save Image To HardDrive From memory";
        String result = instance.getMan();
        assertEquals(expResult, result);
    }
}