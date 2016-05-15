package engine;

import commands.Customcommands;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * Responsible for loading commands and storing images.
 */
public final class Engine
{
    private static Engine instance;
    private HashMap<String,Object> Memory = new HashMap<>();
    private HashMap<String,BufferedImage> IMGS = new HashMap<>();
    private HashMap<String,Class> Classes = new HashMap<>();
    private HashMap<String,Method> Methods = new HashMap<>();
    private HashMap<String,Constructor> Constructors = new HashMap<>();
    private HashMap<String,Object>instances = new HashMap<>();
    public String save = null;
    public static Engine getInstance()
    {
        if(instance == null)
        {
            instance = new Engine();
        }
        return instance;
    }
    public Engine()
    {
        try {
            Classes("./lib/opencv-249.jar");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        removeUnecessaryClasses();
        Methods();
        Constructors();
    }
    private void Classes(String Path) throws IOException, ClassNotFoundException
    {
        JarFile jarFile = new JarFile(Path);
        Enumeration allEntries = jarFile.entries();
        while (allEntries.hasMoreElements())
        {
            JarEntry entry = (JarEntry) allEntries.nextElement();
            String name = entry.getName();
            if(name.endsWith(".java"))
            {
                name = name.substring(0,(name.lastIndexOf(".")+1));
                name = name.replace(".", "");
                name = name.replace("/", ".");
                Class clazz = Class.forName(name);
                String ClassName = clazz.getSimpleName();
                Classes.put(ClassName, clazz);
            }
            Classes.put(Customcommands.class.getSimpleName(),Customcommands.class);
            
        }
    }
    //todo :: remove unnecessary classez 
    private void removeUnecessaryClasses()
    {
        
    }
    private void Methods()
    {
        for (Map.Entry<String, Class> Clazz : Classes.entrySet()) {
                Class clazz = Clazz.getValue();
                Method[] Methodz = clazz.getDeclaredMethods();
                for(Method method : Methodz)
                {
                    //check if method is public
                    if(Modifier.isPublic(method.getModifiers()))
                    {
                        String MethodName = method.getName()+" :: "+method.getParameterCount();
                        Methods.put(MethodName, method);
                    }
                    
                }
                
            }
    }
    private void Constructors()
    {
        for (Map.Entry<String, Class> Clazz : Classes.entrySet())
        {
            Class clazz = Clazz.getValue();
            Constructor [] constructorz = clazz.getConstructors();
            for(Constructor constructor : constructorz)
            {
                String[]ConstructorNameData = constructor.getName().replace(".", " ").split(" ");
                String ConstructorName = ConstructorNameData[ConstructorNameData.length-1]+" :: "+constructor.getParameterCount();//todo :: check for paramater size and type
                
                Constructors.put(ConstructorName, constructor);
            }
        }
    }
    public TreeMap<String, Method> getMethod(Class clazz)
    {
        HashMap<String,Method> ClazzMethods = new HashMap<>();
        Method[] methods = clazz.getDeclaredMethods(); 
        for(Method method : methods)
        {
            //check if the method is public
            if(Modifier.isPublic(method.getModifiers()))
            {
                String MethodName = method.getName()+" :: "+method.getParameterCount();
            ClazzMethods.put(MethodName, method);
            }
            
        }
        return new TreeMap<>(ClazzMethods);
    }
    public void ExecuteMethod(Method method,Object[] args)
    {
        Object instance = null;
        Object result = null;
        if(instances.containsKey(method.getDeclaringClass().toString()))
        {
            instance = instances.get(method.getDeclaringClass().toString());
        }
        else
        {
            try {
                instance = method.getDeclaringClass().newInstance();
                instances.put(method.getDeclaringClass().toString(), instance);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
           result = method.invoke(instance, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(result != null && save != null)
        {
            addToMemory(save, result);
            save = null;
        }
    }
    
    public TreeMap<String, Method> getMethods()
    {
        return new TreeMap<>(Methods);
    }
    public TreeMap<String, Class> getClasses()
    {
        return new TreeMap<>(Classes);
    }
    public TreeMap<String,Constructor> getConstructors()
    {
        return new TreeMap<>(Constructors);
    }
    public void AddImage(String ImageName,BufferedImage image)
    {
        IMGS.put(ImageName, image);
    }
    public void removeImage(String ImageName)
    {
        IMGS.remove(ImageName);
    }
    public TreeMap<String,BufferedImage> getAllImages()
    {
        return new TreeMap<>(IMGS);
    }
    public boolean isInMemory(String ObjectName)
    {
        return Memory.containsKey(ObjectName);
    }
    public Object getFromMemory(String ObjectName)
    {
        return Memory.get(ObjectName);
    }
    public void addToMemory(String ObjectName,Object object)
    {
        Memory.put(ObjectName, object);
    }
} 