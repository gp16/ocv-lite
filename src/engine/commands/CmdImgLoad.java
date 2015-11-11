package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class CmdImgLoad extends AbstractCommand  {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
	  new Parameter("path", Type.STR, 1, 0, "path of image to be loaded", false, false),
          new Parameter("imageName", Type.STR, 1, 0, "image name in memory", false, false)
	  };
    }

    @Override
    protected Object executeSafe() {
    try {             
        String path = stringArgs.get("path").get(0);
        String name = stringArgs.get("imageName").get(0);
        File file = new File(path);
        Image image = ImageIO.read(file);
        Engine.getInstance().allocImage(name, (BufferedImage) image);
        }
        catch (IOException e) {
            Logger.getLogger(CmdImgLoad.class.getName()).log(Level.SEVERE, null, e);
        }
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
