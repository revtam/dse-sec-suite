package at.ac.univie.dse.cs.network.api.udp.exceptions;

import at.ac.univie.dse.cs.network.exceptions.NetworkException;

public class UnexpectedSourceException extends NetworkException {

    public UnexpectedSourceException(String message) {
        super(message);
    }
}
