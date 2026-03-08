package plugin.manager;

import interfacePlugin.IReportPlugin;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentPluginManager {
    
    private static final String FILE_NAME = "plugin.properties";
    private static AppointmentPluginManager instance;
    
    private Properties pluginProperties; 
    
    private AppointmentPluginManager(){
        pluginProperties = new Properties();
    }
    
    public static AppointmentPluginManager getInstance(){
        return instance;
    }
    
    public static void init(String basePath) throws Exception{
    
        instance = new AppointmentPluginManager();
        instance.loadProperties(basePath);
        if(instance.pluginProperties.isEmpty()){
            throw new Exception("Couldn't initialize plugins");
        }
    }
    
    public IReportPlugin getReportPlugin(String typeReport){
        
        String propertyName = "report." + typeReport.toLowerCase();
        if(!pluginProperties.containsKey(propertyName)){
            return null;
        }
        
        IReportPlugin plugin = null;
        
        String pluginClassName = pluginProperties.getProperty(propertyName);
        
        try{
            
            Class<?> pluginClass = Class.forName(pluginClassName);
            if(pluginClass != null){
                
                Object pluginObject = pluginClass.getDeclaredConstructor().newInstance();
                
                if(pluginObject instanceof IReportPlugin){
                    plugin = (IReportPlugin) pluginObject;
                }
            }
        }catch(ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex){
            Logger.getLogger("AppointmentPluginManager").log(Level.SEVERE, "Error al ejecutar la aplicacion", ex);
        }
        
        return plugin;
    }
        
    private void loadProperties(String basePath){
    
        String filePath = basePath + FILE_NAME;
        
        try(FileInputStream stream = new FileInputStream(filePath)){
            pluginProperties.load(stream);
        }catch(IOException ex){
            Logger.getLogger("AppointmentManager").log(Level.SEVERE, "Error al ejecutar la aplicacion", ex);
        }
    }
 

}
