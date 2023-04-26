package at.ac.univie.dse.cs.network.api.udp.client.handler.ackvalidation;

import java.util.List;

public class AcknowledgementPacketValidatorBuilder {

    public static AcknowledgementPacketValidator build(int sequenceNumberIncrementationValue){
        return new AcknowledgementPacketValidator(List.of(
                new PacketIDValidationRule(),
                new PacketSequenceNumberValidationRule(sequenceNumberIncrementationValue)
        ));
    }
}
