package at.ac.univie.dse.communication;

import java.io.Serializable;

import at.ac.univie.dse.communication.models.EventMetaData;

public class EventsDispatcherMetadata implements Serializable {
    public EventMetaData generateMSCalcCreatedEventMetaData() {
        return new EventMetaData("MS_CALC_CREATION", "msbill");
    }

    public EventMetaData generateMSCalcTerminatedEventMetaData() {
        return new EventMetaData("MS_CALC_TERMINATION", "msbill");
    }

    public EventMetaData generateMSCalcTaskResultEventMetaData(String clntId) {
        return new EventMetaData("CLNT_TASK_RESULT", clntId);
    }

    public EventMetaData generateMSCalcTaskCreatedEventMetaData() {
        return new EventMetaData("CLNT_TASK", "*");
    }
}