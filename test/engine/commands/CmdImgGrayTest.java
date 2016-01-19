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
 * @author Amr_Ayman
 */
    @RunWith(Parameterized.class)
public class CmdImgGrayTest 
{
        protected String RGB;
        protected File file;
        protected String Result;
        protected ICommand cmdImgGray;
        protected ICommand cmdImgLoad;
        protected CmdImgGray instance;
    
    public CmdImgGrayTest(String GRAYname, String RGBname) 
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Result = GRAYname;
        RGB = RGBname;
    }    
    @Before
    public void setUp() {
        instance = new CmdImgGray();
        cmdImgLoad = Engine.getInstance().getCommand("load");
        cmdImgLoad.execute(
                new Argument(Type.SYS_PATH,"test/Imgs/mountain.jpg"),
                new Argument(Type.MAT_ID,"RGB")
        ); 
        cmdImgLoad.execute(
                new Argument(Type.SYS_PATH,"test/Imgs/MountainGrayed.jpg"),
                new Argument(Type.MAT_ID,"expResult")
        );
        cmdImgGray = Engine.getInstance().getCommand("gray");     
        cmdImgGray.execute(new Argument(Type.MAT_ID,RGB),
                new Argument(Type.MAT_ID,Result));
    } 
    
    @Parameterized.Parameters
    public static Collection parameters() 
    {
        return Arrays.asList(new Object[][]{{"GRAY","RGB"}});
    }
    /**
     * Test class CmdImgGray
     */
    @Test
    public void TestGray()
    {
        System.out.println("gray");
        Mat result = Engine.getInstance().getImage(Result);
        Mat expResult = Engine.getInstance().getImage("expResult");
        assertEquals(Highgui.imencode(".jpg", expResult, new MatOfByte()),
                Highgui.imencode(".jpg", result, new MatOfByte()));   
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
}
