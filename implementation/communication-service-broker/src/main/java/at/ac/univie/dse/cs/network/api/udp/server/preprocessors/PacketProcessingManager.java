package at.ac.univie.dse.cs.network.api.udp.server.preprocessors;

import at.ac.univie.dse.cs.network.model.EventKeyValueMap;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;

import java.net.DatagramPacket;

public interface PacketProcessingManager {

    void preprocessPacket(DatagramPacket datagramPacket, EventKeyValueMap eventKeyValueMap) throws NetworkException;
}
