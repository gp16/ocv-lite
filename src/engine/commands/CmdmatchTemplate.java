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
import org.opencv.core.Core.MinMaxLocResult;
import static org.opencv.core.Core.NORM_MINMAX;
import static org.opencv.core.CvType.CV_32FC1;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.TM_CCOEFF_NORMED;
import static org.opencv.imgproc.Imgproc.TM_CCORR;
import static org.opencv.imgproc.Imgproc.TM_CCORR_NORMED;
import static org.opencv.imgproc.Imgproc.TM_SQDIFF;
import static org.opencv.imgproc.Imgproc.TM_SQDIFF_NORMED;

/**
 *
 * @author Amr_Ayman
 */
public class CmdmatchTemplate extends AbstractCommand{

    @Override
    protected Parameter[] getParamsOnce() {
    return new Parameter[] {
	  new Parameter("image", Type.MAT_ID, 1, null, "Image where the search is running", false, false),
          new Parameter("template",Type.MAT_ID, 1, null, "Searched template.", false, false),
          new Parameter("method",Type.INT, 1,5 , "comparison method (1 -> TM_SQDIFF,2 -> TM_SQDIFF_NORMED,3 -> TM_CCORR_NORMED,4 -> TM_CCORR,5 -> TM_CCOEFF_NORMED)", false, false),
          };
    }

    @Override
    protected Object executeSafe() {
        String Image = getArgImgId("image", 0);
        String Template = getArgImgId("template", 0);
        Mat image = engine.Engine.getInstance().getImage(Image);
        Mat template = engine.Engine.getInstance().getImage(Template);
        Mat result = new Mat((image.rows()-template.rows()+1),(image.cols()-template.cols()+1),CV_32FC1);
        int method = getArgInt("method", 0);
        switch(method)
        {
            case 1:
                method = TM_SQDIFF;
                break;
            case 2:
                method = TM_SQDIFF_NORMED;
                break;
            case 3:
                method = TM_CCORR_NORMED;
                break;
            case 4:
                method = TM_CCORR;
                break;
            case 5:
                method = TM_CCOEFF_NORMED;
                break;        
                
        }
        Imgproc.matchTemplate(image, template, result, method);
        Point matchLoc;
        MinMaxLocResult minmax=Core.minMaxLoc(result);
        if( method  == TM_SQDIFF || method == TM_SQDIFF_NORMED )
        { matchLoc = minmax.minLoc; }
        else
        { matchLoc = minmax.maxLoc; }
        Core.rectangle( image, matchLoc,new Point( matchLoc.x + template.cols() , matchLoc.y + template.rows()), new Scalar(0,0,0));
        Engine.getInstance().allocImage(Image,image);
        return null;
    }

    @Override
    public String getName() {
     return "matchTemplate";
    }

    @Override
    public String getMan() {
        return "Compares a template against the source image and draws a rectangle arround the matching area";
    }
    
}
