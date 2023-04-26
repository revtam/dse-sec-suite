package at.ac.univie.dse.cs.network.api.udp.client.handler.ackvalidation;

import at.ac.univie.dse.cs.network.api.udp.config.UDPStatic;
import at.ac.univie.dse.cs.network.model.EventKeyValueMap;
import at.ac.univie.dse.cs.network.api.udp.exceptions.UnexpectedPacketIDException;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;

import java.net.InetAddress;

public class PacketIDValidationRule implements AcknowledgementPacketValidationRule{


    @Override
    public void validate(InetAddress expectedSourceAddress, int expectedSourcePort, EventKeyValueMap sentEventKeyValueMap, EventKeyValueMap receivedEventKeyValueMap) throws NetworkException {
        String sentPacketId = sentEventKeyValueMap.getValue(UDPStatic.NetworkBaseKeys.PACKET_ID_KEY);
        String receivedPacketId = receivedEventKeyValueMap.getValue(UDPStatic.NetworkBaseKeys.PACKET_ID_KEY);

        if(!sentPacketId.equals(receivedPacketId)){
            throw new UnexpectedPacketIDException(UDPStatic.ExceptionsMessages.UNEXPECTED_ACKNOWLEDGEMENT_PACKET_ID);
        }
    }
}
