package at.ac.univie.dse.cs.broker.controller;

import at.ac.univie.dse.cs.broker.manager.EventSubscriptionsManager;
import at.ac.univie.dse.cs.broker.model.SubscriptionEntry;
import at.ac.univie.dse.cs.broker.manager.EventPublishManager;
import at.ac.univie.dse.cs.broker.model.PublishEntry;
import at.ac.univie.dse.cs.network.api.services.PublishedEventListener;
import at.ac.univie.dse.cs.network.api.services.SubscriberPingingService;

import java.util.Map;

public class BrokerReceivedEventsController implements ReceivedEventsController{

    private final EventSubscriptionsManager eventSubscriptionManager;
    private final EventPublishManager eventPublishManager;

    public BrokerReceivedEventsController(EventSubscriptionsManager eventSubscriptionManager, EventPublishManager eventPublishManager) {
        this.eventSubscriptionManager = eventSubscriptionManager;
        this.eventPublishManager = eventPublishManager;
    }


    @Override
    public void publish(String eventName,String publishTarget, String payLoad,Map<String,String> data) {
        var publishEntry = new PublishEntry(eventName,publishTarget,payLoad,data);
        try {
            this.eventPublishManager.addPublishEntry(publishEntry);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(String eventName, String publishTarget,String subscriberIdentifier, Map<String, String> data, PublishedEventListener publishedEventListener, SubscriberPingingService subscriberPingingService) {
        var subscriptionEntry = buildSubscriptionEntry(eventName,publishTarget,subscriberIdentifier,data, publishedEventListener, subscriberPingingService);
        this.eventSubscriptionManager.subscribe(subscriptionEntry);
    }

    @Override
    public void unSubscribe(String eventName,String subscriberIdentifier, Map<String, String> data) {
        this.eventSubscriptionManager.unSubscribe(eventName,subscriberIdentifier);

    }

    private SubscriptionEntry buildSubscriptionEntry(String eventName, String publishTarget, String subscriberIdentifier, Map<String, String> data, PublishedEventListener publishedEventListener, SubscriberPingingService subscriberPingingService) {
        SubscriptionEntry.SubscriptionEntryBuilder subscriptionEntryBuilder = new SubscriptionEntry.SubscriptionEntryBuilder();
        return subscriptionEntryBuilder
                .withEventName(eventName)
                .withPublishTarget(publishTarget)
                .withSubscriberIdentifier(subscriberIdentifier)
                .withData(data)
                .withPublishedEventListener(publishedEventListener)
                .withSubscriberPingingService(subscriberPingingService)
                .build();
    }


}
