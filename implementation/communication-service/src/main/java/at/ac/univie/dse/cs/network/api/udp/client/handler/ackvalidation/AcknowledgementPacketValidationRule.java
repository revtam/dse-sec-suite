package at.ac.univie.dse.cs.network.api.udp.client.handler.ackvalidation;

import at.ac.univie.dse.cs.network.model.EventKeyValueMap;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;

import java.net.InetAddress;

public interface AcknowledgementPacketValidationRule {
    
   void validate(InetAddress expectedSourceAddress, int expectedSourcePort, EventKeyValueMap sentEventKeyValueMap, EventKeyValueMap receivedEventKeyValueMap) throws NetworkException;
}
