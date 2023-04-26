package at.ac.univie.dse.msbill.logic.listeners;

import java.util.function.Consumer;
import java.util.logging.Logger;

import at.ac.univie.dse.msbill.communication.converters.MSCalcTerminatedConverter;
import at.ac.univie.dse.msbill.communication.metadata.EventMetaDataCollection;
import at.ac.univie.dse.msbill.communication.networkobjects.incoming.MSCalcTerminated;
import at.ac.univie.dse.msbill.data.datatypes.MSCalcTermination;
import at.ac.univie.dse.msbill.logic.LoadBalancer;

public class MSCalcTerminatedListener implements Consumer<Object> {

	private MSCalcTerminatedConverter converter;
	private LoadBalancer loadBalancer;
	private static Logger logger = Logger.getLogger(MSCalcTerminatedListener.class.getName());

	public MSCalcTerminatedListener(MSCalcTerminatedConverter converter, LoadBalancer loadBalancer) {
		this.converter = converter;
		this.loadBalancer = loadBalancer;
	}

	@Override
	public void accept(Object t) {
		logger.info(EventMetaDataCollection.getMSCalcTerminatedListenerMetaData().getEventName() + " event received");
		MSCalcTerminated networkObject = (MSCalcTerminated) t;
		MSCalcTermination termination = converter.convertFromNetworkObject(networkObject);
		loadBalancer.terminateInstance(termination.getMsCalcInstanceId());
	}

}
