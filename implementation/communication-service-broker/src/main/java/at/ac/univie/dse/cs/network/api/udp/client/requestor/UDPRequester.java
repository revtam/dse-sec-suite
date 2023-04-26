package at.ac.univie.dse.cs.network.api.udp.client.requestor;

import at.ac.univie.dse.cs.network.api.requester.Requester;
import at.ac.univie.dse.cs.network.api.udp.UDPEventKeyValueMapBuilder;
import at.ac.univie.dse.cs.network.api.udp.config.UDPStatic;
import at.ac.univie.dse.cs.network.api.udp.client.handler.ClientRequestHandler;
import at.ac.univie.dse.cs.network.api.udp.marshaller.entries.EntriesMarshallingService;
import at.ac.univie.dse.cs.network.api.marshaller.ObjectToStringMarshaller;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;
import at.ac.univie.dse.cs.network.model.EventKeyValueMap;
import at.ac.univie.dse.cs.network.model.EventType;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

public class UDPRequester implements Requester {

    private final ClientRequestHandler clientRequestHandler;
    private final ObjectToStringMarshaller objectToStringMarshaller;
    private final EntriesMarshallingService entriesMarshallingService;
    private final String sourceIpAddress;
    private final String sourcePort;
    private final String brokerIpAddress;
    private final int brokerPort;
    private final int sequenceNumberUpperBound;



    public UDPRequester(ClientRequestHandler clientRequestHandler,
                        ObjectToStringMarshaller objectToStringMarshaller,
                        EntriesMarshallingService entriesMarshallingService,
                        String sourceIpAddress,
                        String sourcePort,
                        String brokerIpAddress,
                        int brokerPort,
                        int sequenceNumberUpperBound) {
        this.clientRequestHandler = clientRequestHandler;
        this.objectToStringMarshaller = objectToStringMarshaller;
        this.entriesMarshallingService = entriesMarshallingService;
        this.sourceIpAddress = sourceIpAddress;
        this.sourcePort = sourcePort;
        this.brokerIpAddress = brokerIpAddress;
        this.brokerPort = brokerPort;
        this.sequenceNumberUpperBound = sequenceNumberUpperBound;
    }


    @Override
    public <T> void request(T object,String eventName,String publishTarget,EventType eventType) throws NetworkException, IOException {
        String objectAsString = this.objectToStringMarshaller.MarshallObject(object);
        EventKeyValueMap eventKeyValueMap = buildUDPKeyValueEventData(eventName, publishTarget, objectAsString,eventType);
        byte[] data = this.entriesMarshallingService.getBytes(eventKeyValueMap.getEventData());
        this.clientRequestHandler.sendPacket(InetAddress.getByName(this.brokerIpAddress),this.brokerPort,data);
    }

    @Override
    public void request(Map<String, String> data) throws NetworkException, IOException {
        EventKeyValueMap eventKeyValueMap = new EventKeyValueMap(data);
        final byte[] dataBuffer = this.entriesMarshallingService.getBytes(eventKeyValueMap.getEventData());
        final String destinationIpAddress = data.get(UDPStatic.NetworkBaseKeys.DESTINATION_IP_ADDRESS);
        final int destinationPort = Integer.parseInt(data.get(UDPStatic.NetworkBaseKeys.DESTINATION_PORT));
        this.clientRequestHandler.sendPacket(InetAddress.getByName(destinationIpAddress),destinationPort,dataBuffer);

    }

    private EventKeyValueMap buildUDPKeyValueEventData(String eventName, String publishTarget, String objectAsString, EventType eventType) {
        UDPEventKeyValueMapBuilder udpEventKeyValueMapBuilder = new UDPEventKeyValueMapBuilder();
        return udpEventKeyValueMapBuilder
                .withNewID()
                .withSequenceNumber(this.sequenceNumberUpperBound)
                .withSourceIPAddress(this.sourceIpAddress)
                .withSourcePort(this.sourcePort)
                .withSenderIdentifier(this.sourceIpAddress.concat(this.sourcePort))
                .withEventName(eventName)
                .withEventType(eventType.toString())
                .withPublishTarget(publishTarget)
                .withPayLoad(objectAsString)
                .build();

    }
}
