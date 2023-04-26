package at.ac.univie.dse.cs.network.api.udp.marshaller.entries;

import at.ac.univie.dse.cs.network.api.udp.config.UDPStatic;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapToByteMarshaller implements Function<Map<String,String>,byte[]> {

    @Override
    public byte[] apply(Map<String, String> packetData) {
        String concatenatedPacketData = packetData.entrySet().stream()
                .map(dataEntry -> dataEntry.getKey().concat(UDPStatic.PacketDataDelimiters.KEY_VALUE_SEPARATION_KEY).concat(dataEntry.getValue()))
                .collect(Collectors.joining(UDPStatic.PacketDataDelimiters.TOKENS_SEPARATION_KEY));
        return concatenatedPacketData.getBytes();
    }
}
