package at.ac.univie.dse.cs.network.api.http.model;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class SubscriberPendingResponseKey {


    @NotBlank
    private String eventName;

    @NotBlank
    private String identifier;

    @NotBlank
    private String publishTarget;

    public SubscriberPendingResponseKey(String eventName, String identifier, String publishTarget) {
        this.eventName = eventName;
        this.identifier = identifier;
        this.publishTarget = publishTarget;
    }

    public SubscriberPendingResponseKey() {}

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPublishTarget() {
        return publishTarget;
    }

    public void setPublishTarget(String publishTarget) {
        this.publishTarget = publishTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriberPendingResponseKey that = (SubscriberPendingResponseKey) o;
        return eventName.equals(that.eventName) && identifier.equals(that.identifier) && publishTarget.equals(that.publishTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, identifier, publishTarget);
    }
}
