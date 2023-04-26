package at.ac.univie.dse.cs.network.api.udp.server.preprocessors;

import at.ac.univie.dse.cs.network.model.EventKeyValueMap;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;

import java.net.DatagramPacket;
import java.util.List;

public class PackerProcessingManagerImpl implements PacketProcessingManager {

    private final List<PacketPreprocessor> preprocessors;

    public PackerProcessingManagerImpl(List<PacketPreprocessor> preprocessors) {
        this.preprocessors = preprocessors;
    }

    @Override
    public void preprocessPacket(DatagramPacket datagramPacket, EventKeyValueMap eventKeyValueMap) throws NetworkException {
        for(PacketPreprocessor packetPreprocessor : this.preprocessors){
            packetPreprocessor.preprocessRequest(datagramPacket,eventKeyValueMap);
        }
    }
}
