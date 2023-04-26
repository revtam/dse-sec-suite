package at.ac.univie.dse.cs.network.api.udp.services;

import at.ac.univie.dse.cs.network.api.requester.Requester;
import at.ac.univie.dse.cs.network.api.udp.UDPEventKeyValueMapBuilder;
import at.ac.univie.dse.cs.network.api.udp.config.UDPStatic;
import at.ac.univie.dse.cs.network.model.Static;
import at.ac.univie.dse.cs.network.api.services.PublishedEventListener;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;
import at.ac.univie.dse.cs.network.model.EventKeyValueMap;
import at.ac.univie.dse.cs.network.model.EventType;

import java.io.IOException;
import java.util.Map;

public class UDPPublishedEventListener implements PublishedEventListener {

    private final Requester requester;
    private final String ipAddress;
    private final String port;
    private final int sequenceNumberUpperBound;

    public UDPPublishedEventListener(Requester requester, String ipAddress, String port, int sequenceNumberUpperBound) {
        this.requester = requester;
        this.ipAddress = ipAddress;
        this.port = port;
        this.sequenceNumberUpperBound = sequenceNumberUpperBound;
    }

    @Override
    public void accept(Map<String, String> data, String payLoad) throws NetworkException, IOException {
        EventKeyValueMap eventKeyValueMap = createNewPublishEventFrom(data,payLoad);
        this.requester.request(eventKeyValueMap.getEventData());
    }

    private EventKeyValueMap createNewPublishEventFrom(Map<String, String> data, String payLoad) {
        UDPEventKeyValueMapBuilder udpEventKeyValueMapBuilder = new UDPEventKeyValueMapBuilder();
        return udpEventKeyValueMapBuilder
                .withNewID()
                .withSequenceNumber(this.sequenceNumberUpperBound)
                .withSourceIPAddress(this.ipAddress)
                .withSourcePort(this.port)
                .withDestinationIPAddress(data.get(UDPStatic.NetworkBaseKeys.SOURCE_IP_ADDRESS))
                .withDestinationPort(data.get(UDPStatic.NetworkBaseKeys.SOURCE_PORT))
                .withSenderIdentifier(this.ipAddress.concat(this.port))
                .withEventName(data.get(Static.EVENT_NAME_KEY))
                .withEventType(EventType.PUBLISH.toString())
                .withPublishTarget(data.get(Static.PUBLISH_TARGET_KEY))
                .withPayLoad(payLoad)
                .build();
    }
}
