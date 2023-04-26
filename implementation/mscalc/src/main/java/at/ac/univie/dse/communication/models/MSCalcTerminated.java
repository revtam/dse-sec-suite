package at.ac.univie.dse.communication.models;

import java.io.Serializable;

public class MSCalcTerminated extends CommunicationEvent implements Serializable {
    public String instanceId;

    public MSCalcTerminated(String instanceId) {
        this.instanceId = instanceId;
        shouldBeIntercepted = true;
    }

    public MSCalcTerminated() {
    }
}
