package at.ac.univie.dse.cs.network.api.udp.exceptions;

import at.ac.univie.dse.cs.network.exceptions.NetworkException;

public class DuplicatePacketException extends NetworkException {

    public DuplicatePacketException(String message) {
        super(message);
    }
}
