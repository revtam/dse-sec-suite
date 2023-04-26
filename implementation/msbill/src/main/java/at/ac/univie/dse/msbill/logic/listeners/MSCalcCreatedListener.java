package at.ac.univie.dse.msbill.logic.listeners;

import java.util.function.Consumer;
import java.util.logging.Logger;

import at.ac.univie.dse.msbill.communication.converters.MSCalcCreatedConverter;
import at.ac.univie.dse.msbill.communication.metadata.EventMetaDataCollection;
import at.ac.univie.dse.msbill.communication.networkobjects.incoming.MSCalcCreated;
import at.ac.univie.dse.msbill.data.datatypes.MSCalcCreation;
import at.ac.univie.dse.msbill.logic.LoadBalancer;

public class MSCalcCreatedListener implements Consumer<Object> {

	private MSCalcCreatedConverter converter;
	private LoadBalancer loadBalancer;
	private static Logger logger = Logger.getLogger(MSCalcCreatedListener.class.getName());

	public MSCalcCreatedListener(MSCalcCreatedConverter converter, LoadBalancer loadBalancer) {
		this.converter = converter;
		this.loadBalancer = loadBalancer;
	}

	@Override
	public void accept(Object t) {
		logger.info(EventMetaDataCollection.getMSCalcCreatedListenerMetaData().getEventName() + " event received");
		MSCalcCreated networkObject = (MSCalcCreated) t;
		MSCalcCreation creation = converter.convertFromNetworkObject(networkObject);
		loadBalancer.addInstance(creation.getNewMSCalcInstanceId(), creation.getTaskType());
	}

}
