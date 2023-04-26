package at.ac.univie.dse.cs.network.api.udp.marshaller.entries;

public class EntriesMarshallingServiceBuilder {

    public static EntriesMarshallingService buildMarshallingService(){
        return new EntriesMarshallingService(new ByteToMapMarshaller(),new MapToByteMarshaller());
    }
}
