package main;

import engine.Argument;
import engine.Engine;
import engine.ICommand;
import engine.Type;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import interpreter.*;

public class Main
{
    public static void main(String[] args)
    {
	// example
	ICommand cmd = Engine.getInstance().getCommand("hello");
	cmd.execute(new Argument("Ahmad"), new Argument(5));
	// example for image capture
	Scanner input = new Scanner(System.in);
	System.out.println("enter image name:");
	String name = input.next();
	ICommand cmdImgCapture=Engine.getInstance().getCommand("capture");
        cmdImgCapture.execute(
                new Argument(Type.IMG_ID,name)
        );
        
       
        
        
        // example for image load 
        
        System.out.println("Enter image path : ");
        String path = input.next();
        String imageName = input.next();
        ICommand cmdImgLoad = Engine.getInstance().getCommand("load");
        cmdImgLoad.execute(
                new Argument(Type.SYS_PATH, path),
                new Argument(Type.IMG_ID, imageName)
        );
        JLabel label = new JLabel(new ImageIcon(path));
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(label);
        f.pack();
        f.setLocation(200,200);
        f.setVisible(true);
        
        
        //Convert to gray example  
        ICommand cmdToGray = Engine.getInstance().getCommand("toGray");
        System.out.println("enter image name to gray:");
	String nameforgray = input.next();
        System.out.println("enter the mem name:");
	String nameforsave=input.next();
        cmdToGray.execute(
                new Argument(Type.IMG_ID,nameforgray),
                new Argument(Type.IMG_ID,nameforsave)
        );

        // example for image save
        
        System.out.println("enter image name:");
        String imgname=input.next();
        System.out.println("enter image path:");
        String savePath=input.next();
         ICommand CmdSave=Engine.getInstance().getCommand("save");
         CmdSave.execute(
                new Argument(Type.SYS_PATH, path),
                new Argument(Type.IMG_ID, imageName)
        );

         
         
    //example of interpreter and imagr flip
        String CommPath ="Flip ;dmsm;ms" ;
        Interpreter interpreter = new Interpreter(CommPath);   
        ICommand cmdF;
        cmdF = interpreter.get_command();
        Scanner input2 = new Scanner(System.in);
        String nameforflip=input2.next();
        String nameforsaveflip=input2.next();       
        cmdF.execute
        (
                new Argument(Type.IMG_ID,nameforflip),
                new Argument(Type.IMG_ID,nameforsaveflip)
        );
        
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