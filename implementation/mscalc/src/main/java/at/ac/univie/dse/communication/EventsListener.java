package at.ac.univie.dse.communication;

import at.ac.univie.dse.communication.interfaces.EventTaskCallback;
import at.ac.univie.dse.communication.interfaces.MSCalcCreatedEventCallback;
import at.ac.univie.dse.communication.interfaces.MSCalcTaskResultEventCallback;
import at.ac.univie.dse.communication.models.MSCalcTaskCreated;
import at.ac.univie.dse.communication.models.MSCalcCreated;
import at.ac.univie.dse.communication.models.MSCalcTaskResult;
import at.ac.univie.dse.cs.network.api.invoker.InvocationEntry;


import java.util.Set;

public class EventsListener {
    private EventTaskCallback eventTaskCallback;
    private MSCalcCreatedEventCallback msCalcCreatedEventCallback;
    private MSCalcTaskResultEventCallback msCalcTaskResultEventCallback;

    private InvocationEntry generateEventTaskInvocationEntry() {
        return new InvocationEntry("CLNT_TASK","*", MSCalcTaskCreated.class.getName(), o -> {
            MSCalcTaskCreated task = (MSCalcTaskCreated) o;
            if (eventTaskCallback != null && task.shouldBeIntercepted) {
                eventTaskCallback.didReceiveEventTask(task);
            }
        });
    }

    private InvocationEntry generateMSCalcCreatedInvocationEntry() {
        return new InvocationEntry("MS_CALC_CREATION","msbill", MSCalcCreated.class.getName(), o -> {
            MSCalcCreated event = (MSCalcCreated) o;
            if (msCalcCreatedEventCallback != null && event.shouldBeIntercepted) {
                msCalcCreatedEventCallback.didReceiveEvent(event);
            }
        });
    }

    private InvocationEntry generateMSCalcTaskResultInvocationEntry() {
        return new InvocationEntry("CLNT_TASK_RESULT","*", MSCalcTaskResult.class.getName(), o -> {
            MSCalcTaskResult result = (MSCalcTaskResult) o;
            if (msCalcTaskResultEventCallback != null && result.shouldBeIntercepted) {
                msCalcTaskResultEventCallback.didReceiveEvent(result);
            }
        });
    }

    public Set<InvocationEntry> generateInvocationEntries() {
        return Set.of(generateEventTaskInvocationEntry(),
                generateMSCalcCreatedInvocationEntry(),
                generateMSCalcTaskResultInvocationEntry());
    }

    public void registerCallback(EventTaskCallback eventTaskCallback) {
        this.eventTaskCallback = eventTaskCallback;
    }

    public void registerCallback(MSCalcCreatedEventCallback msCalcCreatedEventCallback) { this.msCalcCreatedEventCallback = msCalcCreatedEventCallback; }

    public void registerCallback(MSCalcTaskResultEventCallback msCalcTaskResultEventCallback) { this.msCalcTaskResultEventCallback = msCalcTaskResultEventCallback; }
}
