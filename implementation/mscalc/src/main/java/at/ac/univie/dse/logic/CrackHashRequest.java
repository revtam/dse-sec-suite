package at.ac.univie.dse.logic;

public class CrackHashRequest extends Request{
    private String hash;
    private String id;

    public CrackHashRequest(String requestorId, String hash, String id) {
        super(requestorId);
        this.hash = hash;
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public String getId() {
        return id;
    }
}
