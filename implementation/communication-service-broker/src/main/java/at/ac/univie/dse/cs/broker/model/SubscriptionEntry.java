package at.ac.univie.dse.cs.broker.model;

import at.ac.univie.dse.cs.network.api.services.PublishedEventListener;
import at.ac.univie.dse.cs.network.api.services.SubscriberPingingService;

import java.util.Map;
import java.util.Objects;

public class SubscriptionEntry {

    private final String eventName;
    private final String publishTarget;
    private final String subscriberIdentifier;
    private final Map<String, String> data;
    private final PublishedEventListener publishedEventListener;
    private final SubscriberPingingService subscriberPingingService;

    public SubscriptionEntry(String eventName,
                             String publishTarget,
                             String subscriberIdentifier,
                             Map<String, String> data,
                             PublishedEventListener publishedEventListener,
                             SubscriberPingingService subscriberPingingService) {
        this.eventName = eventName;
        this.publishTarget = publishTarget;
        this.subscriberIdentifier = subscriberIdentifier;
        this.data = data;
        this.publishedEventListener = publishedEventListener;
        this.subscriberPingingService = subscriberPingingService;
    }

    public String getSubscriberIdentifier() {
        return subscriberIdentifier;
    }

    public SubscriberPingingService getSubscriberPingingService() {
        return subscriberPingingService;
    }

    public Map<String, String> getData() {
        return data;
    }

    public String getEventName() {
        return eventName;
    }

    public String getPublishTarget() {
        return publishTarget;
    }

    public PublishedEventListener getPublishedEventListener() {
        return publishedEventListener;
    }

    public Boolean applySubscriberPingingService() {
        return this.subscriberPingingService.pingSubscriber(this.data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionEntry that = (SubscriptionEntry) o;
        return subscriberIdentifier.equals(that.subscriberIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriberIdentifier);
    }

    @Override
    public String toString() {
        return "SubscriptionEntry{" +
                "eventName='" + eventName + '\'' +
                ", publishTarget='" + publishTarget + '\'' +
                ", subscriberIdentifier='" + subscriberIdentifier + '\'' +
                ", data=" + data +
                ", publishedEventListener=" + publishedEventListener +
                ", subscriberPingingService=" + subscriberPingingService +
                '}';
    }

    public static class SubscriptionEntryBuilder {

        private Map<String, String> data;
        private PublishedEventListener publishedEventListener;
        private SubscriberPingingService subscriberPingingService;
        private String eventName;
        private String publishTarget;
        private String subscriberIdentifier;

        public SubscriptionEntryBuilder withData(Map<String, String> data){
            this.data = data;
            return this;
        }

        public SubscriptionEntryBuilder withEventName(String eventName){
            this.eventName = eventName;
            return this;
        }
        public SubscriptionEntryBuilder withPublishTarget(String publishTarget){
            this.publishTarget = publishTarget;
            return this;
        }

        public SubscriptionEntryBuilder withPublishedEventListener(PublishedEventListener publishedEventListener){
            this.publishedEventListener = publishedEventListener;
            return this;
        }

        public SubscriptionEntryBuilder withSubscriberPingingService(SubscriberPingingService subscriberPingingService){
            this.subscriberPingingService = subscriberPingingService;
            return this;
        }

        public SubscriptionEntryBuilder withSubscriberIdentifier(String subscriberIdentifier){
            this.subscriberIdentifier = subscriberIdentifier;
            return this;
        }

        public SubscriptionEntry build(){
            if(this.data != null && this.subscriberPingingService != null && this.publishedEventListener != null &&
                    this.eventName != null && this.publishTarget != null){
                return new SubscriptionEntry(this.eventName,this.publishTarget, this.subscriberIdentifier, this.data, this.publishedEventListener, this.subscriberPingingService);
            }
            throw new IllegalArgumentException(BrokerStatic.ILLEGAL_SUBSCRIPTION_ENTRY_ARGS);
        }
    }
}
