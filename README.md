[![build status](https://magnum-ci.com/status/f300ab2a3148c10ca15b9470272749ff.png)](https://magnum-ci.com/projects/3863/history)

# 1 Introduction
ocv-lite is a project that aims to simplify using OpenCV by providing
easy-to-use wrappers around it.

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
are no technical barriers preventing the user from accessing the engine directly
if he wishes.

### 2.1.1 Commands
The engine is responsible for encapsulating OpenCV functionalities and allowing
the developer to access them through a simple API. These functionalities are
encapsulated into objects of type `ICommand`, which is the building block of
the engine. You can think of an `ICommand` as a callback that executes a
specific task related to OpenCV. For example, loading an image, converting it to
greyscale, performing edge detection on it, etc,. Each of these commands is
identified by a name. For example, the command responsible for loading an image
is identified by the name "load". In order to access a command of the engine and
execute it, you need to know its name.

### 2.1.2 Sample commands
Here is a list of some commands to give you an idea of what commands are about

1. CmdImgLoad
2. CmdImgSave
3. CmdImgFree
4. CmdImgCapture
5. CmdImgFlip

### 2.1.3 Matrices
Matrices are used internally to represent images. The engine keeps track of the
matrices (internally represented as matrices) it is working on. Each matrix is
identified by a string, which we sometimes refer to as the MAT_Id. To store a
matrix in the engine memory, you need provide a unique Id for it. And when you
need to access the same matrix again, you will need to just provide the same Id
again.

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
way the interpreter can refer to a command. Because the input to the
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
API provides the `AbstractCommand` class, which implements `ICommand` and
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
The interpreter exposes the engine commands to the front-end user in a
systematic way. The user is allowed to write statements and send them to the 
interpreter, which executes them one by one in a sequential order. A statement
is a string of text that instructs the interpreter to do something. It should
contain the name of the engine command to be executed and the arguments to be
sent to it.

### 2.2.1 Interpreter modes
The interpreter can be used either in interactive or batch mode. In the
interactive mode, the interpreter waits for the user to manually enter the
statements and executes each statements once it is entered. In the batch mode,
the user can group all the commands he wants to execute in one file and then
feed it to the interpreter.

To run the interpreter in interactive mode, type into terminal
```
java -jar ocvlite.jar -i
```
And for batch mode
```
java -jar ocvlite.jar -i /path/to/script.ocvl
```

### 2.2.2 Syntax
A script is a list of statements separated by new lines. Each statement starts
with the command name followed by it arguments. For example,
```
    load p"/path/to/image.png" img1
    flip img1 img2 
    resize img2 img3 2.0 
    save img3 p"/path/to/result.png"
```
The above script loads the image named "image.png", flips it, doubles its size,
and then saves it as "result.png".

Different argument types are written differently
1. Strings are enclosed within single quotes
2. System paths are enclosed within single quotes with a *p* prefix
3. Command Ids are enclosed within single quotes with a *c* prefix
4- Matrix Ids, intengers and floats are not enclosed within anything

The arguments that the interpreter can recognize are integers, floats, and
strings. The interpreter is then responsible for casting these arguments
including strings into types that the engine expects like STR, SYS_PATH, MAT_ID,
CMD_ID, etc,. The correct type is decided based on the parameters of the stated
command in the statement.

Each statement follows this rough synopsis
```
<statement>: <cmd_name> [ "<string>" | <integer> | <float> ] ...
```


## 2.3 GUI
The GUI defines a new type of panels. Each one of them has the required controls
to allow the user to resize, close, or duplicate it. Any panel can be duplicated
either vertically or horizontally.
```
.----------.                                   .-----------.
|          |                                   |     |     |
|          |   -- duplicated vertically -->    |     |     |
|          |                                   |     |     |
`----------`                                   `-----------`

.----------.                                   .-----------.
|          |                                   |           |
|          |   -- duplicated horizontally -->  |-----------|
|          |                                   |           |
`----------`                                   `-----------`
```
The reason you might want to duplicate panels is to add new panels of different
types to the screen. Although the new panels initially display the content
displayed by the older ones, every panel has a dropdown menu to select what
content it should display. So after you duplicate a panel, click the dropdown
menu and select the content you want. This content can be either an image, a
command, or a terminal.

### 2.3.1 Image panels
Image panels are assigned a matrix Id to display its content as an image. The
panel is updated once the image is updated by the engine. The user can select the
image id from a dropdown menu or enter it manually if it doesn't exist.

### 2.3.2 Terminal panels
A terminal is simply a graphical interface to the interactive mode of the
interpreter. This is the only panel shown on the screen when the user starts the
GUI. It displays 2 GUI components to the user, a text field where the user writes
the  statements to be executed and a text area that shows the result of each command.

### 2.3.3 Command panels
A command panel is a graphical representation of a command recognized by the
engine. The user selects the requested command from a dropdown menu. 
The command panel then inspects the requested command and shows the necessary inputs 
and controls to set its arguments. Here is how the different arguments are displayed

1. STR: textfield
1. SYS_PATH: file chooser 
2. MAT_ID: dropdown menu (or manually enter matrix Id)
4. INT with min=0, max=1: checkbox
5. INT with small ranges (3, 10): slider
6. INT with larger ranges: textfield (accepts integers only)
7. FLOAT: textfield (accepts floats only)
8. CMD_ID: dropdown menu

When the user finishes entering the correct arguments, he can click the
'execute' which is available on every command panel.