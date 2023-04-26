package at.ac.univie.dse.utilities.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationProperties implements ConfigurationPropertiesProtocol {
    private Properties properties = new Properties();

    public ConfigurationProperties() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("configuration.properties");
        properties.load(inputStream);
        inputStream.close();
    }

    public Integer getQueueTasksLimit() {
        return Integer.parseInt(properties.getProperty("queueTasksLimit"));
    }
    public Integer getIdleTerminationDelay() {
        return Integer.parseInt(properties.getProperty("idleTerminationDelay"));
    }
}
