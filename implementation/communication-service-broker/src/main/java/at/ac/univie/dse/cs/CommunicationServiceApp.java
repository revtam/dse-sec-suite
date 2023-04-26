/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package at.ac.univie.dse.cs;

import at.ac.univie.dse.cs.starter.BrokerApplicationStarter;
import at.ac.univie.dse.cs.properties.NetworkProtocol;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@PropertySource(value={"classpath:http-api.properties"}, ignoreResourceNotFound = true)
public class CommunicationServiceApp {

    public static void main(String[] args) {
        Set<NetworkProtocol> brokerProtocols = Arrays.stream(args)
                .map(NetworkProtocol::valueOf)
                .collect(Collectors.toSet());
        BrokerApplicationStarter.run(brokerProtocols);
    }
}