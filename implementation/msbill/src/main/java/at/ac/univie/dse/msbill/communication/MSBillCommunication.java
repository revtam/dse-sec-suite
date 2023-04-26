package at.ac.univie.dse.msbill.communication;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import at.ac.univie.dse.cs.ms.publishagent.PublishTemplate;
import at.ac.univie.dse.cs.network.api.invoker.InvocationEntry;
import at.ac.univie.dse.msbill.communication.metadata.EventMetaData;
import at.ac.univie.dse.msbill.communication.metadata.EventMetaDataCollection;
import at.ac.univie.dse.msbill.communication.networkobjects.incoming.MSCalcCreated;
import at.ac.univie.dse.msbill.communication.networkobjects.incoming.MSCalcTerminated;
import at.ac.univie.dse.msbill.communication.networkobjects.incoming.MSCalcTaskCreated;
import at.ac.univie.dse.msbill.communication.networkobjects.incoming.MSCalcTaskResult;
import at.ac.univie.dse.msbill.communication.networkobjects.outgoing.MSCalcPrices;
import at.ac.univie.dse.msbill.logic.listeners.MSCalcCreatedListener;
import at.ac.univie.dse.msbill.logic.listeners.MSCalcTerminatedListener;
import at.ac.univie.dse.msbill.logic.listeners.TaskPublishedListener;
import at.ac.univie.dse.msbill.logic.listeners.TaskResultListener;

public class MSBillCommunication implements MSBillCommProtocol {

	private Set<InvocationEntry> invocationEntries;
	private PublishTemplate publishTemplate;

	public MSBillCommunication() {
		this.invocationEntries = new HashSet<>();
	}

	@Override
	public void setMsCalcCreatedListener(MSCalcCreatedListener listener) {
		EventMetaData eventMetaData = EventMetaDataCollection.getMSCalcCreatedListenerMetaData();
		buildInvocationEntry(eventMetaData.getEventName(), eventMetaData.getEventTarget(), MSCalcCreated.class,
				listener);
	}

	@Override
	public void setMsCalcTerminatedListener(MSCalcTerminatedListener listener) {
		EventMetaData eventMetaData = EventMetaDataCollection.getMSCalcTerminatedListenerMetaData();
		buildInvocationEntry(eventMetaData.getEventName(), eventMetaData.getEventTarget(), MSCalcTerminated.class,
				listener);
	}

	@Override
	public void setTaskPublishedListener(TaskPublishedListener listener) {
		EventMetaData eventMetaData = EventMetaDataCollection.getTaskPublishedListenerMetaData();
		buildInvocationEntry(eventMetaData.getEventName(), eventMetaData.getEventTarget(), MSCalcTaskCreated.class,
				listener);
	}

	@Override
	public void setTaskResultListener(TaskResultListener listener) {
		EventMetaData eventMetaData = EventMetaDataCollection.getTaskResultListenerMetaData();
		buildInvocationEntry(eventMetaData.getEventName(), eventMetaData.getEventTarget(), MSCalcTaskResult.class, listener);
	}

	@Override
	public Set<InvocationEntry> getInvocationEntries() {
		return invocationEntries;
	}

	@Override
	public void publishPriceReport(MSCalcPrices messageObject) {
		EventMetaData eventMetaData = EventMetaDataCollection.getMSCalcPricesPublisherMetaData();
		publishMessage(eventMetaData.getEventName(), eventMetaData.getEventTarget(), messageObject.getClass(),
				messageObject);
	}

	@Override
	public void setPublishTemplate(PublishTemplate publishTemplate) {
		this.publishTemplate = publishTemplate;
	}

	private void buildInvocationEntry(String eventName, String publishTarget, Class networkObjectClass,
			Consumer<Object> listener) {
		InvocationEntry invocation = new InvocationEntry(eventName, publishTarget, networkObjectClass.getName(),
				listener);
		invocationEntries.add(invocation);
	}

	private void publishMessage(String eventName, String publishTarget, Class<?> messageClass, Object messageObject) {
		if (publishTemplate == null) {
			throw new RuntimeException("PublishTemplate has to be set in order to be able to publish");
		}
		publishTemplate.publish(messageClass.cast(messageObject), eventName, publishTarget);
	}

}
