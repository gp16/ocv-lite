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

@RunWith(Parameterized.class)
public class CmdImgLoadTest {
    protected String path;
    protected String imageName;
    protected ICommand cmdImgLoad;
    protected CmdImgLoad instance;
    
    public CmdImgLoadTest(String path, String imageName) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.path = path;
        this.imageName = imageName;
    }
    
    @Before
    public void setUp() {
        instance = new CmdImgLoad();
        cmdImgLoad = Engine.getInstance().getCommand("load");       
        cmdImgLoad.execute(
                new Argument(Type.SYS_PATH, path),
                new Argument(Type.MAT_ID, imageName)
        );
    }
    
    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][]{{"test/imgs/mountain.jpg","mountain"}});
    }

    /**
     * Test of executeSafe method, of class CmdImgLoad.
     */
    
    @Test
    public void TestLoad() 
    {
        System.out.println("load");
        Mat ExpResult=Highgui.imread(path);
        Mat result=Engine.getInstance().getImage(imageName);
        
        assertSame(Highgui.imencode(".jpg", ExpResult, new MatOfByte()),
                Highgui.imencode(".jpg", result, new MatOfByte()));
    }

    /**
     * Test of getName method, of class CmdImgLoad.
     */
    
    @Test
    public void testGetName() {
        System.out.println("getName");
        instance = new CmdImgLoad();
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
        instance = new CmdImgLoad();
        String expResult = "load an image from hard disk";
        String result = instance.getMan();
        assertEquals(expResult, result);
    }
}
