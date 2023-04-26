package at.ac.univie.dse.cs.network.api.udp.server.handler;


import at.ac.univie.dse.cs.network.api.udp.server.status.ServerStatusManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;

public class UdpServerRequestHandler implements Runnable{

    private final ServerStatusManager listenerStatusManager;
    private final DatagramSocket serverSocket;
    private final ExecutorService executorService;
    private final UDPServerRequestHandlingThreadFactory UDPServerRequestHandlingThreadFactory;
    private final int udpPacketSize;


    /**
     * create the socket that listens to the received request create the thread
     * pool, for each received request a new thread will be created
     */
    public UdpServerRequestHandler(ServerStatusManager listenerStatusManager,
                                   DatagramSocket serverReceivingSocket,
                                   ExecutorService executor,
                                   UDPServerRequestHandlingThreadFactory UDPServerRequestHandlingThreadFactory, int udpPacketSize) {
        this.listenerStatusManager = listenerStatusManager;
        this.serverSocket = serverReceivingSocket;
        this.executorService = executor;
        this.UDPServerRequestHandlingThreadFactory = UDPServerRequestHandlingThreadFactory;
        this.udpPacketSize = udpPacketSize;
    }

    /**
     * start listening to requests as long as the status is set to true
     */
    @Override
    public void run() {
        while (listenerStatusManager.getStatus()) {
            try {
                byte[] receivedData = new byte[this.udpPacketSize];
                DatagramPacket serverReceivingPacket = new DatagramPacket(receivedData, receivedData.length);
                this.serverSocket.receive(serverReceivingPacket);
                Runnable invoker = this.UDPServerRequestHandlingThreadFactory.getUDPServerRequestHandlingThreadInstance(serverReceivingPacket);
                executorService.execute(invoker);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        shutDown();
    }


    /**
     * once the status is turned off, this method will be called and close
     * connections and thread pool
     */
    private void shutDown() {
        serverSocket.close();
        executorService.shutdown();
    }
}
