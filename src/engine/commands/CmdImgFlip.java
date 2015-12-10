package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 *
 * @author Elmohand Haroon
 */
public class CmdImgFlip extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() 
    {
        return new Parameter[]{
        new Parameter("NameToFlip", Type.IMG_ID, 1, null, "image name to get from memory", false, false),
        new Parameter("NameToSave", Type.IMG_ID, 1, null, "image name to save into memory", false, false)};
 
    }

    @Override
    protected Object executeSafe() 
    {
        
        
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            String NameToFlip=stringArgs.get("NameToFlip").get(0);
            String NameToSave=stringArgs.get("NameToSave").get(0);
            BufferedImage memImg = Engine.getInstance().getImage(NameToFlip);
           
            byte[] data = ((DataBufferByte) memImg.getRaster().  getDataBuffer()).getData();
            Mat mat = new Mat(memImg.getHeight(),memImg.getWidth(),CvType.CV_8UC3);
            mat.put(0, 0, data);
            
            Mat mat1 = new Mat(memImg.getHeight(),memImg.getWidth(),CvType.CV_8UC3);
            Core.flip(mat, mat1, -1);
            
            byte[] data1 = new byte[mat1.rows()*mat1.cols()*(int)(mat1.elemSize())];
            mat1.get(0, 0, data1);
            BufferedImage image1 = new BufferedImage(mat1.cols(), mat1.rows(), 5);
            image1.getRaster().setDataElements(0,0,mat1.cols(),mat1.rows(),data1);
            
            
            Engine.getInstance().allocImage(NameToSave, image1); 
            return null; 
    }

    @Override
    public String getName() 
    {
         return "toFlip";
    }

    @Override
    public String getMan() 
    {
        return "Flip Image";
    }
    
}
