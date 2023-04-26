package at.ac.univie.dse.cs.starter;

import at.ac.univie.dse.cs.ms.publishagent.PublishTemplate;
import at.ac.univie.dse.cs.network.api.invoker.InvocationEntry;
import at.ac.univie.dse.cs.network.api.invoker.Invoker;
import at.ac.univie.dse.cs.network.api.marshaller.JSONStringToObjectUnmarshaller;
import at.ac.univie.dse.cs.network.api.marshaller.StringToObjectUnmarshaller;
import at.ac.univie.dse.cs.network.api.requester.Requester;
import at.ac.univie.dse.cs.network.api.udp.server.handler.UdpServerRequestHandler;
import at.ac.univie.dse.cs.network.api.udp.server.invoker.UDPServiceInvoker;
import at.ac.univie.dse.cs.properties.NetworkProtocol;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceApplicationStarter extends ApplicationStarter{

    public static PublishTemplate run(NetworkProtocol networkProtocol, Set<InvocationEntry> invocationEntries){
        if(networkProtocol == NetworkProtocol.UDP){
            Requester requester = buildUDPRequester();
            PublishTemplate publishTemplate = new PublishTemplate(requester);
            StringToObjectUnmarshaller stringToObjectUnmarshaller = new JSONStringToObjectUnmarshaller(buildObjectMapper());
            Invoker invoker = new UDPServiceInvoker(stringToObjectUnmarshaller, invocationEntries);
            UdpServerRequestHandler serverRequestHandler = buildUDPServerRequestHandler(invoker);
            new Thread(serverRequestHandler).start();
            sendSubscriptionRequests(publishTemplate,invocationEntries);
            return publishTemplate;
        }else if(networkProtocol == NetworkProtocol.RMI){

        }
        return null;
    }

    private static void sendSubscriptionRequests(PublishTemplate publishTemplate, Set<InvocationEntry> invocationEntries) {
        List<Map.Entry<String,String>> subscriptionRequest = invocationEntries.stream()
                .map(entry -> new AbstractMap.SimpleImmutableEntry<>(entry.getEventName(),entry.getPublishTarget()))
                .collect(Collectors.toList());
        publishTemplate.publishSubscriptionRequests(subscriptionRequest);
    }

}
