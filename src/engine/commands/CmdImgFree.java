package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import java.awt.image.BufferedImage;

public class CmdImgFree extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
            new Parameter("imageName", Type.STR, 1, 0, "name of image to be deleted", false, false)
        };
    }

    @Override
    protected Object executeSafe() {
        String name = stringArgs.get("imageName").get(0);
        
        
        
        return null;
    }

    @Override
    public String getName() {
        return("free");
    }

    @Override
    public String getMan() {
        return("Delete image from memory");
    }
    
}
