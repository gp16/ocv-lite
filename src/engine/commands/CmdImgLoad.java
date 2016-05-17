package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

/**
* This command loads an image from the hard disk and name it in the memory 
* @author Abdelrahman Mohsen
* @version 1.0 
* @since 2015
*/

public class CmdImgLoad extends AbstractCommand  {
    
    @Override
    /**
    * @return returns array of 2 objects of type Parameter. 
    * first is a path of an image and second is an image name.
    */
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
	  new Parameter("path", Type.SYS_PATH, 1, null, "path of image to be loaded", false, false),
          new Parameter("imageName", Type.MAT_ID, 1, null, "image name in memory", false, false)
	  };
    }
    
    @Override
    protected Object executeSafe() 
    {   
        String path = getArgPath("path", 0);
        String name = getArgImgId("imageName",0);
        Mat image=Highgui.imread(path);
        Engine.getInstance().allocImage(name,image);
        return null;
    }
        
  

    @Override
    public String getName() {
        return("load");
    }

    @Override
    public String getMan() {
        return("load an image from hard disk");
    }
    
}
