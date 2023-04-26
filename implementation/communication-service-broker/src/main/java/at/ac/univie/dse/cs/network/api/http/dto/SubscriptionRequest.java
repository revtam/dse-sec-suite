package at.ac.univie.dse.cs.network.api.http.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SubscriptionRequest {

    @NotBlank
    private String eventName;

    @NotBlank
    private String identifier;

    @NotBlank
    private String publishTarget;

    public SubscriptionRequest() {}

    public SubscriptionRequest(String eventName, String identifier, String publishTarget) {
        this.eventName = eventName;
        this.identifier = identifier;
        this.publishTarget = publishTarget;
    }

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
