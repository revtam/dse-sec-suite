package at.ac.univie.dse.cs.network.api.http.dto;

import javax.validation.constraints.NotBlank;

public class PublishRequest {

    @NotBlank
    private String eventName;

    @NotBlank
    private String publishTarget;

    @NotBlank
    private String payload;

    public PublishRequest(String eventName, String publishTarget, String payload) {
        this.eventName = eventName;
        this.publishTarget = publishTarget;
        this.payload = payload;
    }

    public PublishRequest() {}

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public String getPublishTarget() {
        return publishTarget;
    }

    public void setPublishTarget(String publishTarget) {
        this.publishTarget = publishTarget;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
