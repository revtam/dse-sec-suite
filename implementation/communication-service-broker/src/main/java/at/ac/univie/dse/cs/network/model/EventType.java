package at.ac.univie.dse.cs.network.model;

public enum EventType {

    PUBLISH("subscribe"),SUBSCRIBE("publish"),UNSUBSCRIBE("unsubscribe");

    private final String label;

    EventType(String label) {
        this.label = label;
    }

    public String getEventType() {
        return this.label;
    }

}
