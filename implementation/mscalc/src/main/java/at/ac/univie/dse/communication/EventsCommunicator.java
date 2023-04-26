package at.ac.univie.dse.communication;

import java.io.Serializable;

import at.ac.univie.dse.communication.interfaces.EventTaskCallback;
import at.ac.univie.dse.communication.interfaces.EventsCommunicatorProtocol;
import at.ac.univie.dse.communication.interfaces.MSCalcCreatedEventCallback;
import at.ac.univie.dse.communication.interfaces.MSCalcTaskResultEventCallback;
import at.ac.univie.dse.communication.models.EventMetaData;
import at.ac.univie.dse.communication.models.MSCalcCreated;
import at.ac.univie.dse.communication.models.MSCalcTaskCreated;
import at.ac.univie.dse.communication.models.MSCalcTaskResult;
import at.ac.univie.dse.communication.models.MSCalcTerminated;
import at.ac.univie.dse.cs.ms.publishagent.PublishTemplate;
import at.ac.univie.dse.cs.properties.NetworkProtocol;
import at.ac.univie.dse.cs.starter.ServiceApplicationStarter;

public class EventsCommunicator implements EventsCommunicatorProtocol, Serializable {
	private PublishTemplate publishTemplate;
	private EventsListener eventsListener = new EventsListener();
	private EventsDispatcherMetadata eventsDispatcherMetadata = new EventsDispatcherMetadata();

	public EventsCommunicator() {
		publishTemplate = ServiceApplicationStarter.run(NetworkProtocol.UDP,
				eventsListener.generateInvocationEntries());
	}

	@Override
	public void publish(MSCalcCreated event) {
		EventMetaData metaData = eventsDispatcherMetadata.generateMSCalcCreatedEventMetaData();
		publishTemplate.publish(event, metaData.getEventName(), metaData.getEventTarget());
	}

	@Override
	public void publish(MSCalcTerminated event) {
		EventMetaData metaData = eventsDispatcherMetadata.generateMSCalcTerminatedEventMetaData();
		publishTemplate.publish(event, metaData.getEventName(), metaData.getEventTarget());
	}

	@Override
	public void publish(MSCalcTaskResult event) {
		EventMetaData metaData = eventsDispatcherMetadata.generateMSCalcTaskResultEventMetaData(event.getClientId());
		publishTemplate.publish(event, metaData.getEventName(), metaData.getEventTarget());
	}

	@Override
	public void publish(MSCalcTaskCreated task) {
		EventMetaData metaData = eventsDispatcherMetadata.generateMSCalcTaskCreatedEventMetaData();
		publishTemplate.publish(task, metaData.getEventName(), metaData.getEventTarget());
	}

	@Override
	public void registerCallback(EventTaskCallback eventTaskCallback) {
		eventsListener.registerCallback(eventTaskCallback);
	}

	@Override
	public void registerCallback(MSCalcCreatedEventCallback eventCallback) {
		eventsListener.registerCallback(eventCallback);
	}

	@Override
	public void registerCallback(MSCalcTaskResultEventCallback eventCallback) {
		eventsListener.registerCallback(eventCallback);
	}
}
