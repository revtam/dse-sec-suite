package at.ac.univie.dse.msbill;

import at.ac.univie.dse.cs.properties.ApplicationPropertiesCache;
import at.ac.univie.dse.msbill.communication.CommunicationCoordinator;
import at.ac.univie.dse.msbill.communication.MSBillCommProtocol;
import at.ac.univie.dse.msbill.communication.MSBillCommunication;
import at.ac.univie.dse.msbill.data.account.AccountDao;
import at.ac.univie.dse.msbill.data.account.AccountRepository;
import at.ac.univie.dse.msbill.data.account.AccountStorage;
import at.ac.univie.dse.msbill.data.instance.InstanceDataDao;
import at.ac.univie.dse.msbill.data.instance.InstanceDataRepository;
import at.ac.univie.dse.msbill.data.instance.InstanceDataStorage;
import at.ac.univie.dse.msbill.logic.AccountManager;
import at.ac.univie.dse.msbill.logic.LoadBalancer;
import at.ac.univie.dse.msbill.logic.PriceCalculator;
import at.ac.univie.dse.msbill.properties.ApplicationProperties;
import at.ac.univie.dse.msbill.properties.PropertyKeys;

public class MSBillApplication {

	public static void main(String[] args) {
		ApplicationPropertiesCache.getInstance().setProperty("udp.acknowledgement.timeout", "1000");

		// get application.properties values
		ApplicationProperties props = ApplicationProperties.getInstance();
		Double basePrice = props.getDoubleProperty(PropertyKeys.BASE_PRICE);
		Double priceFactor = props.getDoubleProperty(PropertyKeys.PRICE_FACTOR);
		String primaryMsCalcId = props.getProperty(PropertyKeys.PRIMARY);

		// create main logic components
		LoadBalancer loadBalancer = createLoadBalancer(basePrice, priceFactor);
		AccountManager accountManager = createAccountManager();

		// store default first mscalc instance
		loadBalancer.addInstance(primaryMsCalcId, "");

		// create main communication component
		MSBillCommProtocol communicator = new MSBillCommunication();

		// assemble and start service
		CommunicationCoordinator coordinator = new CommunicationCoordinator(communicator, loadBalancer, accountManager);
		coordinator.launch();
	}

	private static LoadBalancer createLoadBalancer(Double basePrice, Double priceFactor) {
		InstanceDataDao dataDao = new InstanceDataStorage();
		InstanceDataRepository dataRepo = InstanceDataRepository.getInstance(dataDao);
		PriceCalculator priceCalc = new PriceCalculator(basePrice, priceFactor);
		return new LoadBalancer(dataRepo, priceCalc);
	}

	private static AccountManager createAccountManager() {
		AccountDao accDao = new AccountStorage();
		AccountRepository accRepo = AccountRepository.getInstance(accDao);
		return new AccountManager(accRepo);
	}

}
