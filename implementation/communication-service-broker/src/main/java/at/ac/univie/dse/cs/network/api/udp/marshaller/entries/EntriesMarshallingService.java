package at.ac.univie.dse.cs.network.api.udp.marshaller.entries;

import at.ac.univie.dse.cs.network.model.EventKeyValueMap;

import java.util.Map;
import java.util.function.Function;

public class EntriesMarshallingService {

    private final Function<byte[], Map<String,String>> byteToMapParser;
    private final Function<Map<String,String>,byte[]> mapToByteParser;

    public EntriesMarshallingService(Function<byte[], Map<String, String>> byteToMapParser,
                                     Function<Map<String, String>, byte[]> mapTpByteParser) {
        this.byteToMapParser = byteToMapParser;
        this.mapToByteParser = mapTpByteParser;
    }

    public byte[] getBytes(Map<String,String> packetData){
        return this.mapToByteParser.apply(packetData);
    }

    public EventKeyValueMap getParsedData(byte[] data){
        return new EventKeyValueMap(this.byteToMapParser.apply(data));
    }
}
