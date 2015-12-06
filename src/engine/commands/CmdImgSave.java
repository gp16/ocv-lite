/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.commands;

import engine.AbstractCommand;
import engine.Parameter;
import engine.Type;
import engine.Engine;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Core;

/**
 * saves the specified image to the given path.
 * @author amrgamal
 * @version 1.0
 * @since   2015
 */
public class CmdImgSave extends AbstractCommand{

    /**
    * @return array of 2 objects of type Parameter. 
    * First is a path of an image and second is an image ID.
    */
    
    
    @Override
    protected Parameter[] getParamsOnce() 
    {
     return new Parameter[]{
         new Parameter("Path", Type.SYS_PATH, 1, null, "path to save image in", false, false),
         new Parameter("ImageName", Type.IMG_ID, 1, null, "Image Name", false, false)
                           };
   }
    
    @Override
    protected Object executeSafe() {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            String path=stringArgs.get("Path").get(0);
            String ImageName=stringArgs.get("ImageName").get(0);
            BufferedImage Image=Engine.getInstance().getImage(ImageName);
            File output =new File(path);
            ImageIO.write(Image, "jpg", output);   
        } catch (IOException ex) {
            Logger.getLogger(CmdImgSave.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public String getName() 
    {
        return "save";
    }

    @Override
    public String getMan() 
    {
        return "Save Image To HardDrive From memory";
    }
    
}
