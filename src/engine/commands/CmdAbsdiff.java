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
import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 *
 * @author Amr_Ayman
 */
public class CmdAbsdiff extends AbstractCommand
{

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("Source", Type.MAT_ID, 1, null, "First image name to get from memory", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null, "Second image name to get from memory", false, false),
            new Parameter("Result", Type.MAT_ID, 1, null, "Result image name that will be saved in memory", false, false)};
    }
    
    @Override
    protected Object executeSafe() {
        String Src=getArgImgId("Source",0);                       
        String Dst=getArgImgId("Destination",0);
        String Result=getArgImgId("Result",0);
        Mat src = Engine.getInstance().getImage(Src);
        Mat dst = Engine.getInstance().getImage(Dst);
        Mat result = new Mat();
        Core.absdiff(src, dst, result);
        Engine.getInstance().allocImage(Result, result);                         
        return null;
    }
    
    @Override
    public String getName() {
        return "absdiff";
    }
    
    @Override
    public String getMan() {
         return "Calculate difference between two images";
    }
    
}