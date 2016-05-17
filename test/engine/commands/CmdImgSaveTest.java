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
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
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
 * @author amrgamal
 */
@RunWith(Parameterized.class)
public class CmdImgSaveTest {
    protected String path;
    protected String imageName;
    protected ICommand cmdImgSave;
    protected ICommand cmdImgLoad;
    protected CmdImgSave instance;
    
    public CmdImgSaveTest(String imageName, String path) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.path = path;
        this.imageName = imageName;
    }
    
    @Before
    public void setUp() {
        instance = new CmdImgSave();
        cmdImgLoad = Engine.getInstance().getCommand("load");
        cmdImgLoad.execute(
                new Argument(Type.SYS_PATH,"test/Imgs/mountain.jpg"),
                new Argument(Type.MAT_ID,imageName));
        
        cmdImgSave = Engine.getInstance().getCommand("save");       
        cmdImgSave.execute(
                new Argument(Type.MAT_ID, imageName),
                new Argument(Type.SYS_PATH, path)
                
        );
    }
    
    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][]{{"mountain","test/imgs/saved.jpg"}});
    }

    /**
     * Test of class CmdImgSave.
     * @throws java.io.IOException    */
    
    @Test
    public void TestSave() throws IOException {
        System.out.println("save");
        Mat result = Engine.getInstance().getImage(imageName);
        Mat ExpResult=Highgui.imread(path);      
        assertSame(Highgui.imencode(".jpg", ExpResult, new MatOfByte()),
                Highgui.imencode(".jpg", result, new MatOfByte()));
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