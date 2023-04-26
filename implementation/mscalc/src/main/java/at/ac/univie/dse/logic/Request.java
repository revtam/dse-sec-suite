package at.ac.univie.dse.logic;
import java.util.UUID;

public class Request {
    private String requestorId;
    private String id;

    public Request(String requestorId) {
        this.requestorId = requestorId;
        this.id = UUID.randomUUID().toString();
    }

    public String getRequestorId() {
        return requestorId;
    }

    public String getId() {
        return id;
    }
}
