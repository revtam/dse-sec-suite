package at.ac.univie.dse.cs.broker.controller;

import at.ac.univie.dse.cs.network.api.services.PublishedEventListener;
import at.ac.univie.dse.cs.network.api.services.SubscriberPingingService;

import java.util.Map;

public interface ReceivedEventsController {

    void publish(String eventName,String publishTarget, String payLoad,Map<String,String> data);
    void subscribe(String eventName, String publishTarget,String subscriberIdentifier, Map<String, String> data, PublishedEventListener publishedEventListener, SubscriberPingingService subscriberPingingService);
    void unSubscribe(String eventName, String subscriberIdentifier,Map<String,String> data);

}
