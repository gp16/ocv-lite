/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.commands;

import engine.Argument;
import engine.ICommand;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import engine.Engine;
import engine.Type;
import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 *
 * @author ElmohandHaroon
 */
@RunWith(Parameterized.class)
public class CmdImgFreeTest {

    protected String path;
    protected ICommand cmdImgLoad;
    protected String imageName;
    protected ICommand cmdImgFree;
    protected CmdImgFree instance;

    public CmdImgFreeTest(String path, String imageName) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.path = path;
        this.imageName = imageName;
    }

    @Before
    public void setUp() {
        instance = new CmdImgFree();
        cmdImgLoad = Engine.getInstance().getCommand("load");
        cmdImgLoad.execute(
                new Argument(Type.SYS_PATH, path),
                new Argument(Type.MAT_ID, imageName)
        );

        cmdImgFree = Engine.getInstance().getCommand("free");
        cmdImgFree.execute(new Argument(Type.MAT_ID, imageName));

    }

    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][]{{"test/imgs/mountain.jpg", "mountain"}});
    }

    /**
     * Test of executeSafe method, of class CmdImgFree.
     */
    @Test
    public void testExecuteSafe() 
    {
        System.out.println("free");
        Mat result = Engine.getInstance().getImage(imageName);
        assertNull(result);
    }

    /**
     * Test of getName method, of class CmdImgFree.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        instance = new CmdImgFree();
        String expResult = "free";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMan method, of class CmdImgFree.
     */
    @Test
    public void testGetMan() {
        System.out.println("getMan");
        instance = new CmdImgFree();
        String expResult = "Delete image from memory";
        String result = instance.getMan();
        assertEquals(expResult, result);
    }

}
