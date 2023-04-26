package at.ac.univie.dse.cs.network.api.udp.server.preprocessors;

import at.ac.univie.dse.cs.network.api.udp.client.handler.ClientRequestHandler;
import at.ac.univie.dse.cs.network.api.udp.config.UDPStatic;
import at.ac.univie.dse.cs.network.api.udp.marshaller.entries.EntriesMarshallingService;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;
import at.ac.univie.dse.cs.network.model.EventKeyValueMap;
import com.google.common.collect.ImmutableMap;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class PacketAcknowledgementPreprocessor implements PacketPreprocessor {

    private final ClientRequestHandler clientRequestHandler;
    private final EntriesMarshallingService marshallingService;
    private final String serverIpAddress;
    private final String serverPort;

    public PacketAcknowledgementPreprocessor(ClientRequestHandler clientRequestHandler,
                                             EntriesMarshallingService marshallingService, String serverIpAddress, String serverPort){
        this.clientRequestHandler = clientRequestHandler;
        this.marshallingService = marshallingService;
        this.serverIpAddress = serverIpAddress;
        this.serverPort = serverPort;
    }


    @Override
    public void preprocessRequest(DatagramPacket datagramPacket, EventKeyValueMap packetMarshalledData) {
        InetAddress IPAddress = datagramPacket.getAddress();
        int port = datagramPacket.getPort();
        String id = packetMarshalledData.getValue(UDPStatic.NetworkBaseKeys.PACKET_ID_KEY);
        String seqNumber = updateAckSequenceNumber(packetMarshalledData.getIntValue(UDPStatic.NetworkBaseKeys.SEQUENCE_NUMBER_KEY));
        byte[] ackData = this.marshallingService.getBytes(ImmutableMap.of(UDPStatic.NetworkBaseKeys.PACKET_ID_KEY,id,
                UDPStatic.NetworkBaseKeys.SEQUENCE_NUMBER_KEY,seqNumber,
                UDPStatic.NetworkBaseKeys.SOURCE_IP_ADDRESS,this.serverIpAddress,
                UDPStatic.NetworkBaseKeys.SOURCE_PORT,this.serverPort));
        try {
            this.clientRequestHandler.sendPacket(IPAddress,port,ackData);
        } catch (NetworkException e) {
            e.printStackTrace();
        }
    }

    private String updateAckSequenceNumber(int ackSeqNumber) {
        return Integer.toString( ackSeqNumber+ UDPStatic.Configurations.ACK_SEQUENCE_NUMBER_INCREMENTATION_VALUE);
    }


}
