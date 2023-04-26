package at.ac.univie.dse.communication.interfaces;

import at.ac.univie.dse.communication.models.MSCalcCreated;

public interface MSCalcCreatedEventCallback {
    void didReceiveEvent(MSCalcCreated event);
}
