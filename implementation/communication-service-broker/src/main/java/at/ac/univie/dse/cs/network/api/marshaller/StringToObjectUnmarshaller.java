package at.ac.univie.dse.cs.network.api.marshaller;

import java.io.IOException;

public interface StringToObjectUnmarshaller {
    
    <T> T unmarshall(String objectAsString,Class<T> classType) throws IOException;
}
