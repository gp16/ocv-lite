# 1 Introduction
ocv-lite is a project that aims to simplify using OpenCV by providing
easy-to-use wrappers around to the library.

# 2 Wrappers
There are essentially three wrappers, namely, the engine, the GUI frontend, and
the interpreter. Each of these parts serve a different purpose as we will see in
the subsequent sections.

## 2.1 Engine
The engine is the core component of the project that handles all the OpenCV logic.
Its main purpose is to power the other two wrappers, the interpreter and the
GUI. The engine is meant to be an internal part hidden from the end-user. And
indeed, the user doesn't need to it directly since it is guaranteed that the
other wrappers have all the capabilities the engine has. With that said, there
are no techincal barriers preventing the user from accessing the engine directly
if he wishes.

### 2.1.1 Commands
The engine is responsible for encapsulating OpenCV functionalities and allowing
the developer to access them through a simple API. These functionalities are
encapsulated into objects of type `ICommand`, which is the building block of
the engine. You can think of an `ICommand` as a callback that executes a
specific task related to OpenCV. For example, loading an image, converting it to
greyscale, performing edge detection on it, etc,. Each of these commands is
identifed by a name. For example, the command responsible for loading an image
is identified by the name "load". In order to access a command of the engine and
execute it, you need to know its name.

### 2.1.2 Sample commands
Here is a list of some commands to give you an idea of what commands are about

1. CmdLoad
2. CmdSave
3. CmdFree
4. CmdCamCapture (TODO) (already implemented under a different name, CmdImgCapture)
5. CmdFlip (TODO)
6. CmdLoadCommand (TODO)


### 2.1.3 Images
The engine also keeps track of the images it is working on. Each image is identified
by a string, which we sometimes refere to as the image Id. To store an image in
the engine memory, you need provide a unique Id for it. And when you need to
access the same image again, you will need to just provide the same Id again.

### 2.1.4 Example
Here is an example of how to use the API to load an image from disk, flip it,
and save the resulting image on disk again.

```
// --- Get reference to the engine --- //
Engine engine = Engine.getInstance();

// --- 1) Load the image --- //
// Get reference to the load command
ICommand load = engine.getCommand("load");

// The load command expects two arguments, the first is the path to the
// image on disk, the second is the new image Id
load.execute("/path/to/image.png", "myImage1");


// --- 2) Flip it --- //
// Get reference to the flip command
ICommand flip = engine.getCommand("flip");

// The flip command expects two image Ids, source and destination
flip.execute("myImage1", "myImage2");


// --- 3) Save the result --- //
// Get reference to the save command
ICommand save = engine.getCommand("save");

// The save command expects two arguments, the image id and path on disk
save.execute("myImage2", "/path/to/flipped.png");
```

The reason behind using string names to access commands is that this is the only
way the interpreter can refere to a command. Because the input to the
interpreter is a script which is, of course, in plaintext.


(TODO) There is also the `Engine.execute(String cmdName, Object arguments...)`
shortcut that can make the code more compact. So instead of typing
```
ICommand save = engine.getCommand("save");
save.execute("myImage2", "/path/to/flipped.png");
```

You can just type
```
engine.execute("save", "myImage2", "/path/to/flipped.png");
```

`Engine.execute()` will automagically choose the correct command based on the
first string you supply. All the other arguments will be sent to the chosen
command.


### 2.1.4 Arguments and parameters

Commands are not very permissive when it comes to receiving arguments. If it
expects the first argument to be a string, then it must be. Sending an argument
of wrong type will raise an error. And so will sending too many or too few
arguments.

This raises a question, how does a command decide whether the arguments are
valid or not? The answer to this question has more to do with how the API is
programmed than how it is be used. There are two mechanisms to ensure that the
passed arguments are valid.

The first mechanism allows each `ICommand` to specify an array of `Parameter`
objects. This array serves as a function signature in procedural languages like
C, it is used as a reference to tell whether an array of arguments is valid or
not. To make the separation between parameters and arguments clearer, think of a
parameter array as a property of a command and an argument array what is
actually passed to the command.

The second mechanism is type checking. The array of arguments is checked against
the array of parameters to see if they are valid or not. In fact, it is not
guaranteed that all `ICommand` objects implement type checking. This is why the
API provieds the `AbstractCommand` class, which implements `ICommand` and
provides some basic traits like type checking, argument loading, usage manual,
etc,. On a side note, command programmers are encouraged to extend
`AbstractCommand` rather than implement `ICommand` directly.

In order to execute a command, you should send it a valid array of arguments.
Fortunately, you can use `AbstractCommand.showFullMan()`, which displays
everything about the parameters, including their names, their types, what they
are used for, whether they are optional, and finally (TODO) whether they can
recurr.


### 2.1.5 Dynamically loaded commands (TODO)
A dynamically loaded command is similar to a plugin in the sense that it allows
users to add new features that didn't exist before. And by new features here, we
mean new commands. The user can create a new command by writing a class that
implements `ICommand`, compiling it, and instructing the engine to load it.
Once the user does that, all of the three wrappers are automagically notified of
the new command, and the user can access it from any of them.

Here is an example
```
ICommand load_cmd = engine.getCommand("cmd_load");
load_cmd.execute("/path/to/jar/file/", "command_class_name");

// you can now use the command you just loaded
cmd = engine.getCommand("command_name");
cmd.execute(...);
```

## 2.2 Interpreter
(TODO)

## 2.3 GUI
(TODO)