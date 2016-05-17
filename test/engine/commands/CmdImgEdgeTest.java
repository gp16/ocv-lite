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
public class CmdImgEdgeTest {
    
        protected String orginal;
        protected File file;
        protected String edge;
        protected ICommand cmdImgEdge;
        protected ICommand cmdImgLoad;
        protected CmdImgEdge instance;
        
    public CmdImgEdgeTest(String orginalname, String edgename)  {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.orginal = orginalname;
        this.edge = edgename;
    }
    @Before
    public void setUp() {
        instance = new CmdImgEdge();
        cmdImgLoad = Engine.getInstance().getCommand("load");
        cmdImgLoad.execute(
                new Argument(Type.SYS_PATH,"test/Imgs/mountain.jpg"),
                new Argument(Type.MAT_ID,"orginal")
        );
        cmdImgLoad.execute(
                new Argument(Type.SYS_PATH,"test/Imgs/mountain_edged.jpg"),
                new Argument(Type.MAT_ID,"edge")
        ); 
        cmdImgEdge = Engine.getInstance().getCommand("edge");     
        cmdImgEdge.execute(new Argument(Type.MAT_ID,orginal),
                new Argument(Type.MAT_ID,edge));
    } 
    
    @Parameterized.Parameters
    public static Collection parameters() 
    {
        return Arrays.asList(new Object[][]{{"orginal","edged"}});
    }
    /**
     * Test class CmdImgEdge
     * 
     */
    @Test
    public void TestEdge() 
    {
        System.out.println("edge");
        Mat expResult = Engine.getInstance().getImage("edge");
        Mat result = Engine.getInstance().getImage(edge);
        assertEquals(Highgui.imencode(".jpg", expResult, new MatOfByte()),
                Highgui.imencode(".jpg", result, new MatOfByte()));   
    }
    /**
     * Test of getName method, of class CmdImgEdge.
     */
    @Test
    public void testGetName() 
    {
        System.out.println("getName");
        String expResult = "edge";
        String result = instance.getName();
        assertEquals(expResult, result);
    }
    /**
     * Test of getMan method, of class CmdImgEdge.
     */
    @Test
    public void testGetMan() 
    {
        System.out.println("getMan");
        String expResult = "Convert Image To edge";
        String result = instance.getMan();
        assertEquals(expResult, result);
    }
}
