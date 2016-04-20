package engine;


import commands.AddImage;
import commands.Man;
import commands.autoHistogram;
import commands.imshow;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    String SavingName = null;
    HashMap<String,Object>GenericMemory = new HashMap<>();
    HashMap<String,BufferedImage>IMGS = new HashMap<>();
   private final Parameter parameters = new Parameter();
   private final Methods methods = new Methods();
    private static Engine instance;
    private final List<Class> classes = new ArrayList<>();
    private final List<Method[]> Methods = new ArrayList<>();
    HashMap <String,Method> MethodsByName = new HashMap<>();
    public static Engine getInstance() {
	if(instance == null)
	    instance = new Engine();
	
	return instance;
    }
    
    public Engine()
    {
        getClasses("C:\\opencv\\build\\java\\opencv-249.jar");
    }
    
    private  void getClasses(String path)
    {
        try {
            JarFile jarFile = new JarFile(path);
            Enumeration allEntries = jarFile.entries();
            while (allEntries.hasMoreElements())
            {
                JarEntry entry = (JarEntry) allEntries.nextElement();
                String name = entry.getName();
                if(name.endsWith(".class"))
                {
                    name = name.substring(0,(name.lastIndexOf(".")+1));
                    name = name.replace(".", "");
                    name = name.replace("/", ".");
                    Class c = Class.forName(name);
                    classes.add(c);
                }
            }
            classes.add(AddImage.class);
            classes.add(autoHistogram.class);
            classes.add(imshow.class);
            classes.add(Man.class);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //for argumentEditor
    public HashMap<String, Class> getAllClasses()
    {
        HashMap<String,Class> clazzes = new HashMap<>();
        for (Class clazz : classes) {
            clazzes.put(clazz.getName(), clazz);
        }
        return clazzes;
    }
    //for argument editor
    public HashMap<String, Method> getClassMethods(Class clazz)
    {
        HashMap<String,Method> classMethods = new HashMap<>();
        
        Method []PublicMethods = methods.getAllMethods(clazz);
        for (Method aMethod : PublicMethods) {
            String key = aMethod.getName()+" : "+aMethod.getParameterCount();
            classMethods.put(key, aMethod);
        }
        return classMethods;
    }
    
    public  List<Method> getMethod(String NeededmethodName)
    {
        List<Method>returnValues=new ArrayList<>();
        
        Method method = null;
        for(int i=0;i<classes.size();i++)
        {
            Class clazz=classes.get(i);
            Method[] allMethods=methods.getAllMethods(clazz);
            
            for (Method allMethod : allMethods) {
                
                String MethodName = methods.getMethodName(allMethod);
                java.lang.reflect.Parameter[] allParameters = parameters.getParameters(allMethod);
                Class[]parameterTypes=parameters.getParamTypes(allMethod);
                
                method=checkMethod(clazz,MethodName,NeededmethodName,parameterTypes);
                
                if(method!=null)
                {
                    putMethodsByName(MethodName, method);
                    returnValues.add(method);
                }
            }
        }
        return returnValues;
    }
    
    private  Method checkMethod(Class aclass,String methodname,String TargetedMethodName,Class[]parameter)
    {
        Method correctMethod = null;
        if(methodname.equals(TargetedMethodName))
        {
            try 
            {
                correctMethod=aclass.getMethod(TargetedMethodName,parameter);
            } 
            catch (NoSuchMethodException | SecurityException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return correctMethod;
    }
    
    public void setSavingName(String Name)
    {
        SavingName = Name;
    }
    public String getSavingName()
    {
        return SavingName;
    }
    
     public void allocObject(String ObjectName, Object object) {
	GenericMemory.put(ObjectName, object);
    }
    
     public void dellocObject(String ObjectName) {
	GenericMemory.remove(ObjectName);
    }
     
     public String[] getObjectNames() {
	return GenericMemory.keySet().toArray(new String[]{});
    }
     
     public Object[] getObjects() {
	return GenericMemory.values().toArray(new Object[]{});
    }
     
     public Object getObject(String ObjectName) {
	return GenericMemory.get(ObjectName);
    }
     
     public int getObjectsCount() {
	return GenericMemory.size();
    }
     
     
     public boolean contains(String key) {
        return GenericMemory.containsKey(key);
    }
     
     
     public String[] getImagesNames() {
	return IMGS.keySet().toArray(new String[]{});
    }
     
     public Image[] getImages() {
	return IMGS.values().toArray(new Image[]{});
    }

    // gets an image by name
    public BufferedImage getImage(String imageName) {
	return IMGS.get(imageName);
    }
   
    public int getImagesCount() {
	return IMGS.size();
    }
    
    //puts an image into memory
    public void allocImage(String imageName, BufferedImage image) {
	IMGS.put(imageName, image);
    }    
    // removes an image from memory
    public void deallocImage(String imageName) {
	IMGS.remove(imageName);
    }
    
    public Method getMethodsByName(String MethodName) {
	return MethodsByName.get(MethodName);
    }
    
    public void putMethodsByName(String MethodName,Method method) {
        int i=0;
        if(MethodsByName.containsKey(MethodName))
        {
            i++;
            MethodName =i+""+ MethodName;
            
        }
	MethodsByName.put(MethodName, method);
    }
    public String[] getMethodNames() {
        int i=0;
        String[] methodNames = new String[MethodsByName.size()];
        for (Map.Entry<String, Method> entrySet : MethodsByName.entrySet()) {
            String key = entrySet.getKey();
            Object value = entrySet.getValue();
            methodNames[i] = key;
            i++;
        }
        return methodNames;
    }

}   