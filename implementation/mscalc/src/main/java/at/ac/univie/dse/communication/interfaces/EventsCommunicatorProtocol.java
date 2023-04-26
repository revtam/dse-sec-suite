package at.ac.univie.dse.communication.interfaces;

import at.ac.univie.dse.communication.models.MSCalcTaskCreated;
import at.ac.univie.dse.communication.models.MSCalcCreated;
import at.ac.univie.dse.communication.models.MSCalcTaskResult;
import at.ac.univie.dse.communication.models.MSCalcTerminated;


public interface EventsCommunicatorProtocol {
    void publish(MSCalcCreated event);
    void publish(MSCalcTerminated event);
    void publish(MSCalcTaskResult event);
    void publish(MSCalcTaskCreated task);

    void registerCallback(EventTaskCallback eventTaskCallback);
    void registerCallback(MSCalcCreatedEventCallback eventCallback);
    void registerCallback(MSCalcTaskResultEventCallback eventCallback);
}
