package at.ac.univie.dse.cs.network.api.udp.server.preprocessors;

import at.ac.univie.dse.cs.network.api.udp.exceptions.DuplicatePacketException;
import at.ac.univie.dse.cs.network.model.EventKeyValueMap;

import java.net.DatagramPacket;

public interface PacketPreprocessor {

    void preprocessRequest(DatagramPacket datagramPacket, EventKeyValueMap packetMarshalledData) throws DuplicatePacketException;

}
