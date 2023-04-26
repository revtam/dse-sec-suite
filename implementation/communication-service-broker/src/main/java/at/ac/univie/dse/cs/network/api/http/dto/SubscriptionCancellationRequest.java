package at.ac.univie.dse.cs.network.api.http.dto;

public class SubscriptionCancellationRequest {

    private String eventName;
    private String identifier;
    private String publishTarget;

    public SubscriptionCancellationRequest() {}

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
}
