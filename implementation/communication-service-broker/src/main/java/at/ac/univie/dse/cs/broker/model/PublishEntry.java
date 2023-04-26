package at.ac.univie.dse.cs.broker.model;

import java.util.Map;

public class PublishEntry {

    private final String eventName;
    private final String publishTarget;
    private final String payLoad;
    private final Map<String, String> data;


    public PublishEntry(String eventName, String publishTarget, String payLoad, Map<String, String> data) {
        this.eventName = eventName;
        this.publishTarget = publishTarget;
        this.payLoad = payLoad;
        this.data = data;
    }

    public String getEventName() {
        return eventName;
    }

    public String getPublishTarget() {
        return publishTarget;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public Map<String, String> getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "PublishEntry{" +
                "eventName='" + eventName + '\'' +
                ", publishTarget='" + publishTarget + '\'' +
                ", payLoad='" + payLoad + '\'' +
                ", data=" + data +
                '}';
    }
}
