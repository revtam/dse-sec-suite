package at.ac.univie.dse.cs.network.exceptions;

public class NetworkException extends Exception{

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message,Throwable t) {
        super(message,t);
    }
}
