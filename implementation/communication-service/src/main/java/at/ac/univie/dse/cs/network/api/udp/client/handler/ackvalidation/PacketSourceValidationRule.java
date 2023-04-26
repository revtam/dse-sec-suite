package at.ac.univie.dse.cs.network.api.udp.client.handler.ackvalidation;

import at.ac.univie.dse.cs.network.api.udp.config.UDPStatic;
import at.ac.univie.dse.cs.network.api.udp.exceptions.UnexpectedSourceException;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;
import at.ac.univie.dse.cs.network.model.EventKeyValueMap;

import java.net.InetAddress;

public class PacketSourceValidationRule implements AcknowledgementPacketValidationRule{
    @Override
    public void validate(InetAddress expectedSourceAddress, int expectedSourcePort, EventKeyValueMap sentEventKeyValueMap, EventKeyValueMap receivedEventKeyValueMap) throws NetworkException {
        String expectedIp = expectedSourceAddress.getHostAddress();
        String receivedIp = receivedEventKeyValueMap.getValue(UDPStatic.NetworkBaseKeys.SOURCE_IP_ADDRESS);
        int receivedPort = receivedEventKeyValueMap.getIntValue(UDPStatic.NetworkBaseKeys.SOURCE_PORT);

        if(!expectedIp.equals(receivedIp) || expectedSourcePort != receivedPort){
            throw new UnexpectedSourceException(UDPStatic.ExceptionsMessages.UNEXPECTED_ACKNOWLEDGEMENT_SOURCE);
        }
    }
}
