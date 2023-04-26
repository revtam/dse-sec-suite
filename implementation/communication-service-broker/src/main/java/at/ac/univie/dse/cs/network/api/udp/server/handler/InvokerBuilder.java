package at.ac.univie.dse.cs.network.api.udp.server.handler;

import at.ac.univie.dse.cs.broker.controller.ReceivedEventsController;
import at.ac.univie.dse.cs.network.api.invoker.Invoker;
import at.ac.univie.dse.cs.network.api.services.PublishedEventListener;
import at.ac.univie.dse.cs.network.api.services.SubscriberPingingService;
import at.ac.univie.dse.cs.network.api.udp.server.invoker.UDPBrokerInvoker;

public class InvokerBuilder {

    public static Invoker buildUDPBrokerInvoker(PublishedEventListener publishedEventListener,
                                                ReceivedEventsController receivedEventsController,
                                                SubscriberPingingService subscriberPingingService){
        return new UDPBrokerInvoker(publishedEventListener,receivedEventsController,subscriberPingingService);
    }
}
