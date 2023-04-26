package at.ac.univie.dse.cs.network.api.udp.client.handler.ackvalidation;

import at.ac.univie.dse.cs.network.api.udp.config.UDPStatic;
import at.ac.univie.dse.cs.network.api.udp.exceptions.UnexpectedSequenceNumberException;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;
import at.ac.univie.dse.cs.network.model.EventKeyValueMap;

import java.net.InetAddress;

public class PacketSequenceNumberValidationRule implements AcknowledgementPacketValidationRule{

    private final int sequenceNumberIncrementationValue;
    public PacketSequenceNumberValidationRule(int sequenceNumberIncrementationValue){

        this.sequenceNumberIncrementationValue = sequenceNumberIncrementationValue;
    }


    @Override
    public void validate(InetAddress expectedSourceAddress, int expectedSourcePort, EventKeyValueMap sentEventKeyValueMap, EventKeyValueMap receivedEventKeyValueMap) throws NetworkException {
        int sentSeqNumber = sentEventKeyValueMap.getIntValue(UDPStatic.NetworkBaseKeys.SEQUENCE_NUMBER_KEY);
        int receivedSeqNumber = receivedEventKeyValueMap.getIntValue(UDPStatic.NetworkBaseKeys.SEQUENCE_NUMBER_KEY);

        if(sentSeqNumber + this.sequenceNumberIncrementationValue != receivedSeqNumber){
            throw new UnexpectedSequenceNumberException(UDPStatic.ExceptionsMessages.UNEXPECTED_ACKNOWLEDGEMENT_SEQUENCE_NUMBER);
        }
    }
}
