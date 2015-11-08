package engine;

import engine.commands.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public final class Engine
{
    private static Engine instance;
    
    private final HashMap<String, ICommand>      CMDS = new HashMap<>();
    private final HashMap<String, BufferedImage> IMGS = new HashMap<>();
    
    // singleton pattern
    private Engine() {
	registerCommand(new CmdHello());    
	registerCommand(new CmdImgCapture());
        registerCommand(new CmdImgLoad());
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

    public BufferedImage[] getImages() {
	return IMGS.values().toArray(new BufferedImage[]{});
    }

    // gets an image by name
    public BufferedImage getImage(String imageName) {
	return IMGS.get(imageName);
    }
    
    public int getImagesCount() {
	return IMGS.size();
    }
    
    // puts an image into memory
    public void allocImage(String imageName, BufferedImage image) {
	IMGS.put(imageName, image);
    }
    
    
    // removes an image from memory
    public void deallocImage(String imageName) {
	IMGS.remove(imageName);
    }
}