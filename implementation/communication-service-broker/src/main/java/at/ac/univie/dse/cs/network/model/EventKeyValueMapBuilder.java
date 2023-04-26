package at.ac.univie.dse.cs.network.model;

import java.util.HashMap;
import java.util.Map;


public class EventKeyValueMapBuilder {

    public final Map<String,String> entries;


    public EventKeyValueMapBuilder(){
        this.entries = new HashMap<>();
    }

    public EventKeyValueMapBuilder(Map<String,String> map){
        this.entries = map;
    }


    public EventKeyValueMapBuilder withEventName(String eventName){
        this.entries.put(Static.EVENT_NAME_KEY,eventName);
        return this;
    }

    public EventKeyValueMapBuilder withSenderIdentifier(String identifier){
        this.entries.put(Static.SENDER_IDENTIFIER,identifier);
        return this;
    }

    public EventKeyValueMapBuilder withEventType(String eventType){
        this.entries.put(Static.EVENT_TYPE,eventType);
        return this;
    }

    public EventKeyValueMapBuilder withPublishTarget(String publishTarget){
        this.entries.put(Static.PUBLISH_TARGET_KEY,publishTarget);
        return this;
    }

    public EventKeyValueMapBuilder withPayLoad(String payload){
        this.entries.put(Static.PAYLOAD_KEY,payload);
        return this;
    }

    public EventKeyValueMap build(){
        return new EventKeyValueMap(this.entries);
    }

}
