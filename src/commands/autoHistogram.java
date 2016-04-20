/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

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
public class autoHistogram {
    public Mat calculateHistogram(Mat mat)
    {
        int histSize=256;
    int channelNos=mat.channels();
    Mat hist_b = new Mat();
    Mat hist_g = new Mat();
    Mat hist_r= new Mat();
    List<Mat> channels = new ArrayList<>();
    Core.split(mat, channels);
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
int hist_w = mat.width()/2, hist_h = mat.height()/2;
double bin_w =(double) Math.round(hist_w/histSize );
Mat histedMat = new Mat(hist_h, hist_w, CvType.CV_8UC3, new Scalar(0, 0, 0));
Core.normalize(hist_b, hist_b, 0, histedMat.rows(), Core.NORM_MINMAX);
if(channelNos>1)
{
    Core.normalize(hist_g, hist_g, 0, histedMat.rows(), Core.NORM_MINMAX);
    Core.normalize(hist_r, hist_r, 0, histedMat.rows(), Core.NORM_MINMAX);
}
for (int i = 0; i < histSize; i++)
{
    if(channelNos > 1)
    {
        Core.line(histedMat, new Point(bin_w * (i - 1), hist_h - Math.round(hist_b.get(i - 1, 0)[0])), new Point(bin_w * (i), hist_h - Math.round(hist_b.get(i, 0)[0])), new Scalar(255, 0, 0), 2, 8, 0);
        Core.line(histedMat, new Point(bin_w * (i - 1), hist_h - Math.round(hist_g.get(i - 1, 0)[0])),new Point(bin_w * (i), hist_h - Math.round(hist_g.get(i, 0)[0])), new Scalar(0, 255, 0), 2, 8, 0);
        Core.line(histedMat, new Point(bin_w * (i - 1), hist_h - Math.round(hist_r.get(i - 1, 0)[0])),new Point(bin_w * (i), hist_h - Math.round(hist_r.get(i, 0)[0])), new Scalar(0, 0, 255), 2, 8, 0);
    }
    else
    {
        Core.line(histedMat, new Point(bin_w * (i - 1), hist_h - Math.round(hist_b.get(i - 1, 0)[0])), new Point(bin_w * (i), hist_h - Math.round(hist_b.get(i, 0)[0])), new Scalar(255, 255, 255), 2, 8, 0);
    }
}
return histedMat;
    }
}
