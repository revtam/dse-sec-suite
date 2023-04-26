package at.ac.univie.dse.cs.network.model;


import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class EventKeyValueMap {

    private final Map<String,String> eventData;

    public EventKeyValueMap(Map<String, String> map){
        this.eventData = Collections.unmodifiableMap(map);
    }

    public int getIntValue(String key){
        return Integer.parseInt(getValue(key));
    }

    public String getValue(String key){
        return this.eventData.get(key);
    }

    public Map<String, String> getEventData() {
        return this.eventData;
    }

    @Override
    public String toString() {
        return this.eventData.entrySet().stream()
                .map(entry -> entry.getKey().concat(" - ").concat(entry.getValue()).concat(System.lineSeparator()))
                .collect(Collectors.joining());
    }
}
