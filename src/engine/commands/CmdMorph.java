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
public class CmdMorph extends AbstractCommand{

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]
        {
            new Parameter("src", Type.MAT_ID, 1, null, "source image", false, false),
            new Parameter("dst", Type.MAT_ID, 1, null, "destination image", false, false),
            new Parameter("operation", Type.INT, 0, 4, "0 - MORPH_OPEN - an opening operation\n" +
                    "1 - MORPH_CLOSE - a closing operation\n" +
                    "2 - MORPH_GRADIENT - a morphological gradient\n" +
                    "3 - MORPH_TOPHAT - \"top hat\"\n" +
                    "4 - MORPH_BLACKHAT - \"black hat\"", false, false),
            new Parameter("Ksize", Type.INT, 1, 31, "kernel size where ksize%2 != 0", false, false)
            ,new Parameter("Ktype", Type.INT, 0, 1, "0 - cross, 1 - ellipse", false, false)
        
        };}

    @Override
    protected Object executeSafe() {
         String src=getArgImgId("src", 0);
       String dst=getArgImgId("dst", 0);
       Integer op=getArgInt("operation", 0);
       Integer Ksize=getArgInt("Ksize", 0);
       Integer Ktype=getArgInt("Ktype", 0);
       Mat destination=new Mat();
       Mat source = Engine.getInstance().getImage(src);
       Mat element=Imgproc.getStructuringElement(Ktype, new Size(Ksize,Ksize));
        Imgproc.morphologyEx(source, destination, op, element);
        Engine.getInstance().allocImage(dst, destination);
       return null;
    }

    @Override
    public String getName() {
    return "morphologyEx";
    }

    @Override
    public String getMan() {
    return "Performs advanced morphological transformations";    
    }
    
}
