package at.ac.univie.dse.msbill.communication;

import at.ac.univie.dse.cs.ms.publishagent.PublishTemplate;
import at.ac.univie.dse.cs.properties.NetworkProtocol;
import at.ac.univie.dse.cs.starter.ServiceApplicationStarter;
import at.ac.univie.dse.msbill.communication.converters.MSCalcCreatedConverter;
import at.ac.univie.dse.msbill.communication.converters.MSCalcPricesConverter;
import at.ac.univie.dse.msbill.communication.converters.MSCalcTerminatedConverter;
import at.ac.univie.dse.msbill.communication.converters.TaskPublishedConverter;
import at.ac.univie.dse.msbill.communication.converters.TaskResultConverter;
import at.ac.univie.dse.msbill.logic.AccountManager;
import at.ac.univie.dse.msbill.logic.LoadBalancer;
import at.ac.univie.dse.msbill.logic.PriceReportDispatcher;
import at.ac.univie.dse.msbill.logic.PriceReportScheduler;
import at.ac.univie.dse.msbill.logic.listeners.MSCalcCreatedListener;
import at.ac.univie.dse.msbill.logic.listeners.MSCalcTerminatedListener;
import at.ac.univie.dse.msbill.logic.listeners.TaskPublishedListener;
import at.ac.univie.dse.msbill.logic.listeners.TaskResultListener;
import at.ac.univie.dse.msbill.logic.publishers.PriceReportPublisher;
import at.ac.univie.dse.msbill.properties.ApplicationProperties;
import at.ac.univie.dse.msbill.properties.PropertyKeys;

public class CommunicationCoordinator {

	private MSBillCommProtocol communicator;
	private LoadBalancer loadBalancer;
	private AccountManager accountManager;

	public CommunicationCoordinator(MSBillCommProtocol communicator, LoadBalancer loadBalancer,
			AccountManager accountManager) {
		this.communicator = communicator;
		this.loadBalancer = loadBalancer;
		this.accountManager = accountManager;
	}

	public void launch() {
		// create incoming message handlers
		MSCalcCreatedListener msCalcCreatedListener = new MSCalcCreatedListener(new MSCalcCreatedConverter(),
				loadBalancer);
		MSCalcTerminatedListener msCalcterminatedListener = new MSCalcTerminatedListener(
				new MSCalcTerminatedConverter(), loadBalancer);
		TaskPublishedListener taskPublishedListener = new TaskPublishedListener(new TaskPublishedConverter(),
				loadBalancer, accountManager);
		TaskResultListener taskResultListener = new TaskResultListener(new TaskResultConverter(), loadBalancer);

		// set incoming message handlers
		communicator.setMsCalcCreatedListener(msCalcCreatedListener);
		communicator.setMsCalcTerminatedListener(msCalcterminatedListener);
		communicator.setTaskPublishedListener(taskPublishedListener);
		communicator.setTaskResultListener(taskResultListener);

		// start communication service
		PublishTemplate publishTemplate = ServiceApplicationStarter.run(NetworkProtocol.UDP,
				communicator.getInvocationEntries());
		communicator.setPublishTemplate(publishTemplate);

		// create outgoing message handler
		PriceReportPublisher priceReportPublisher = new PriceReportPublisher(communicator, new MSCalcPricesConverter());

		// create and start periodical dispatching
		Runnable priceReportDispatcher = new PriceReportDispatcher(priceReportPublisher, loadBalancer);
		long delayMillisec = ApplicationProperties.getInstance().getLongProperty(PropertyKeys.SCHEDULER_PERIOD);
		PriceReportScheduler priceScheduler = new PriceReportScheduler(priceReportDispatcher, delayMillisec);
		priceScheduler.start();
	}

}
