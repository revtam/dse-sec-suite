package at.ac.univie.dse.communication.interfaces;

import at.ac.univie.dse.communication.models.MSCalcTaskResult;

public interface MSCalcTaskResultEventCallback {
    void didReceiveEvent(MSCalcTaskResult event);
}
