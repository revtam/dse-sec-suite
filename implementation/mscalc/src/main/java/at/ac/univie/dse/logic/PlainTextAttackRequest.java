package at.ac.univie.dse.logic;

public class PlainTextAttackRequest extends Request{
    private String cipherText;
    private String clearText;
    private String id;

    public PlainTextAttackRequest(String id, String requestorId, String cipherText, String clearText) {
        super(requestorId);
        this.cipherText = cipherText;
        this.clearText = clearText;
        this .id = id;
    }

    public String getCipherText() {
        return cipherText;
    }

    public String getClearText() {
        return clearText;
    }

    public String getId() {
        return id;
    }
}
