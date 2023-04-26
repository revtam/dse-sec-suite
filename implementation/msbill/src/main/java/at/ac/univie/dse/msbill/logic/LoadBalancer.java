package at.ac.univie.dse.msbill.logic;

import java.util.List;
import java.util.logging.Logger;

import at.ac.univie.dse.msbill.data.datatypes.InstanceData;
import at.ac.univie.dse.msbill.data.datatypes.PriceReport;
import at.ac.univie.dse.msbill.data.instance.InstanceDataRepository;
import at.ac.univie.dse.msbill.exception.ExistingKeyException;
import at.ac.univie.dse.msbill.exception.MissingKeyException;

public class LoadBalancer {

	private InstanceDataRepository instanceDataRepo;
	private PriceCalculator priceCalculator;
	private static Logger logger = Logger.getLogger(LoadBalancer.class.getName());

	public LoadBalancer(InstanceDataRepository instanceDataRepo, PriceCalculator priceCalculator) {
		this.instanceDataRepo = instanceDataRepo;
		this.priceCalculator = priceCalculator;
	}

	/**
	 * @throws ExistingKeyException if msCalcInstanceId is already present in the
	 *                              database
	 */
	public void addInstance(String msCalcInstanceId, String taskType) {
		Double basePrice = priceCalculator.calculate(0);
		instanceDataRepo.createInstanceData(msCalcInstanceId, taskType, basePrice);
		logger.info("New MSCalc instance registered with id " + msCalcInstanceId);
	}

	/**
	 * @throws MissingKeyException if msCalcInstanceId does not exist in the
	 *                             database
	 */
	public void incrementLoad(String msCalcInstanceId) {
		int newLoad = instanceDataRepo.getInstanceLoad(msCalcInstanceId) + 1;
		Double newPrice = priceCalculator.calculate(newLoad);
		instanceDataRepo.setInstanceLoad(msCalcInstanceId, newLoad);
		instanceDataRepo.setInstancePrice(msCalcInstanceId, newPrice);
		logger.info("MSCalc instance with id " + msCalcInstanceId + " received a task, new load value = " + newLoad
				+ ", new price = " + newPrice);
	}

	/**
	 * Decrements load value by 1 but in case the value would be less than 0, it is
	 * reset to 0 and an exception is thrown.
	 * 
	 * @throws MissingKeyException if msCalcInstanceId does not exist in the
	 *                             database
	 * @throws RuntimeException    if load value is below 0 after decrementing
	 * 
	 */
	public void decrementLoad(String msCalcInstanceId) {
		int load = instanceDataRepo.getInstanceLoad(msCalcInstanceId);
		int newLoad = --load < 0 ? 0 : load;
		Double newPrice = priceCalculator.calculate(newLoad);
		instanceDataRepo.setInstanceLoad(msCalcInstanceId, newLoad);
		instanceDataRepo.setInstancePrice(msCalcInstanceId, newPrice);

		if (load < 0) {
			logger.warning("Load value was decremented to " + load + " for instance " + msCalcInstanceId
					+ " load value was reset to " + newLoad);
			throw new RuntimeException(
					"Load value for instance " + msCalcInstanceId + " got below 0 after decrementing");
		} else {
			logger.info("MSCalc instance with id " + msCalcInstanceId + " completed a task, new load value = " + newLoad
					+ ", new price = " + newPrice);
		}
	}

	/**
	 * @throws MissingKeyException if msCalcInstanceId does not exist in the
	 *                             database
	 */
	public void terminateInstance(String msCalcInstanceId) {
		instanceDataRepo.removeInstance(msCalcInstanceId);
		logger.info("MSCalc instance with id " + msCalcInstanceId + " was terminated");
	}

	/**
	 * @throws MissingKeyException if msCalcInstanceId does not exist in the
	 *                             database
	 */
	public Double getPrice(String msCalcInstanceId) {
		return instanceDataRepo.getInstancePrice(msCalcInstanceId);
	}

	public PriceReport getPriceReport() {
		return instanceDataRepo.getPriceReport();
	}

	/**
	 * @throws MissingKeyException if msCalcInstanceId does not exist in the
	 *                             database
	 */
	public InstanceData getInstance(String msCalcInstanceId) {
		return instanceDataRepo.getInstanceData(msCalcInstanceId);
	}

	public List<InstanceData> getAllInstances() {
		return instanceDataRepo.getAllInstanceData();
	}

}
