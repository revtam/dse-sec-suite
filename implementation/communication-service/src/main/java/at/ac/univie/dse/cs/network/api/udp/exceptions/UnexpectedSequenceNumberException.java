package at.ac.univie.dse.cs.network.api.udp.exceptions;

import at.ac.univie.dse.cs.network.exceptions.NetworkException;


public class UnexpectedSequenceNumberException extends NetworkException {

    public UnexpectedSequenceNumberException(String message) {
        super(message);
    }
}
