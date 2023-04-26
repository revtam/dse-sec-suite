package at.ac.univie.dse.cs.network.api.udp.client.handler.ackvalidation;

import at.ac.univie.dse.cs.network.model.EventKeyValueMap;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;

import java.net.InetAddress;
import java.util.List;

public class AcknowledgementPacketValidator {

    private final List<AcknowledgementPacketValidationRule> acknowledgementPacketValidationRules;

    public AcknowledgementPacketValidator(List<AcknowledgementPacketValidationRule> acknowledgementPacketValidationRules) {
        this.acknowledgementPacketValidationRules = acknowledgementPacketValidationRules;
    }


    public void validateAcknowledgement(InetAddress expectedSource, int expectedPort, EventKeyValueMap sentEventKeyValueMap, EventKeyValueMap receivedEventKeyValueMap) throws NetworkException {
        for(AcknowledgementPacketValidationRule acknowledgementPacketValidationRule : this.acknowledgementPacketValidationRules){
            acknowledgementPacketValidationRule.validate(expectedSource,expectedPort,sentEventKeyValueMap,receivedEventKeyValueMap);
        }
    }
}
