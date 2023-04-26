package at.ac.univie.dse.cs.network.api.udp.client.handler;

import at.ac.univie.dse.cs.network.exceptions.NetworkException;

import java.net.InetAddress;

public interface ClientRequestHandler {

    void sendPacket(InetAddress IPAddress, int port, byte[] data) throws NetworkException;
}
