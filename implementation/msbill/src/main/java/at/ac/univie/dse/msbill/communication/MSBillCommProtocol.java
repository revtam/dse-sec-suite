package at.ac.univie.dse.msbill.communication;

import java.util.Set;

import at.ac.univie.dse.cs.ms.publishagent.PublishTemplate;
import at.ac.univie.dse.cs.network.api.invoker.InvocationEntry;
import at.ac.univie.dse.msbill.communication.networkobjects.outgoing.MSCalcPrices;
import at.ac.univie.dse.msbill.logic.listeners.MSCalcCreatedListener;
import at.ac.univie.dse.msbill.logic.listeners.MSCalcTerminatedListener;
import at.ac.univie.dse.msbill.logic.listeners.TaskPublishedListener;
import at.ac.univie.dse.msbill.logic.listeners.TaskResultListener;

public interface MSBillCommProtocol {

	void setMsCalcCreatedListener(MSCalcCreatedListener listener);

	void setMsCalcTerminatedListener(MSCalcTerminatedListener listener);

	void setTaskPublishedListener(TaskPublishedListener listener);

	void setTaskResultListener(TaskResultListener listener);

	Set<InvocationEntry> getInvocationEntries();

	void publishPriceReport(MSCalcPrices prices);

	void setPublishTemplate(PublishTemplate publishTemplate);

}