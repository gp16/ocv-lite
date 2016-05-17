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
import java.io.File;
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
 * @author AmrGamal
 */
    @RunWith(Parameterized.class)
public class CmdImgflipTest {
    
        protected String orginal;
        protected File file;
        protected String flip;
        protected ICommand cmdImgFlip;
        protected ICommand cmdImgLoad;
        protected CmdImgFlip instance;
    public CmdImgflipTest(String orginalname, String flipname)  {
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.orginal = orginalname;
        this.flip = flipname;
    }
    @Before
    public void setUp() {
        instance = new CmdImgFlip();
        cmdImgLoad = Engine.getInstance().getCommand("load");
        cmdImgLoad.execute(
                new Argument(Type.SYS_PATH,"test/Imgs/mountain.jpg"),
                new Argument(Type.MAT_ID,"orginal")
        );
        cmdImgLoad.execute(
                new Argument(Type.SYS_PATH,"test/Imgs/mountain_fliped.jpg"),
                new Argument(Type.MAT_ID,"flip")
        ); 
        cmdImgFlip = Engine.getInstance().getCommand("flip");     
        cmdImgFlip.execute(new Argument(Type.MAT_ID,orginal),
                new Argument(Type.MAT_ID,flip));
    } 
    
    @Parameterized.Parameters
    public static Collection parameters() 
    {
        return Arrays.asList(new Object[][]{{"orginal","fliped"}});
    }
    /**
     * Test class CmdImgFlip
     * 
     */
    @Test
    public void TestFlip() 
    {
        System.out.println("flip");
        Mat expResult = Engine.getInstance().getImage("flip");
        Mat result = Engine.getInstance().getImage(flip);
        assertEquals(Highgui.imencode(".jpg", expResult, new MatOfByte()),
                Highgui.imencode(".jpg", result, new MatOfByte()));   
    }
    /**
     * Test of getName method, of class CmdImgFlip.
     */
    @Test
    public void testGetName() 
    {
        System.out.println("getName");
        String expResult = "flip";
        String result = instance.getName();
        assertEquals(expResult, result);
    }
    /**
     * Test of getMan method, of class CmdImgFlip.
     */
    @Test
    public void testGetMan() 
    {
        System.out.println("getMan");
        String expResult = "Flip Image";
        String result = instance.getMan();
        assertEquals(expResult, result);
    }
}
