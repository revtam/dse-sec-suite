package at.ac.univie.dse.cs.network.api.udp.exceptions;

import at.ac.univie.dse.cs.network.exceptions.NetworkException;

public class UnexpectedPacketIDException extends NetworkException {
    public UnexpectedPacketIDException(String message) {
        super(message);
    }
}
