package at.ac.univie.dse.cs.network.api.udp.marshaller.entries;

import at.ac.univie.dse.cs.network.api.udp.config.UDPStatic;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ByteToMapMarshaller implements Function<byte[],Map<String, String>> {

    @Override
    public Map<String, String> apply(byte[] bytes) {
        String requestDeserializedData = getString(bytes);
        return parseData(requestDeserializedData);
    }

    private Map<String, String> parseData(String deserializedData){
        String[] tokens = deserializedData.split(UDPStatic.PacketDataDelimiters.TOKENS_SEPARATION_KEY);
        return Arrays.stream(tokens)
                .map(token -> {
                    int keyValueSeparationPosition = token.indexOf(UDPStatic.PacketDataDelimiters.KEY_VALUE_SEPARATION_KEY);
                    final var key = token.substring(0,keyValueSeparationPosition);
                    final var value = token.substring(keyValueSeparationPosition+1);
                    String[] entry =  new String[2];
                    entry[0]=key;
                    entry[1]=value;
                    return entry;
                })
                .collect(Collectors.toUnmodifiableMap(keyValueArray -> keyValueArray[0], keyValueArray -> keyValueArray[1]));
    }

    private String getString(byte[] data){
        if (data == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (i < data.length && data[i] != 0)
        {
            ret.append((char) data[i]);
            i++;
        }
        return ret.toString();
    }

}
