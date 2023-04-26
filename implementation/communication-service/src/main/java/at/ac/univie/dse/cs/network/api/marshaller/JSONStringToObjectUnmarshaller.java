package at.ac.univie.dse.cs.network.api.marshaller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONStringToObjectUnmarshaller implements StringToObjectUnmarshaller{

    private final ObjectMapper objectMapper;

    public JSONStringToObjectUnmarshaller(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public <T> T unmarshall(String objectAsString, Class<T> classType) throws IOException {
        return (T) objectMapper.readValue(objectAsString,classType);
    }
}
