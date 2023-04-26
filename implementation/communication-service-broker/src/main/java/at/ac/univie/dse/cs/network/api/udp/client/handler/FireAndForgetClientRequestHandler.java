package at.ac.univie.dse.cs.network.api.udp.client.handler;

import at.ac.univie.dse.cs.network.api.udp.config.UDPStatic;
import at.ac.univie.dse.cs.network.api.udp.exceptions.PacketTransmissionException;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class FireAndForgetClientRequestHandler implements ClientRequestHandler {

    @Override
    public void sendPacket(InetAddress IPAddress, int port, byte[] data) throws NetworkException {
        DatagramPacket packet = new DatagramPacket(data, data.length, IPAddress, port);
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            throw new PacketTransmissionException(UDPStatic.ExceptionsMessages.PACKET_TRANSMISSION_EXCEPTION);
        }
    }
}
