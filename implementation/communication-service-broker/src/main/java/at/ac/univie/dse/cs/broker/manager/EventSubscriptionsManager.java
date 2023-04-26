package at.ac.univie.dse.cs.broker.manager;

import at.ac.univie.dse.cs.broker.exception.SubscriptionNotFoundException;
import at.ac.univie.dse.cs.broker.model.SubscriptionEntry;

import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EventSubscriptionsManager {

    private static final Logger LOGGER = Logger.getLogger(EventSubscriptionsManager.class.getName());
    private final Map<String, Set<SubscriptionEntry>> eventSubscriptionMap;
    private final Predicate<Map.Entry<SubscriptionEntry,String>> subscribersFilterPredicate;

    public EventSubscriptionsManager(Map<String, Set<SubscriptionEntry>> eventSubscriptionMap, Predicate<Map.Entry<SubscriptionEntry, String>> subscribersFilterPredicate) {
        this.eventSubscriptionMap = eventSubscriptionMap;
        this.subscribersFilterPredicate = subscribersFilterPredicate;
    }

    public void subscribe(SubscriptionEntry subscriptionEntry){
        Set<SubscriptionEntry> subscribers = this.eventSubscriptionMap
                .getOrDefault(subscriptionEntry.getEventName(),emptySet());
        subscribers.add(subscriptionEntry);
        this.eventSubscriptionMap.put(subscriptionEntry.getEventName(),subscribers);
        logCurrentSubscribers();

    }

    public void unSubscribe(String eventName,String subscriberIdentifier) {
        Set<SubscriptionEntry> subscribers =  this.eventSubscriptionMap.getOrDefault(eventName,emptySet());
        SubscriptionEntry subscription = subscribers
                .stream()
                .filter(subscriptionEntry -> subscriptionEntry.getSubscriberIdentifier().equals(subscriberIdentifier))
                .findAny()
                .orElseThrow(() -> new SubscriptionNotFoundException("No Subscription was found for: ".concat(eventName).concat(" , ").concat(subscriberIdentifier)));
        subscribers.remove(subscription);
        this.eventSubscriptionMap.put(eventName,subscribers);
        logCurrentSubscribers();
    }

    public Set<SubscriptionEntry> getSubscribersByEventNameAndPublishTarget(String eventName, String publishTarget){
        return this.eventSubscriptionMap.getOrDefault(eventName,emptySet()).stream()
                .map(subscriptionEntry -> new AbstractMap.SimpleEntry<SubscriptionEntry,String>(subscriptionEntry,publishTarget))
                .filter(this.subscribersFilterPredicate)
                .map(AbstractMap.SimpleEntry::getKey)
                .collect(Collectors.toSet());
    }

    private Set<SubscriptionEntry> emptySet(){
        return Collections.synchronizedSet(new HashSet<>());
    }

    private void logCurrentSubscribers() {
        LOGGER.info("Current Subscribers: ");
        this.eventSubscriptionMap.entrySet().stream()
                .forEach(entry -> {
                    LOGGER.info("Event name: " + entry.getKey());
                    entry.getValue().stream().forEach(sEntry -> {
                        LOGGER.info("subscriber: "+ sEntry);
                    });
                });
    }

}
