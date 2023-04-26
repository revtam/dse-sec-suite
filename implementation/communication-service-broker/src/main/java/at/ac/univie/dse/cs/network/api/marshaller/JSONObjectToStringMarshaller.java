package at.ac.univie.dse.cs.network.api.marshaller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONObjectToStringMarshaller implements ObjectToStringMarshaller{

    public final ObjectMapper objectMapper;

    public JSONObjectToStringMarshaller(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> String MarshallObject(T object) throws IOException {
        return this.objectMapper.writeValueAsString(object);
    }
}
