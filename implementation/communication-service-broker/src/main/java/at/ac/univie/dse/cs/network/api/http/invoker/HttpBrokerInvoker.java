package at.ac.univie.dse.cs.network.api.http.invoker;

import at.ac.univie.dse.cs.broker.controller.ReceivedEventsController;
import at.ac.univie.dse.cs.network.api.http.server.HttpServerRequestHandler;
import at.ac.univie.dse.cs.network.api.invoker.Invoker;
import at.ac.univie.dse.cs.network.api.services.PublishedEventListener;
import at.ac.univie.dse.cs.network.api.services.SubscriberPingingService;
import at.ac.univie.dse.cs.network.model.EventType;
import at.ac.univie.dse.cs.network.model.Static;

import java.util.Map;
import java.util.logging.Logger;

public class HttpBrokerInvoker implements Invoker {

    private static final Logger LOGGER = Logger.getLogger(HttpBrokerInvoker.class.getName());
    private final ReceivedEventsController receivedEventsController;
    private final SubscriberPingingService subscriberPingingService;
    private final PublishedEventListener publishedEventListener;

    public HttpBrokerInvoker(PublishedEventListener publishedEventListener,
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
            case UNSUBSCRIBE: invokeUnsubscribe(data); break;
        }
    }

    private void invokeUnsubscribe(Map<String, String> data) {
        final var eventName = data.get(Static.EVENT_NAME_KEY);
        final var subscriberIdentifier = data.get(Static.SENDER_IDENTIFIER);
        this.receivedEventsController.unSubscribe(eventName,subscriberIdentifier,data);
    }

    private void invokePublish(Map<String, String> data) {
        final var eventName = data.get(Static.EVENT_NAME_KEY);
        final var publishTarget = data.get(Static.PUBLISH_TARGET_KEY);
        final var payLoad = data.get(Static.PAYLOAD_KEY);
        LOGGER.info("Invoking ReceivedEventsController with:");
        LOGGER.info("eventName: "+eventName);
        LOGGER.info("publishTarget: "+publishTarget);
        LOGGER.info("payLoad: "+payLoad);
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
