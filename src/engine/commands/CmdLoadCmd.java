package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.ICommand;
import engine.Parameter;
import engine.Type;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CmdLoadCmd extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
            new Parameter("path", Type.SYS_PATH, 1, null, "Path to the folder containing the package", true, false),
            new Parameter("class_name", Type.STR, 1, null, "Fully qualifed name of the class", true, false),
        };
    }

    @Override
    protected Object executeSafe() {

        URL url;
        try {
            url = new File(getArgPath("path", 0)).toURI().toURL();
            URL[] classUrls = { url };
            URLClassLoader ucl = new URLClassLoader(classUrls);
            Class pluginClass = ucl.loadClass(getArgString("class_name", 0));
            
            if(ICommand.class.isAssignableFrom(pluginClass)) {
                ICommand plugin = (ICommand) pluginClass.newInstance();
                Engine.getInstance().registerCommand(plugin);
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(CmdLoadCmd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CmdLoadCmd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CmdLoadCmd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CmdLoadCmd.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    public String getName() {
        return "load_cmd";
    }

    @Override
    public String getMan() {
        return "Registers a command into the engine at runtime";
    }    
}
