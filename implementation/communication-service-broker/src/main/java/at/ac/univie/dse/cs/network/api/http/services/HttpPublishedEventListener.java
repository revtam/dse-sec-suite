package at.ac.univie.dse.cs.network.api.http.services;

import at.ac.univie.dse.cs.network.api.http.model.SubscriberPendingResponseKey;
import at.ac.univie.dse.cs.network.api.services.PublishedEventListener;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;
import at.ac.univie.dse.cs.network.model.Static;

import java.io.IOException;
import java.util.Map;

public class HttpPublishedEventListener implements PublishedEventListener {

    private final PendingSubscriptionsResponsesService pendingSubscriptionsResponsesService;

    public HttpPublishedEventListener(PendingSubscriptionsResponsesService pendingSubscriptionsResponsesService) {
        this.pendingSubscriptionsResponsesService = pendingSubscriptionsResponsesService;
    }

    @Override
    public void accept(Map<String, String> data, String payLoad) throws NetworkException, IOException {
        final String eventName = data.get(Static.EVENT_NAME_KEY);
        final String publishTarget = data.get(Static.PUBLISH_TARGET_KEY);
        final String senderIdentifier = data.get(Static.SENDER_IDENTIFIER);
        SubscriberPendingResponseKey subscriberPendingResponseKey = new SubscriberPendingResponseKey(eventName,senderIdentifier,publishTarget);
        this.pendingSubscriptionsResponsesService.addPendingSubscription(subscriberPendingResponseKey,payLoad);
    }
}
