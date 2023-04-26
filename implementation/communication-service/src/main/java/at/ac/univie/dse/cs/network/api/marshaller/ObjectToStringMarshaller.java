package at.ac.univie.dse.cs.network.api.marshaller;

import java.io.IOException;

public interface ObjectToStringMarshaller {
    
    <T> String MarshallObject(T object) throws IOException;
}
