package at.ac.univie.dse.communication.interfaces;

import at.ac.univie.dse.communication.models.MSCalcTaskCreated;

public interface EventTaskCallback {
    void didReceiveEventTask(MSCalcTaskCreated event);
}
