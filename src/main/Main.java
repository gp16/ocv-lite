package main;

import engine.Engine;
import engine.ICommand;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
	// example
	ICommand cmd = Engine.getInstance().getCommand("hello");
	cmd.execute("Ahmad", 5);
	// example for image capture
	Scanner input = new Scanner(System.in);
	/*System.out.println("enter image name:");
	String name = input.next();
	ICommand cmdImgCapture=Engine.getInstance().getCommand("capture");
        cmdImgCapture.execute(name);
        */
        
        // example for image load 
        
        System.out.println("Enter image path: ");
        String path = input.next();
        ICommand cmdImgLoad = Engine.getInstance().getCommand("load");
        cmdImgLoad.execute(path);
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