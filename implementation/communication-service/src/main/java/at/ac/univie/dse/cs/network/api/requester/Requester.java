package at.ac.univie.dse.cs.network.api.requester;

import at.ac.univie.dse.cs.network.exceptions.NetworkException;
import at.ac.univie.dse.cs.network.model.EventType;

import java.io.IOException;
import java.util.Map;

public interface Requester {

     <T> void request(T object, String eventName, String publishTarget, EventType eventType) throws NetworkException, IOException;
     void request(Map<String, String> data) throws NetworkException, IOException;
}
