package at.ac.univie.dse.cs.network.api.udp.server.preprocessors;

import at.ac.univie.dse.cs.network.api.udp.client.handler.FireAndForgetClientRequestHandler;
import at.ac.univie.dse.cs.network.api.udp.marshaller.entries.EntriesMarshallingService;
import com.google.common.collect.ImmutableList;
import at.ac.univie.dse.cs.network.api.udp.marshaller.entries.EntriesMarshallingServiceBuilder;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ProcessingManagerBuilder {

    public static PacketProcessingManager buildProcessingManager(int queueSizeBound, BlockingQueue<String> packetsIdQueue,
                                                                 String serverIpAddress,String serverPort){
        EntriesMarshallingService parsingService = EntriesMarshallingServiceBuilder.buildMarshallingService();
        List<PacketPreprocessor> preprocessors = ImmutableList.of(
                new PacketAcknowledgementPreprocessor(new FireAndForgetClientRequestHandler(),parsingService, serverIpAddress, serverPort),
                new DuplicatePacketPreprocessor(queueSizeBound,packetsIdQueue)
                );
        return new PackerProcessingManagerImpl(preprocessors);
    }
}
