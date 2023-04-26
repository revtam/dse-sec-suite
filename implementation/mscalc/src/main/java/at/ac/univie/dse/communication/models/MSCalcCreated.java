package at.ac.univie.dse.communication.models;


import java.io.Serializable;

public class MSCalcCreated extends CommunicationEvent implements Serializable {
    public String instanceId;
    public MSCalcCreated(String instanceId) {
        this.instanceId = instanceId;
        shouldBeIntercepted = true;
    }

    public MSCalcCreated() {
    }

    public String getInstanceId() {
        return instanceId;
    }
}
