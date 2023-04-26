package at.ac.univie.dse.cs.network.api.invoker;

import java.util.function.Consumer;

public class InvocationEntry {

    private final String eventName;
    private final String publishTarget;//unique
    private final String className;
    private final Consumer<Object> onReceivedPublishEventListener;

    public InvocationEntry(String eventName, String publishTarget, String className, Consumer<Object> onReceivedPublishEventListener) {
        this.eventName = eventName;
        this.publishTarget = publishTarget;
        this.className = className;
        this.onReceivedPublishEventListener = onReceivedPublishEventListener;
    }

    public String getPublishTarget() {
        return publishTarget;
    }

    public String getEventName() {
        return eventName;
    }

    public String getClassName() {
        return className;
    }

    public Consumer<Object> getOnReceivedPublishEventListener() {
        return onReceivedPublishEventListener;
    }
}
