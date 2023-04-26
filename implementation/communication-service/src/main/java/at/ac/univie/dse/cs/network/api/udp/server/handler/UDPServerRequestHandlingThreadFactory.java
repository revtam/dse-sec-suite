package at.ac.univie.dse.cs.network.api.udp.server.handler;

import at.ac.univie.dse.cs.network.api.udp.marshaller.entries.EntriesMarshallingService;
import at.ac.univie.dse.cs.network.model.EventKeyValueMap;
import at.ac.univie.dse.cs.network.api.invoker.Invoker;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;
import at.ac.univie.dse.cs.network.api.udp.server.preprocessors.PacketProcessingManager;

import java.net.DatagramPacket;

public class UDPServerRequestHandlingThreadFactory{

    private final EntriesMarshallingService marshallingService;
    private final PacketProcessingManager packerProcessingManager;
    private final Invoker invoker;

    public UDPServerRequestHandlingThreadFactory(EntriesMarshallingService parsingService,
                                                 PacketProcessingManager packerPreprocessingManager,
                                                 Invoker invoker) {
        this.marshallingService = parsingService;
        this.packerProcessingManager = packerPreprocessingManager;
        this.invoker = invoker;
    }

    public UDPServerRequestHandlingThread getUDPServerRequestHandlingThreadInstance(DatagramPacket datagramPacket){
        return new UDPServerRequestHandlingThread(datagramPacket, marshallingService, packerProcessingManager, invoker);
    }



    public static class UDPServerRequestHandlingThread implements Runnable{

        private final DatagramPacket datagramPacket;
        private final EntriesMarshallingService marshallingService;
        private final PacketProcessingManager packerProcessingManager;
        private final Invoker invoker;

        public UDPServerRequestHandlingThread(DatagramPacket datagramPacket,
                                              EntriesMarshallingService parsingService,
                                              PacketProcessingManager packerPreprocessingManager,
                                              Invoker invoker) {
            this.datagramPacket = datagramPacket;
            this.marshallingService = parsingService;
            this.packerProcessingManager = packerPreprocessingManager;
            this.invoker = invoker;
        }

        @Override
        public void run() {
            try {
                final EventKeyValueMap eventKeyValueMap = this.marshallingService.getParsedData(this.datagramPacket.getData());
                this.packerProcessingManager.preprocessPacket(this.datagramPacket,eventKeyValueMap);
                this.invoker.invoke(eventKeyValueMap.getEventData());
            } catch (NetworkException e) {
                e.printStackTrace();
            }
        }
    }
}

