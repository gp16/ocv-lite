package engine;

import engine.commands.*;
import java.util.HashMap;
import java.util.TreeMap;
import org.opencv.core.Mat;

/**
 * 
 * Responsible for loading commands and storing images.
 */
public final class Engine
{
    private static Engine instance;
    
    private final TreeMap<String, ICommand> CMDS = new TreeMap<>();
    private final TreeMap<String, Mat> IMGS = new TreeMap<>();
    private final HashMap<String, Object> STRUCTS = new HashMap<>();
    // singleton pattern
    private Engine() {
	registerCommand(new CmdImgCapture());
        registerCommand(new CmdImgLoad());
        registerCommand(new CmdImgFree());
        registerCommand(new CmdImgSave());
        registerCommand(new CmdImgFlip());
        registerCommand(new CmdImgEdge());
        registerCommand(new CmdImgDetect());
        registerCommand(new CmdMan());
        registerCommand(new CmdThreshold());
        registerCommand(new CmdImgBlur());
        registerCommand(new CmdImgCanny());
        registerCommand(new CmdHistogram());
        registerCommand(new CmdEqualize());
        registerCommand(new CmdPyr());
        registerCommand(new CmdDilation());
        registerCommand(new CmdErosion());
        registerCommand(new CmdMorph());
        registerCommand(new CmdHoughCircles());
        registerCommand(new CmdcopyMakeBorder());
        registerCommand(new Cmdaccumulate());
        registerCommand(new CmdSubtract());
        registerCommand(new CmdLoadCmd());
        registerCommand(new Cmdresize());
        registerCommand(new CmdmatchTemplate());
        registerCommand(new CmdLaplacian());
        registerCommand(new CmdPutText());
        registerCommand(new CmdCornerHarris());
        registerCommand(new CmdAddWeighted());
	registerCommand(new CmdGaussianBlur());
        registerCommand(new CmdAbsdiff());
        registerCommand(new CmdBilateralFilter());
        registerCommand(new CmdColor());
        registerCommand(new CmdRandShuffle());
        registerCommand(new CmdAdd());
        registerCommand(new CmdTranspose());
        registerCommand(new CmdInRange());
        registerCommand(new CmdaccumulateSquare());
        registerCommand(new CmdMultiply1());
        registerCommand(new CmdDivide2());
        registerCommand(new CmdMedianBlur());
        registerCommand(new CmdBoxFilter());
        registerCommand(new CmdDistanceTransform());
        registerCommand(new CmdImgRepeat());
        registerCommand(new CmdImgReduce());
        registerCommand(new CmdRaisesToPower());
        registerCommand(new CmdImgNormalize());
        registerCommand(new CmdMultiply2());
        registerCommand(new CmdMinimum());
        registerCommand(new CmdMaximum());
        registerCommand(new CmdDivide1());
        registerCommand(new CmdDivide3());
        registerCommand(new CmdBitwiseAnd());
        registerCommand(new CmdBitwiseOr());
        registerCommand(new CmdBitwiseXor());
        registerCommand(new CmdFilter2D());
        registerCommand(new CmdImgResize());
        registerCommand(new CmdBitwiseNot());
        registerCommand(new CmdDrawCircle());
        registerCommand(new CmdDrawHoughCircles());
        registerCommand(new CmdScharr());
    }
    
    public static Engine getInstance() {
	if(instance == null)
	    instance = new Engine();
	
	return instance;
    }
    
    public void registerCommand(ICommand command) {
	CMDS.put(command.getName(), command);
    }
    
    public ICommand getCommand(String cmdName) {
	return CMDS.get(cmdName);
    }
    
    public String[] getCommandsNames() {
	return CMDS.keySet().toArray(new String[]{});
    }
    
    public ICommand[] getCommands() {
	return CMDS.values().toArray(new ICommand[]{});
    }
    
    public int getCommandsCount() {
	return CMDS.size();
    }
    
    public String[] getImagesNames() {
	return IMGS.keySet().toArray(new String[]{});
    }

    public Mat[] getImages() {
	return IMGS.values().toArray(new Mat[]{});
    }
    
    // gets an image by name
    public Mat getImage(String imageName) {
	return IMGS.get(imageName);
    }
    
    public int getImagesCount() {
	return IMGS.size();
    }
    
    // puts an image into memory
    public void allocImage(String imageName, Mat image) {
	IMGS.put(imageName, image);
    }
    
    
    // removes an image from memory
    public void deallocImage(String imageName) {
	IMGS.remove(imageName);
    }
    //puts a structure into memory
    public void allocStructs(String structName, Object struct) {
	STRUCTS.put(structName, struct);
    }
    //get a structure by name
    public Object getStruct(String structName) {
	return STRUCTS.get(structName);
    }
    //removes a structure from memory
   public void deallocStruct(String structName) {
	STRUCTS.remove(structName);
    }
}   