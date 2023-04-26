package at.ac.univie.dse.cs.network.api.udp.client.handler;

public class UnreachableDestinationException extends RuntimeException {
    public UnreachableDestinationException(String unreachableDestination) {
        super(unreachableDestination);
    }
}
