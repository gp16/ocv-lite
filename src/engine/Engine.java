package engine;

import engine.commands.*;
import java.util.HashMap;
import org.opencv.core.Mat;

/**
 * 
 * Responsible for loading commands and storing images.
 */
public final class Engine
{
    private static Engine instance;
    
    private final HashMap<String, ICommand> CMDS = new HashMap<>();
    private final HashMap<String, Mat> IMGS = new HashMap<>();
    // singleton pattern
    private Engine() {
	registerCommand(new CmdHello());    
	registerCommand(new CmdImgCapture());
        registerCommand(new CmdImgLoad());
        registerCommand(new CmdImgFree());
        registerCommand(new CmdImgSave());
        registerCommand(new CmdImgGray());
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
}   