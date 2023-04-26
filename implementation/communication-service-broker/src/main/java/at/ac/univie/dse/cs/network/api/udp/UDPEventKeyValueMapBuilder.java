package at.ac.univie.dse.cs.network.api.udp;

import at.ac.univie.dse.cs.network.model.EventKeyValueMapBuilder;
import at.ac.univie.dse.cs.network.api.udp.config.UDPStatic;
import at.ac.univie.dse.cs.network.api.udp.server.utils.Utils;

import java.util.Map;

public class UDPEventKeyValueMapBuilder extends EventKeyValueMapBuilder {

    public UDPEventKeyValueMapBuilder(){
        super();
    }

    public UDPEventKeyValueMapBuilder(Map<String,String> map){
        super(map);
    }

    public UDPEventKeyValueMapBuilder withNewID(){
        this.entries.put(UDPStatic.NetworkBaseKeys.PACKET_ID_KEY, Utils.getStringRandomId());
        return this;
    }

    public UDPEventKeyValueMapBuilder withSequenceNumber(int sequenceNumberUpperBound){
        this.entries.put(UDPStatic.NetworkBaseKeys.SEQUENCE_NUMBER_KEY,Utils.getStringRandomSequenceNumber(sequenceNumberUpperBound));
        return this;
    }

    public UDPEventKeyValueMapBuilder withSourceIPAddress(String ipAddress){
        this.entries.put(UDPStatic.NetworkBaseKeys.SOURCE_IP_ADDRESS,ipAddress);
        return this;
    }

    public UDPEventKeyValueMapBuilder withSourcePort(String port){
        this.entries.put(UDPStatic.NetworkBaseKeys.SOURCE_PORT,port);
        return this;
    }

    public UDPEventKeyValueMapBuilder withDestinationIPAddress(String ipAddress) {
        this.entries.put(UDPStatic.NetworkBaseKeys.DESTINATION_IP_ADDRESS,ipAddress);
        return this;
    }

    public UDPEventKeyValueMapBuilder withDestinationPort(String port) {
        this.entries.put(UDPStatic.NetworkBaseKeys.DESTINATION_PORT,port);
        return this;
    }
}
