package at.ac.univie.dse.cs.network.api.udp.exceptions;

import at.ac.univie.dse.cs.network.exceptions.NetworkException;

public class PacketTransmissionException extends NetworkException {
    public PacketTransmissionException(String message) {
        super(message);
    }
    public PacketTransmissionException(String message, Throwable t) {
        super(message,t);
    }
}