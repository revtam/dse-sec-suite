package at.ac.univie.dse.cs.starter;

import at.ac.univie.dse.cs.network.api.invoker.Invoker;
import at.ac.univie.dse.cs.network.api.marshaller.JSONObjectToStringMarshaller;
import at.ac.univie.dse.cs.network.api.marshaller.ObjectToStringMarshaller;
import at.ac.univie.dse.cs.network.api.requester.Requester;
import at.ac.univie.dse.cs.network.api.udp.client.handler.ClientRequestHandler;
import at.ac.univie.dse.cs.network.api.udp.client.handler.ackvalidation.AcknowledgementPacketValidator;
import at.ac.univie.dse.cs.network.api.udp.client.handler.ackvalidation.AcknowledgementPacketValidatorBuilder;
import at.ac.univie.dse.cs.network.api.udp.client.requestor.UDPRequesterBuilder;
import at.ac.univie.dse.cs.network.api.udp.marshaller.entries.EntriesMarshallingService;
import at.ac.univie.dse.cs.network.api.udp.marshaller.entries.EntriesMarshallingServiceBuilder;
import at.ac.univie.dse.cs.network.api.udp.server.handler.UdpServerRequestHandler;
import at.ac.univie.dse.cs.network.api.udp.server.handler.ServerRequestHandlerBuilder;
import at.ac.univie.dse.cs.properties.ApplicationPropertiesCache;
import at.ac.univie.dse.cs.properties.PropertiesKeys;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import at.ac.univie.dse.cs.network.api.udp.client.handler.SyncWithServerClientRequestHandler;

import java.util.concurrent.LinkedBlockingQueue;

public class ApplicationStarter {

    protected static final ApplicationPropertiesCache applicationPropertiesCache = ApplicationPropertiesCache.getInstance();


    protected static UdpServerRequestHandler buildUDPServerRequestHandler(Invoker invoker){
        String serverIpAddress = applicationPropertiesCache.getProperty(PropertiesKeys.UDP.LISTENER_IP_ADDRESS);
        int serverPort = applicationPropertiesCache.getIntProperty(PropertiesKeys.UDP.LISTENER_PORT);
        int executorServiceServerThreads = applicationPropertiesCache.getIntProperty(PropertiesKeys.UDP.LISTENER_EXECUTOR_THREADS);
        int udpPacketSize = applicationPropertiesCache.getIntProperty(PropertiesKeys.UDP.LISTENER_PACKET_SIZE);
        int serverRequestQueueSize = applicationPropertiesCache.getIntProperty(PropertiesKeys.UDP.LISTENER_REQUESTS_QUEUE_SIZE);
        EntriesMarshallingService entriesMarshallingService = EntriesMarshallingServiceBuilder.buildMarshallingService();
        return ServerRequestHandlerBuilder.buildServerRequestHandler(entriesMarshallingService,serverIpAddress,serverPort,executorServiceServerThreads,invoker,serverRequestQueueSize,new LinkedBlockingQueue<>(),udpPacketSize);
    }

    protected static Requester buildUDPRequester(){
        int maxAttempts = applicationPropertiesCache.getIntProperty(PropertiesKeys.UDP.CLIENT_REQUEST_MAX_ATTEMPTS);
        int udpAckPacketSize = applicationPropertiesCache.getIntProperty(PropertiesKeys.UDP.ACKNOWLEDGMENT_PACKET_SIZE);
        int acknowledgmentTimeout = applicationPropertiesCache.getIntProperty(PropertiesKeys.UDP.ACKNOWLEDGMENT_TIMEOUT);
        String sourceIpAddress = applicationPropertiesCache.getProperty(PropertiesKeys.UDP.LISTENER_IP_ADDRESS);
        String sourcePort = applicationPropertiesCache.getProperty(PropertiesKeys.UDP.LISTENER_PORT);
        String brokerIpAddress = applicationPropertiesCache.getProperty(PropertiesKeys.UDP.BROKER_LISTENER_IP_ADDRESS);
        int brokerPort = applicationPropertiesCache.getIntProperty(PropertiesKeys.UDP.BROKER_LISTENER_PORT);
        int sequenceNumberUpperBound = applicationPropertiesCache.getIntProperty(PropertiesKeys.UDP.PACKET_SEQUENCE_BOUND);
        int sequenceNumberIncrementationValue = applicationPropertiesCache.getIntProperty(PropertiesKeys.UDP.ACKNOWLEDGMENT_SEQUENCE_NUMBER_INCREMENTATION);
        ObjectToStringMarshaller objectToStringMarshaller = new JSONObjectToStringMarshaller(buildObjectMapper());
        EntriesMarshallingService entriesMarshallingService = EntriesMarshallingServiceBuilder.buildMarshallingService();
        AcknowledgementPacketValidator acknowledgementPacketValidator = AcknowledgementPacketValidatorBuilder.build(sequenceNumberIncrementationValue);
        ClientRequestHandler clientRequestHandler = new SyncWithServerClientRequestHandler(acknowledgementPacketValidator, entriesMarshallingService, maxAttempts,udpAckPacketSize,acknowledgmentTimeout);
        return UDPRequesterBuilder.build(objectToStringMarshaller,entriesMarshallingService,clientRequestHandler,
                sourceIpAddress,sourcePort,brokerIpAddress,brokerPort,sequenceNumberUpperBound);

    }

    public static ObjectMapper buildObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }
}
