/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.commands;

import engine.Engine;
import engine.ICommand;
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
public class CmdImgCaptureTest {    
    protected String imageName;
    protected ICommand cmdImgCapture;
    protected CmdImgCapture instance;
    
    public CmdImgCaptureTest(String imageName) 
    {
        this.imageName=imageName;
    }
    @Parameterized.Parameters
   public static Collection parameters()
   {
       return Arrays.asList(new Object[][]{{"pic"}});
   }
    
    
    @Before
    public void setUp() 
    {
        instance = new CmdImgCapture();
        cmdImgCapture= Engine.getInstance().getCommand("capture");       
        cmdImgCapture.execute(imageName);
    }
    @Test
   public void TestCapture()
   {
       System.out.println("Capture");
       Object image=Engine.getInstance().getImage(imageName);
       assertNotNull(image);
   }


    /**
     * Test of getName method, of class CmdImgCapture.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "capture";
        String result = instance.getName();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getMan method, of class CmdImgCapture.
     */
    @Test
    public void testGetMan() {
        System.out.println("getMan");
        String expResult = "capture an image from the camera";
        String result = instance.getMan();
        assertEquals(expResult, result);
        
    }
    
}
