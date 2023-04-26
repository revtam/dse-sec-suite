package at.ac.univie.dse.cs.broker.manager;

import at.ac.univie.dse.cs.broker.model.SubscriptionEntry;
import at.ac.univie.dse.cs.broker.model.PublishEntry;
import at.ac.univie.dse.cs.network.api.http.invoker.HttpBrokerInvoker;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class EventPublishManager implements Runnable{

    private static final Logger LOGGER = Logger.getLogger(EventPublishManager.class.getName());

    private final BlockingQueue<PublishEntry> pendingPublishEvents;
    private final EventSubscriptionsManager eventSubscriptionManager;


    public EventPublishManager(BlockingQueue<PublishEntry> pendingPublishEvents,
                               EventSubscriptionsManager eventSubscriptionManager) {
        this.pendingPublishEvents = pendingPublishEvents;
        this.eventSubscriptionManager = eventSubscriptionManager;
    }


    public void addPublishEntry(PublishEntry publishEntry) throws InterruptedException {
        LOGGER.info("adding to PendingPublishEvents: "+ publishEntry);
        this.pendingPublishEvents.put(publishEntry);

    }

    @Override
    public void run() {
        while(true){
            try {
                PublishEntry publishEntry = this.pendingPublishEvents.take();
                LOGGER.info("Publishing: "+publishEntry);
                Set<SubscriptionEntry> subscribers = this.eventSubscriptionManager.getSubscribersByEventNameAndPublishTarget(publishEntry.getEventName(),publishEntry.getPublishTarget());
                LOGGER.info("Publishing to : ");
                subscribers.forEach(subscriptionEntry -> {
                            LOGGER.info(subscriptionEntry.toString());
                        });
                for(var subscriber : subscribers){
                    var publishEventListener = subscriber.getPublishedEventListener();
                    publishEventListener.accept(subscriber.getData(),publishEntry.getPayLoad());
                }
            } catch (InterruptedException | IOException | NetworkException e) {
                e.printStackTrace();
            }
        }
    }
}
