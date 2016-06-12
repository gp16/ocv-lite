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
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Amr_Ayman
 */
public class CmdColor extends AbstractCommand{
    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("Source", Type.MAT_ID, 1, null, "image name to get from memory", false, false),
        new Parameter("Destination", Type.MAT_ID, 1, null, "image name to get from memory", false, false),
        new Parameter("Code", Type.INT, 1, Integer.MAX_VALUE, "color space code for that the source image will be coverted to. \n" 
                    +"COLOR_BGR2BGRA = 0,\n"  
                    +"COLOR_BGRA2BGR = 1,\n"  
                    +"COLOR_BGR2RGBA = 2,\n"
                    +"COLOR_RGBA2BGR = 3,\n"
                    +"COLOR_BGR2RGB = 4,\n"
                    +"COLOR_BGRA2RGBA = 5,\n"
                    +"COLOR_BGR2GRAY = 6,\n"
                    +"COLOR_RGB2GRAY = 7,\n"
                    +"COLOR_GRAY2BGR = 8,\n"
                    +"COLOR_GRAY2BGRA = 9,\n"
                    +"COLOR_BGRA2GRAY = 10,\n"
                    +"COLOR_RGBA2GRAY = 11, \n" 
                    +"COLOR_BGR2BGR565 = 12, \n" 
                    +"COLOR_RGB2BGR565 = 13, \n" 
                    +"COLOR_BGR5652BGR = 14, \n" 
                    +"COLOR_BGR5652RGB = 15, \n" 
                    +"COLOR_BGRA2BGR565 = 16, \n" 
                    +"COLOR_RGBA2BGR565 = 17, \n" 
                    +"COLOR_BGR5652BGRA = 18, \n" 
                    +"COLOR_BGR5652RGBA = 19, \n" 
                    +"COLOR_GRAY2BGR565 = 20, \n" 
                    +"COLOR_BGR5652GRAY = 21, \n" 
                    +"COLOR_BGR2BGR555 = 22, \n" 
                    +"COLOR_RGB2BGR555 = 23, \n" 
                    +"COLOR_BGR5552BGR = 24, \n" 
                    +"COLOR_BGR5552RGB = 25, \n" 
                    +"COLOR_BGRA2BGR555 = 26, \n" 
                    +"COLOR_RGBA2BGR555 = 27, \n" 
                    +"COLOR_BGR5552BGRA = 28, \n" 
                    +"COLOR_BGR5552RGBA = 29, \n" 
                    +"COLOR_GRAY2BGR555 = 30, \n" 
                    +"COLOR_BGR5552GRAY = 31, \n" 
                    +"COLOR_BGR2XYZ = 32, \n" 
                    +"COLOR_RGB2XYZ = 33, \n" 
                    +"COLOR_XYZ2BGR = 34, \n" 
                    +"COLOR_XYZ2RGB = 35, \n" 
                    +"COLOR_BGR2YCrCb = 36, \n" 
                    +"COLOR_RGB2YCrCb = 37, \n" 
                    +"COLOR_YCrCb2BGR = 38, \n" 
                    +"COLOR_YCrCb2RGB = 39, \n" 
                    +"COLOR_BGR2HSV = 40, \n" 
                    +"COLOR_RGB2HSV = 41, \n" 
                    +"COLOR_BGR2Lab = 44, \n" 
                    +"COLOR_RGB2Lab = 45, \n"
                    +"COLOR_BayerBG2BGR = 46, \n" 
                    +"COLOR_BayerGB2BGR = 47, \n" 
                    +"COLOR_BayerRG2BGR = 48, \n" 
                    +"COLOR_BayerGR2BGR = 49, \n"                 
                    +"COLOR_BGR2Luv = 50, \n" 
                    +"COLOR_RGB2Luv = 51, \n" 
                    +"COLOR_BGR2HLS = 52, \n" 
                    +"COLOR_RGB2HLS = 53, \n" 
                    +"COLOR_HSV2BGR = 54, \n" 
                    +"COLOR_HSV2RGB = 55, \n" 
                    +"COLOR_Lab2BGR = 56, \n" 
                    +"COLOR_Lab2RGB = 57, \n" 
                    +"COLOR_Luv2BGR = 58, \n" 
                    +"COLOR_Luv2RGB = 59, \n" 
                    +"COLOR_HLS2BGR = 60, \n" 
                    +"COLOR_HLS2RGB = 61, \n" 
                    +"COLOR_BayerBG2BGR_VNG = 62, \n" 
                    +"COLOR_BayerGB2BGR_VNG = 63, \n" 
                    +"COLOR_BayerRG2BGR_VNG = 64, \n" 
                    +"COLOR_BayerGR2BGR_VNG = 65, \n"                 
                    +"COLOR_BGR2HSV_FULL = 66, \n" 
                    +"COLOR_RGB2HSV_FULL = 67, \n" 
                    +"COLOR_BGR2HLS_FULL = 68, \n" 
                    +"COLOR_RGB2HLS_FULL = 69, \n" 
                    +"COLOR_HSV2BGR_FULL = 70, \n" 
                    +"COLOR_HSV2RGB_FULL = 71, \n" 
                    +"COLOR_HLS2BGR_FULL = 72, \n" 
                    +"COLOR_HLS2RGB_FULL = 73, \n" 
                    +"COLOR_LBGR2Lab = 74, \n" 
                    +"COLOR_LRGB2Lab = 75, \n" 
                    +"COLOR_LBGR2Luv = 76, \n" 
                    +"COLOR_LRGB2Luv = 77, \n" 
                    +"COLOR_Lab2LBGR = 78, \n" 
                    +"COLOR_Lab2LRGB = 79, \n" 
                    +"COLOR_Luv2LBGR = 80, \n" 
                    +"COLOR_Luv2LRGB = 81, \n" 
                    +"COLOR_BGR2YUV = 82, \n" 
                    +"COLOR_RGB2YUV = 83, \n" 
                    +"COLOR_YUV2BGR = 84, \n" 
                    +"COLOR_YUV2RGB = 85, \n" 
                    +"COLOR_BayerBG2GRAY = 86, \n"
                    +"COLOR_BayerGB2GRAY = 87, \n"
                    +"COLOR_BayerRG2GRAY = 88, \n"
                    +"COLOR_BayerGR2GRAY = 89, \n"                
                    +"COLOR_YUV2RGB_NV12 = 90, \n" 
                    +"COLOR_YUV2BGR_NV12 = 91, \n" 
                    +"COLOR_YUV2RGB_NV21 = 92, \n" 
                    +"COLOR_YUV2BGR_NV21 = 93, \n" 
                    +"COLOR_YUV2RGBA_NV12 = 94, \n" 
                    +"COLOR_YUV2BGRA_NV12 = 95, \n" 
                    +"COLOR_YUV2RGBA_NV21 = 96, \n" 
                    +"COLOR_YUV2BGRA_NV21 = 97, \n" 
                    +"COLOR_YUV2RGB_YV12 = 98, \n" 
                    +"COLOR_YUV2BGR_YV12 = 99, \n" 
                    +"COLOR_YUV2RGB_IYUV = 100, \n" 
                    +"COLOR_YUV2BGR_IYUV = 101, \n" 
                    +"COLOR_YUV2RGBA_YV12 = 102, \n" 
                    +"COLOR_YUV2BGRA_YV12 = 103, \n" 
                    +"COLOR_YUV2RGBA_IYUV = 104, \n" 
                    +"COLOR_YUV2BGRA_IYUV = 105, \n" 
                    +"COLOR_YUV2GRAY_420 = 106, \n" 
                    +"COLOR_YUV2RGB_UYVY = 107, \n" 
                    +"COLOR_YUV2BGR_UYVY = 108, \n" 
                    +"COLOR_YUV2RGBA_UYVY = 111, \n" 
                    +"COLOR_YUV2BGRA_UYVY = 112, \n" 
                    +"COLOR_YUV2RGB_YUY2 = 115, \n" 
                    +"COLOR_YUV2BGR_YUY2 = 116, \n" 
                    +"COLOR_YUV2RGB_YVYU = 117, \n" 
                    +"COLOR_YUV2BGR_YVYU = 118, \n" 
                    +"COLOR_YUV2RGBA_YUY2 = 119, \n" 
                    +"COLOR_YUV2BGRA_YUY2 = 120, \n" 
                    +"COLOR_YUV2RGBA_YVYU = 121, \n" 
                    +"COLOR_YUV2BGRA_YVYU = 122, \n" 
                    +"COLOR_YUV2GRAY_UYVY = 123, \n" 
                    +"COLOR_YUV2GRAY_YUY2 = 124, \n" 
                    +"COLOR_RGBA2mRGBA = 125, \n" 
                    +"COLOR_mRGBA2RGBA = 126, \n" 
                    +"COLOR_RGB2YUV_I420 = 127, \n" 
                    +"COLOR_BGR2YUV_I420 = 128, \n" 
                    +"COLOR_RGBA2YUV_I420 = 129, \n"
                    +"COLOR_BGRA2YUV_I420 = 130, \n"
                    +"COLOR_RGB2YUV_YV12 = 131, \n" 
                    +"COLOR_BGR2YUV_YV12 = 132, \n" 
                    +"COLOR_RGBA2YUV_YV12 = 133, \n"
                    +"COLOR_BGRA2YUV_YV12 = 134, \n"
                    +"COLOR_BayerBG2BGR_EA = 135, \n" 
                    +"COLOR_BayerGB2BGR_EA = 136, \n"
                    +"COLOR_BayerRG2BGR_EA = 137, \n"
                    +"COLOR_BayerGR2BGR_EA = 138, \n"
                    +"COLOR_COLORCVT_MAX = 139", false, false),
        };
    }

    @Override
    protected Object executeSafe() 
    {
        
        String Source=getArgImgId("Source",0);                       
        String Destination=getArgImgId("Destination",0);                      
        Mat Source_Mat = Engine.getInstance().getImage(Source);
        Mat Destination_Mat = new Mat();
        int code = getArgInt("Code", 0);
        Imgproc.cvtColor(Source_Mat, Destination_Mat, code);
        
        Engine.getInstance().allocImage(Destination, Destination_Mat);                         

        return null;
    }

    @Override
    public String getName() {
        return "color";
    }

    @Override
    public String getMan() {
        return "Convert Image color To a different color space";
    }
}
