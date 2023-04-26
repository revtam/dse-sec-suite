package at.ac.univie.dse.msbill.data.instance;

import java.util.List;

import at.ac.univie.dse.msbill.data.datatypes.InstanceData;
import at.ac.univie.dse.msbill.data.datatypes.PriceReport;
import at.ac.univie.dse.msbill.exception.ExistingKeyException;
import at.ac.univie.dse.msbill.exception.MissingKeyException;

public class InstanceDataRepository {

	private static InstanceDataRepository repoInstance = null;

	private InstanceDataDao dao;

	private InstanceDataRepository(InstanceDataDao dao) {
		this.dao = dao;
	}

	public static InstanceDataRepository getInstance(InstanceDataDao dao) {
		synchronized (InstanceDataRepository.class) {
			if (repoInstance == null) {
				repoInstance = new InstanceDataRepository(dao);
			}
		}
		return repoInstance;
	}

	public boolean isInstanceIdPresent(String msCalcInstanceId) {
		return dao.getInstanceData(msCalcInstanceId) != null;
	}

	/**
	 * @throws ExistingKeyException if msCalcInstanceId is already present in the
	 *                              database
	 */
	public void createInstanceData(String msCalcInstanceId, String taskType, Double price) {
		if (isInstanceIdPresent(msCalcInstanceId)) {
			throw new ExistingKeyException(msCalcInstanceId);
		}
		InstanceData newInstanceData = new InstanceData(msCalcInstanceId, taskType, price);
		dao.createInstanceData(newInstanceData);
	}

	/**
	 * @throws MissingKeyException if msCalcInstanceId does not exist in the
	 *                             database
	 */
	public void setInstanceLoad(String msCalcInstanceId, int load) {
		InstanceData instanceData = retrieveInstanceDataOrThrowException(msCalcInstanceId);
		instanceData.setTasksInQueue(load);
		dao.updateInstaceData(instanceData);
	}

	/**
	 * @throws MissingKeyException if msCalcInstanceId does not exist in the
	 *                             database
	 */
	public void setInstancePrice(String msCalcInstanecId, Double price) {
		InstanceData instanceData = retrieveInstanceDataOrThrowException(msCalcInstanecId);
		instanceData.setPrice(price);
		dao.updateInstaceData(instanceData);
	}

	/**
	 * @throws MissingKeyException if msCalcInstanceId does not exist in the
	 *                             database
	 */
	public void removeInstance(String msCalcInstanceId) {
		InstanceData instanceData = retrieveInstanceDataOrThrowException(msCalcInstanceId);
		dao.deleteInstanceData(instanceData);
	}

	/**
	 * @throws MissingKeyException if msCalcInstanceId does not exist in the
	 *                             database
	 */
	public int getInstanceLoad(String msCalcInstanceId) {
		return retrieveInstanceDataOrThrowException(msCalcInstanceId).getTasksInQueue();
	}

	/**
	 * @throws MissingKeyException if msCalcInstanceId does not exist in the
	 *                             database
	 */
	public Double getInstancePrice(String msCalcInstanceId) {
		return retrieveInstanceDataOrThrowException(msCalcInstanceId).getPrice();
	}

	/**
	 * @throws MissingKeyException if msCalcInstanceId does not exist in the
	 *                             database
	 */
	public InstanceData getInstanceData(String msCalcInstanceData) {
		return retrieveInstanceDataOrThrowException(msCalcInstanceData);
	}

	/**
	 * @return PriceReport instance that contains data
	 */
	public PriceReport getPriceReport() {
		return new PriceReport(dao.getAllInstanceData());
	}

	public List<InstanceData> getAllInstanceData() {
		return dao.getAllInstanceData();
	}

	private InstanceData retrieveInstanceDataOrThrowException(String instanceId) {
		if (!isInstanceIdPresent(instanceId)) {
			throw new MissingKeyException(instanceId);
		}
		return dao.getInstanceData(instanceId);
	}

}
