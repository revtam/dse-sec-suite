package at.ac.univie.dse.cs.network.api.udp.client.requestor;

import at.ac.univie.dse.cs.network.api.requester.Requester;
import at.ac.univie.dse.cs.network.api.udp.client.handler.ClientRequestHandler;
import at.ac.univie.dse.cs.network.api.udp.marshaller.entries.EntriesMarshallingService;
import at.ac.univie.dse.cs.network.api.marshaller.ObjectToStringMarshaller;

public class UDPRequesterBuilder {

    public static Requester build(ObjectToStringMarshaller objectToStringMarshaller,
                                  EntriesMarshallingService entriesMarshallingService,
                                  ClientRequestHandler clientRequestHandler,
                                  String sourceIpAddress,
                                  String sourcePort,
                                  String brokerIpAddress,
                                  int brokerPort,
                                  int sequenceNumberUpperBound){
        return new UDPRequester(clientRequestHandler, objectToStringMarshaller, entriesMarshallingService, sourceIpAddress, sourcePort, brokerIpAddress,brokerPort, sequenceNumberUpperBound);
    }
}
