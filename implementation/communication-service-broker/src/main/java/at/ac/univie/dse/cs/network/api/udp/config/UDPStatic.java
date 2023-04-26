package at.ac.univie.dse.cs.network.api.udp.config;


public class UDPStatic {


    public static class ExceptionsMessages{
        public static final String DUPLICATE_PACKET = "Packet was already received and processed by the listener. ";
        public static final String UNEXPECTED_ACKNOWLEDGEMENT_SOURCE = "Unexpected acknowledgment source Address/port.";
        public static final String UNEXPECTED_ACKNOWLEDGEMENT_SEQUENCE_NUMBER = "Unexpected acknowledgment sequence number.";
        public static final String UNEXPECTED_ACKNOWLEDGEMENT_PACKET_ID = "Unexpected acknowledgment packet ID.";
        public static final String UNREACHABLE_DESTINATION = "destination server socket couldn't be reached. ";
        public static final String PACKET_TRANSMISSION_EXCEPTION = "An error occurred while transferring the UDP packet.";
    }

    public static class PacketDataDelimiters{
        public static final String TOKENS_SEPARATION_KEY = "<###>";
        public static final String KEY_VALUE_SEPARATION_KEY = ":";
    }

    public static class NetworkBaseKeys{
        public static final String SEQUENCE_NUMBER_KEY = "seqNumber";
        public static final String PACKET_ID_KEY = "packetId";
        public static final String SOURCE_IP_ADDRESS = "sourceIp";
        public static final String SOURCE_PORT = "sourcePort";
        public static final String DESTINATION_IP_ADDRESS = "destinationIp";
        public static final String DESTINATION_PORT = "destinationPort";

    }

    public static class Configurations{
        public static final int ACK_SEQUENCE_NUMBER_INCREMENTATION_VALUE = 1;
        public static final int REQUEST_ID_QUEUE_SIZE = 100;
    }

}
