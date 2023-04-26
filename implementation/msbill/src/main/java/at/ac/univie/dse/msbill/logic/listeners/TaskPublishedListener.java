package at.ac.univie.dse.msbill.logic.listeners;

import java.util.function.Consumer;
import java.util.logging.Logger;

import at.ac.univie.dse.msbill.communication.converters.TaskPublishedConverter;
import at.ac.univie.dse.msbill.communication.metadata.EventMetaDataCollection;
import at.ac.univie.dse.msbill.communication.networkobjects.incoming.MSCalcTaskCreated;
import at.ac.univie.dse.msbill.data.datatypes.MSCalcReceivedTask;
import at.ac.univie.dse.msbill.logic.AccountManager;
import at.ac.univie.dse.msbill.logic.LoadBalancer;

public class TaskPublishedListener implements Consumer<Object> {

	private TaskPublishedConverter converter;
	private LoadBalancer loadBalancer;
	private AccountManager accountManager;
	private static Logger logger = Logger.getLogger(TaskPublishedListener.class.getName());

	public TaskPublishedListener(TaskPublishedConverter converter, LoadBalancer loadBalancer,
			AccountManager accountManager) {
		this.converter = converter;
		this.loadBalancer = loadBalancer;
		this.accountManager = accountManager;
	}

	@Override
	public void accept(Object t) {
		logger.info(EventMetaDataCollection.getTaskPublishedListenerMetaData().getEventName() + " event received");
		MSCalcTaskCreated networkObject = (MSCalcTaskCreated) t;
		MSCalcReceivedTask receivedTask = converter.convertFromNetworkObject(networkObject);
		accountManager.addClnt(receivedTask.getClntInstanceId());
		Double price = loadBalancer.getPrice(receivedTask.getMsCalcInstanceId());
		accountManager.addCost(receivedTask.getClntInstanceId(), price);
		loadBalancer.incrementLoad(receivedTask.getMsCalcInstanceId());
	}

}
