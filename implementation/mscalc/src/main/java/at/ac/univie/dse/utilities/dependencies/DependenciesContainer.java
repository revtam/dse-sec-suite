package at.ac.univie.dse.utilities.dependencies;

import at.ac.univie.dse.communication.EventsCommunicator;
import at.ac.univie.dse.communication.interfaces.EventsCommunicatorProtocol;
import at.ac.univie.dse.utilities.configuration.ConfigurationProperties;
import at.ac.univie.dse.utilities.configuration.ConfigurationPropertiesProtocol;

import java.io.IOException;

public class DependenciesContainer implements DependenciesContainerProtocol {
    private EventsCommunicatorProtocol eventsCommunicator;
    private ConfigurationPropertiesProtocol configurationProperties;

    @Override
    public EventsCommunicatorProtocol getEventsCommunicatorProtocol() {
        if(eventsCommunicator == null) {
            eventsCommunicator = new EventsCommunicator();
        }
        return eventsCommunicator;
    }

    @Override
    public ConfigurationPropertiesProtocol getConfigurationPropertiesProtocol() {
        if(configurationProperties == null) {
            try {
                configurationProperties = new ConfigurationProperties();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  configurationProperties;
    }
}
