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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
/**
 *
 * @author Amr_Ayman
 */
public class CmdHistogram extends AbstractCommand
{  
    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
	  new Parameter("src", Type.MAT_ID, 1, null, "source image name", false, false),
          new Parameter("dst", Type.MAT_ID, 1, null, "destination image name", false, false)
	  };
    }
/**
 *   this method executes the calculateHistogram Method on the inputted image
 *   and stores the result in memory
 */
    @Override
    protected Object executeSafe() {
        String Src=getArgImgId("src",0);                       
        String Dst=getArgImgId("dst",0);                      
        Mat Source = Engine.getInstance().getImage(Src);
        Mat Destination=calculateHistogram(Source);
        Engine.getInstance().allocImage(Dst, Destination);
        return null;
    }

    @Override
    public String getName() {
        return("hist");
    }

    @Override
    public String getMan() {
        return("calculate the histogram for the image");
    }
/**
 * This Method calculates the histogram of an input image,
 *   based on the number of channels this image has.  
 */

private Mat calculateHistogram(Mat image)
{
    int histSize=256;
    int channelNos=image.channels();
    Mat hist_b = new Mat();
    Mat hist_g = new Mat();
    Mat hist_r= new Mat();
    List<Mat> channels = new ArrayList<>();
    Core.split(image, channels);
    Imgproc.calcHist(
            Arrays.asList( new Mat[]{channels.get(0)}), new MatOfInt(0), 
            new Mat(), hist_b, new MatOfInt(256), new MatOfFloat(0.0f, 256.0f));
    if(channelNos>1)
    {
        Imgproc.calcHist(
                Arrays.asList( new Mat[]{channels.get(1)}), new MatOfInt(0), 
                new Mat(), hist_g, new MatOfInt(256), new MatOfFloat(0.0f, 256.0f));
        Imgproc.calcHist(
                Arrays.asList( new Mat[]{channels.get(2)}), new MatOfInt(0), 
                new Mat(), hist_r, new MatOfInt(256), new MatOfFloat(0.0f, 256.0f));
    }
int hist_w = image.width()/2, hist_h = image.height()/2;
double bin_w =(double) Math.round(hist_w/histSize );
Mat histImage = new Mat(hist_h, hist_w, CvType.CV_8UC3, new Scalar(0, 0, 0));
Core.normalize(hist_b, hist_b, 0, histImage.rows(), Core.NORM_MINMAX);
if(channelNos>1)
{
    Core.normalize(hist_g, hist_g, 0, histImage.rows(), Core.NORM_MINMAX);
    Core.normalize(hist_r, hist_r, 0, histImage.rows(), Core.NORM_MINMAX);
}
for (int i = 0; i < histSize; i++)
{
    if(channelNos > 1)
    {
        Core.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_b.get(i - 1, 0)[0])), new Point(bin_w * (i), hist_h - Math.round(hist_b.get(i, 0)[0])), new Scalar(255, 0, 0), 2, 8, 0);
        Core.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_g.get(i - 1, 0)[0])),new Point(bin_w * (i), hist_h - Math.round(hist_g.get(i, 0)[0])), new Scalar(0, 255, 0), 2, 8, 0);
        Core.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_r.get(i - 1, 0)[0])),new Point(bin_w * (i), hist_h - Math.round(hist_r.get(i, 0)[0])), new Scalar(0, 0, 255), 2, 8, 0);
    }
    else
    {
        Core.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_b.get(i - 1, 0)[0])), new Point(bin_w * (i), hist_h - Math.round(hist_b.get(i, 0)[0])), new Scalar(255, 255, 255), 2, 8, 0);
    }
}
return histImage;
}
}