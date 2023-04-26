package at.ac.univie.dse.cs.network.api.udp.client.handler;

import at.ac.univie.dse.cs.network.api.udp.client.handler.ackvalidation.AcknowledgementPacketValidator;
import at.ac.univie.dse.cs.network.api.udp.config.UDPStatic;
import at.ac.univie.dse.cs.network.api.udp.marshaller.entries.EntriesMarshallingService;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;
import at.ac.univie.dse.cs.network.model.EventKeyValueMap;
import at.ac.univie.dse.cs.properties.ApplicationPropertiesCache;
import at.ac.univie.dse.cs.network.api.udp.exceptions.PacketTransmissionException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SyncWithServerClientRequestHandler implements ClientRequestHandler {

    private final AcknowledgementPacketValidator acknowledgementPacketHandler;
    private final EntriesMarshallingService marshallingService;
    private final int maxAttempts;
    private final int udpPacketSize;
    private final int acknowledgmentTimeout;


    public SyncWithServerClientRequestHandler(AcknowledgementPacketValidator acknowledgementPacketHandler,
                                              EntriesMarshallingService marshallingService,
                                              int maxAttempts, int udpPacketSize, int acknowledgmentTimeout) {
        this.acknowledgementPacketHandler = acknowledgementPacketHandler;
        this.marshallingService = marshallingService;
        this.maxAttempts = maxAttempts;
        this.udpPacketSize = udpPacketSize;
        this.acknowledgmentTimeout = acknowledgmentTimeout;
    }

    @Override
    public void sendPacket(InetAddress IPAddress, int port, byte[] data) throws NetworkException {
        ApplicationPropertiesCache applicationPropertiesCache = ApplicationPropertiesCache.getInstance();
        boolean success = true;
        int i;
        for(i = 0 ; i < this.maxAttempts ; ++i){
            DatagramPacket packet = new DatagramPacket(data, data.length, IPAddress, port);
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.send(packet);
                byte[] receivedDataBuffer = new byte[this.udpPacketSize];
                DatagramPacket datagramPacket = new DatagramPacket(receivedDataBuffer, receivedDataBuffer.length);
                socket.setSoTimeout(this.acknowledgmentTimeout);
                socket.receive(datagramPacket);
                EventKeyValueMap sentEventKeyValueMap = this.marshallingService.getParsedData(data);
                EventKeyValueMap receivedEventKeyValueMap = this.marshallingService.getParsedData(datagramPacket.getData());
                this.acknowledgementPacketHandler.validateAcknowledgement(IPAddress, port, sentEventKeyValueMap, receivedEventKeyValueMap);
                break;
            } catch (IOException | NetworkException e) {
                System.out.println("NO ACK RECEIVED");
                        success = false;
                e.printStackTrace();
            }
        }
        if(!success && this.maxAttempts == i){
            throw new PacketTransmissionException(UDPStatic.ExceptionsMessages.UNREACHABLE_DESTINATION);
        }

    }
}
