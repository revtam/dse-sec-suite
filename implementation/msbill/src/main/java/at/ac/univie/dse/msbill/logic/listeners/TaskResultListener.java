package at.ac.univie.dse.msbill.logic.listeners;

import java.util.function.Consumer;
import java.util.logging.Logger;

import at.ac.univie.dse.msbill.communication.converters.TaskResultConverter;
import at.ac.univie.dse.msbill.communication.metadata.EventMetaDataCollection;
import at.ac.univie.dse.msbill.communication.networkobjects.incoming.MSCalcTaskResult;
import at.ac.univie.dse.msbill.data.datatypes.MSCalcCompletedTask;
import at.ac.univie.dse.msbill.logic.LoadBalancer;

public class TaskResultListener implements Consumer<Object> {

	private TaskResultConverter converter;
	private LoadBalancer loadBalancer;
	private static Logger logger = Logger.getLogger(TaskResultListener.class.getName());

	public TaskResultListener(TaskResultConverter converter, LoadBalancer loadBalancer) {
		this.converter = converter;
		this.loadBalancer = loadBalancer;
	}

	@Override
	public void accept(Object t) {
		logger.info(EventMetaDataCollection.getTaskResultListenerMetaData().getEventName() + " event received");
		MSCalcTaskResult networkObject = (MSCalcTaskResult) t;
		MSCalcCompletedTask completedTask = converter.convertFromNetworkObject(networkObject);
		loadBalancer.decrementLoad(completedTask.getMsCalcInstanceId());
	}

}
