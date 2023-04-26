package at.ac.univie.dse.cs.network.api.udp.server.invoker;

import at.ac.univie.dse.cs.broker.controller.ReceivedEventsController;
import at.ac.univie.dse.cs.network.model.Static;
import at.ac.univie.dse.cs.network.api.services.SubscriberPingingService;
import at.ac.univie.dse.cs.network.api.invoker.Invoker;
import at.ac.univie.dse.cs.network.api.services.PublishedEventListener;
import at.ac.univie.dse.cs.network.model.EventType;

import java.util.Map;

public class UDPBrokerInvoker implements Invoker {

    private final ReceivedEventsController receivedEventsController;
    private final SubscriberPingingService subscriberPingingService;
    private final PublishedEventListener publishedEventListener;

    public UDPBrokerInvoker(PublishedEventListener publishedEventListener,
                            ReceivedEventsController receivedEventsController,
                            SubscriberPingingService subscriberPingingService) {
        this.publishedEventListener = publishedEventListener;
        this.receivedEventsController = receivedEventsController;
        this.subscriberPingingService = subscriberPingingService;
    }

    @Override
    public void invoke(Map<String, String> data) {
        EventType eventType = getEventType(data);
        switch (eventType){
            case PUBLISH: invokePublish(data); break;
            case SUBSCRIBE: invokeSubscribe(data);break;
            case UNSUBSCRIBE: invokeUnsubscribe(data);break;
        }
    }

    private void invokeUnsubscribe(Map<String, String> data) {
        final var eventName = data.get(Static.EVENT_NAME_KEY);
        final var subscriberIdentifier = data.get(Static.SENDER_IDENTIFIER);
        this.receivedEventsController.unSubscribe(eventName,subscriberIdentifier,data);
    }

    private void invokePublish(Map<String, String> data) {
        var eventName = data.get(Static.EVENT_NAME_KEY);
        var publishTarget = data.get(Static.PUBLISH_TARGET_KEY);
        var payLoad = data.get(Static.PAYLOAD_KEY);
        this.receivedEventsController.publish(eventName,publishTarget,payLoad,data);
    }

    private void invokeSubscribe(Map<String, String> data) {
        final var eventName = data.get(Static.EVENT_NAME_KEY);
        final var publishTarget = data.get(Static.PUBLISH_TARGET_KEY);
        final var subscriberIdentifier = data.get(Static.SENDER_IDENTIFIER);
        this.receivedEventsController.subscribe(eventName,publishTarget,subscriberIdentifier,data,this.publishedEventListener,this.subscriberPingingService);
    }

    private EventType getEventType(Map<String, String> data) {
        String eventTypeString = data.get(Static.EVENT_TYPE);
        return EventType.valueOf(eventTypeString);
    }
}
