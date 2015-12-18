package main;

import engine.Argument;
import engine.Engine;
import engine.ICommand;
import engine.Type;
import javax.swing.JFrame;
import ui.api.GenericWindowContainer;

public class Main {

    public static void main(String[] args) {
//        Engine engine = Engine.getInstance();
//        ICommand load = engine.getCommand("load");
//        ICommand flip = engine.getCommand("toFlip");
//        ICommand save = engine.getCommand("save");
//        
//        load.execute(
//                new Argument(Type.SYS_PATH, "C:\\Users\\ALKHIAMY\\Desktop\\balloons.jpg"),
//                new Argument(Type.IMG_ID, "balloons")
//        );
//
//        flip.execute(
//                new Argument(Type.IMG_ID, "balloons"),
//                new Argument(Type.IMG_ID, "balloons_flipped")
//        );
//
//        save.execute(
//                new Argument(Type.SYS_PATH, "C:\\Users\\ALKHIAMY\\Desktop\\balloons-flipped.jpg"),
//                new Argument(Type.IMG_ID, "balloons_flipped")
//                
//        );

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GenericWindowContainer container = new GenericWindowContainer();
        frame.add(container);
        frame.pack();
        frame.setVisible(true);
    }
}

/*
 How to implement your own ocv-lite commands!
 
 1- Define a class that implements ICommand (or better, extends AbstractCommand) in
 the package engine.commands. Make sure the class is public and is defined in
 its own file with the same name. For example, if you make a class named CmdFoo,
 make sure the file name is `CmdFoo.java`.
 
 2- Register your command in the constructor of engine.Engine as following
 registerCommand(new CmdFoo());
 
 3- All ocv-lite commands should start with the 'Cmd' prefix.

 4- For more details on how to access arguments, see CmdHello.java. In short, you
 should not access Object arguments[] directly. Instead, you should read
 from stringArgs, floatArgs and intArgs which are provided by AbstractCommand.

 5- Images should be stored in Engine.IMGS, which can be accessed via several
 methods like allocImage(), deallocImage() and getImage()

 */
