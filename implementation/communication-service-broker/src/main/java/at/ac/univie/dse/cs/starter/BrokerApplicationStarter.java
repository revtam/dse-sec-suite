package at.ac.univie.dse.cs.starter;

import at.ac.univie.dse.cs.CommunicationServiceApp;
import at.ac.univie.dse.cs.broker.builder.BrokerBuilder;
import at.ac.univie.dse.cs.broker.controller.ReceivedEventsController;
import at.ac.univie.dse.cs.broker.manager.ActiveSubscribersMonitor;
import at.ac.univie.dse.cs.broker.manager.EventPublishManager;
import at.ac.univie.dse.cs.broker.manager.EventSubscriptionsManager;
import at.ac.univie.dse.cs.broker.model.SubscriptionEntry;
import at.ac.univie.dse.cs.network.api.invoker.Invoker;
import at.ac.univie.dse.cs.network.api.requester.Requester;
import at.ac.univie.dse.cs.network.api.services.PublishedEventListener;
import at.ac.univie.dse.cs.network.api.services.SubscriberPingingService;
import at.ac.univie.dse.cs.network.api.udp.server.handler.InvokerBuilder;
import at.ac.univie.dse.cs.network.api.udp.server.handler.UdpServerRequestHandler;
import at.ac.univie.dse.cs.network.api.udp.services.UDPPublishedEventListenerBuilder;
import at.ac.univie.dse.cs.network.api.udp.services.UDPSubscriberPingingService;
import at.ac.univie.dse.cs.properties.NetworkProtocol;
import at.ac.univie.dse.cs.properties.PropertiesKeys;
import org.springframework.boot.SpringApplication;

import java.util.Map;
import java.util.Set;

public class BrokerApplicationStarter extends ApplicationStarter{

    public static ReceivedEventsController receivedEventsController;

    public static void run(Set<NetworkProtocol> networkProtocols){
        int delay = applicationPropertiesCache.getIntProperty(PropertiesKeys.Broker.SUBSCRIBERS_MONITOR_DELAY);
        int period = applicationPropertiesCache.getIntProperty(PropertiesKeys.Broker.SUBSCRIBERS_MONITOR_PERIOD);
        int threads = applicationPropertiesCache.getIntProperty(PropertiesKeys.Broker.SUBSCRIBERS_MONITOR_THREADS);
        Map<String, Set<SubscriptionEntry>> eventSubscriptionMap = BrokerBuilder.buildEventSubscriptionMap();
        EventSubscriptionsManager eventSubscriptionsManager = BrokerBuilder.buildEventSubscriptionsManager(eventSubscriptionMap);
        EventPublishManager eventPublishManager = BrokerBuilder.buildEventPublishManager(eventSubscriptionsManager);
        receivedEventsController = BrokerBuilder.buildReceivedEventsController(eventPublishManager,eventSubscriptionsManager);
        ActiveSubscribersMonitor activeSubscribersMonitor = BrokerBuilder.buildActiveSubscribersMonitor(eventSubscriptionMap,delay,period,threads);
        bindNetworkProtocols(receivedEventsController,networkProtocols);
        new Thread(eventPublishManager).start();
        activeSubscribersMonitor.start();
    }

    private static void bindNetworkProtocols(ReceivedEventsController receivedEventsController, Set<NetworkProtocol> networkProtocols) {
        if(networkProtocols.contains(NetworkProtocol.UDP)){
            bindUDPProtocol(receivedEventsController);
        }
        if(networkProtocols.contains(NetworkProtocol.HTTP)){
            SpringApplication.run(CommunicationServiceApp.class);
        }
    }

    private static void bindUDPProtocol(ReceivedEventsController receivedEventsController) {
        String sourceIpAddress = applicationPropertiesCache.getProperty(PropertiesKeys.UDP.LISTENER_IP_ADDRESS);
        String sourcePort = applicationPropertiesCache.getProperty(PropertiesKeys.UDP.LISTENER_PORT);
        int sequenceNumberUpperBound = applicationPropertiesCache.getIntProperty(PropertiesKeys.UDP.PACKET_SEQUENCE_BOUND);
        Requester requester = buildUDPRequester();
        PublishedEventListener publishedEventListener = UDPPublishedEventListenerBuilder.build(requester,sourceIpAddress,sourcePort,sequenceNumberUpperBound);
        SubscriberPingingService subscriberPingingService = new UDPSubscriberPingingService();
        Invoker invoker = InvokerBuilder.buildUDPBrokerInvoker(publishedEventListener, receivedEventsController,subscriberPingingService);
        UdpServerRequestHandler serverRequestHandler = buildUDPServerRequestHandler(invoker);
        new Thread(serverRequestHandler).start();
    }

}
