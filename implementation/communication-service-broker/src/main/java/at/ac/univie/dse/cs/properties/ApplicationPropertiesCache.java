package at.ac.univie.dse.cs.properties;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ApplicationPropertiesCache {

    private static final Logger log = Logger.getLogger(ApplicationPropertiesCache.class.getName());
    private static final ApplicationPropertiesCache PROPERTIES_CACHE = new ApplicationPropertiesCache();
    private final Properties properties;

    private ApplicationPropertiesCache(){
        this.properties = new Properties();
        try {
            Properties userProperties = new Properties();
            InputStream baseIn = this.getClass().getClassLoader().getResourceAsStream("base.properties");
            this.properties.load(baseIn);
            baseIn.close();
            InputStream applicationIn = this.getClass().getClassLoader().getResourceAsStream("application.properties");
            if(applicationIn != null){
                userProperties.load(applicationIn);
                this.properties.putAll(userProperties);
                applicationIn.close();
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("The application is running with the following configurations");
        for(var key : this.properties.keySet()){
            log.info(key.toString().concat(":   ").concat(this.properties.getProperty((String) key)));
        }
    }

    public static ApplicationPropertiesCache getInstance(){
        return PROPERTIES_CACHE;
    }
    
    public int getIntProperty(String key){
        return Integer.parseInt(getProperty(key));
    }

    public String getProperty(String key){
        return this.properties.getProperty(key);
    }

    public boolean containsKey(String key){
        return this.properties.containsKey(key);
    }

    public void print() {
        properties.keySet().forEach(System.out::println);
    }
}

