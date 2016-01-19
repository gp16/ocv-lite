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
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

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
         new Parameter("ImageName", Type.MAT_ID, 1, null, "Image Name", false, false),
         new Parameter("Path", Type.SYS_PATH, 1, null, "path to save image in", false, false)
                           };
   }
    
    @Override
    protected Object executeSafe() 
    {
            String path = getArgPath("Path", 0);
            String ImageName = getArgImgId("ImageName", 0);  
            Mat image=Engine.getInstance().getImage(ImageName);
            Highgui.imwrite(path, image);
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
