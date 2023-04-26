package at.ac.univie.dse.cs.broker.exception;

public class SubscriptionNotFoundException extends BrokerBaseException{
    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
