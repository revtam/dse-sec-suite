package at.ac.univie.dse.cs.network.api.udp.server.handler;

import at.ac.univie.dse.cs.network.api.udp.marshaller.entries.EntriesMarshallingService;
import at.ac.univie.dse.cs.network.api.udp.server.preprocessors.ProcessingManagerBuilder;
import at.ac.univie.dse.cs.network.api.invoker.Invoker;
import at.ac.univie.dse.cs.network.api.udp.server.preprocessors.PacketProcessingManager;
import at.ac.univie.dse.cs.network.api.udp.server.status.ServerStatusManager;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRequestHandlerBuilder {

    public static UdpServerRequestHandler buildServerRequestHandler(EntriesMarshallingService marshallingService,
                                                                    String serverIpAddress,
                                                                    int serverPort,
                                                                    int executorServiceServerThreads,
                                                                    Invoker invoker,
                                                                    int receivedPacketsQueueSize,
                                                                    BlockingQueue<String> receivedPacketsQueue,
                                                                    int udpPacketSize){
        ServerStatusManager listenerStatusManager = new ServerStatusManager();
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(executorServiceServerThreads);
        PacketProcessingManager packetProcessingManager = ProcessingManagerBuilder.buildProcessingManager(receivedPacketsQueueSize,receivedPacketsQueue,serverIpAddress,Integer.toString(serverPort));
        UDPServerRequestHandlingThreadFactory udpServerRequestHandlingThreadFactory =
                new UDPServerRequestHandlingThreadFactory(marshallingService,packetProcessingManager, invoker);
        return new UdpServerRequestHandler(listenerStatusManager,datagramSocket,executorService,udpServerRequestHandlingThreadFactory, udpPacketSize);
    }


}
