package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;


public class CmdImgLoad extends AbstractCommand  {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
	  new Parameter("path", Type.STR, 1, 0, "path of image to be loaded", false, false),
	  };
    }

    @Override
    protected Object executeSafe() {
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
         String imageName = stringArgs.get("image name").get(0);
        try {             
        Mat frame = new Mat();
        byte[] BufferdImage = new byte[(int) (frame.total() * frame.channels())];
        frame.get(0, 0, BufferdImage);
        InputStream is = new ByteArrayInputStream(BufferdImage);
        BufferedImage image = ImageIO.read(is);
        Engine.getInstance().allocImage(imageName, image);
        }
        catch (IOException e) {
            System.out.println("no image found");
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
