package at.ac.univie.dse.cs.properties;

public class PropertiesKeys {

    public static class UDP{
        public static final String LISTENER_IP_ADDRESS = "udp.listener.ip";
        public static final String LISTENER_PORT = "udp.listener.port";
        public static final String LISTENER_SOCKET_TIMEOUT = "udp.listener.socket.timeout";
        public static final String BROKER_LISTENER_IP_ADDRESS = "udp.broker.listener.ip";
        public static final String BROKER_LISTENER_PORT = "udp.broker.listener.port";
        public static final String LISTENER_EXECUTOR_THREADS = "udp.listener.executor.threads";
        public static final String LISTENER_PACKET_SIZE = "udp.listener.packet.size";
        public static final String LISTENER_REQUESTS_QUEUE_SIZE = "udp.listener.queue.size";
        public static final String CLIENT_REQUEST_MAX_ATTEMPTS = "udp.client.retry.maxattempts";
        public static final String ACKNOWLEDGMENT_PACKET_SIZE = "udp.acknowledgement.packet.size";
        public static final String ACKNOWLEDGMENT_TIMEOUT = "udp.acknowledgement.timeout";
        public static final String ACKNOWLEDGMENT_SEQUENCE_NUMBER_INCREMENTATION= "udp.acknowledgement.sequence.incrementation";
        public static final String PACKET_SEQUENCE_BOUND = "udp.packet.sequencenumber.bound";
    }

    public static class Broker{
        public static final String SUBSCRIBERS_MONITOR_DELAY = "broker.subscribers.monitor.delay";
        public static final String SUBSCRIBERS_MONITOR_PERIOD = "broker.subscribers.monitor.period";
        public static final String SUBSCRIBERS_MONITOR_THREADS = "broker.subscribers.monitor.threads";
    }

    public static class RMI{
        public static final String REGISTRY_PORT = "rmi.registry.port";
        public static final String EXPORT_OBJECT_PORT = "rmi.exportobject.port";
    }

}
