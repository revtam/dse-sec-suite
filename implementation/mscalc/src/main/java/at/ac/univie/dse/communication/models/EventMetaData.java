package at.ac.univie.dse.communication.models;

import java.io.Serializable;

public class EventMetaData implements Serializable {
    private String eventName;
    private String eventTarget;

    public EventMetaData(String eventName, String eventTarget) {
        this.eventName = eventName;
        this.eventTarget = eventTarget;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventTarget() {
        return eventTarget;
    }
}
