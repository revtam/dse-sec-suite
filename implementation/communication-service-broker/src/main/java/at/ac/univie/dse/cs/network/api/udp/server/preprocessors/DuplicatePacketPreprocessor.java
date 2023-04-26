package at.ac.univie.dse.cs.network.api.udp.server.preprocessors;

import at.ac.univie.dse.cs.network.api.udp.config.UDPStatic;
import at.ac.univie.dse.cs.network.api.udp.exceptions.DuplicatePacketException;
import at.ac.univie.dse.cs.network.model.EventKeyValueMap;

import java.net.DatagramPacket;
import java.util.concurrent.BlockingQueue;

public class DuplicatePacketPreprocessor implements PacketPreprocessor {

    private final BlockingQueue<String> packetsIdQueue;
    private final int queueSizeBound;

    public DuplicatePacketPreprocessor(int queueSizeBound,BlockingQueue<String> packetsIdQueue){
        this.queueSizeBound = queueSizeBound;
        this.packetsIdQueue = packetsIdQueue;
    }


    @Override
    public void preprocessRequest(DatagramPacket datagramPacket, EventKeyValueMap packetMarshalledData) throws DuplicatePacketException {
        String packetId = packetMarshalledData.getValue(UDPStatic.NetworkBaseKeys.PACKET_ID_KEY);
        checkIfPaketIdExists(packetId);
        updatePacketsIdQueue(packetId);
    }

    private void checkIfPaketIdExists(String packetId) throws DuplicatePacketException {
        if(this.packetsIdQueue.contains(packetId)){
            throw new DuplicatePacketException(UDPStatic.ExceptionsMessages.DUPLICATE_PACKET.concat("PacketId: ").concat(packetId));
        }
    }

    public synchronized void updatePacketsIdQueue(String packetId){
        while(this.packetsIdQueue.size() >= this.queueSizeBound){
            this.packetsIdQueue.poll();
        }
        this.packetsIdQueue.add(packetId);
    }
}
