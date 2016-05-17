/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Amr_Ayman
 */
public class CmdPyr extends AbstractCommand 
{
    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]
        {
            new Parameter("src", Type.MAT_ID, 1, null, "source image", false, false),
            new Parameter("dst", Type.MAT_ID, 1, null, "destination image", false, false),
            new Parameter("type", Type.STR, 1, null, "'in' to zoom-in,\n 'out' to zoom-out", false, false)
        };
    }

    @Override
    protected Object executeSafe() 
    {
       String src=getArgImgId("src", 0);
       String dst=getArgImgId("dst", 0);
       String type=getArgString("type", 0);
       Mat destination=new Mat();
       Mat source = Engine.getInstance().getImage(src);
       if("in".equals(type))
       {
        Imgproc.pyrUp(source, destination,new Size(source.cols()*2, source.rows()*2));
       }
       else if("out".equals(type))
       {
         Imgproc.pyrDown(source, destination,new Size(source.cols()/2, source.rows()/2));   
       }
       Engine.getInstance().allocImage(dst, destination);                         
       return null;
    }

    @Override
    public String getName() {
        return "pyr";
    }

    @Override
    public String getMan() {
        return "Zoom in/out of an image";
    }
}
