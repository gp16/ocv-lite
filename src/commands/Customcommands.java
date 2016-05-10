/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import org.opencv.core.Mat;

/**
 *
 * @author Amr_Ayman
 */
public class Customcommands {
    public void addImage(String imageName,Mat mat)
    {
        AddImage addImage = new AddImage(imageName,mat);
    }
    public void getMan()
    {
       Man manual = new Man(); 
    }
    public void imshow(Mat mat)
    {
        imshow imshow = new imshow(mat);
    }
    public void createHistogram(Mat mat)
    {
        autoHistogram histogram = new autoHistogram();
        histogram.calculateHistogram(mat);
    }
}
