package at.ac.univie.dse.msbill.data.instance;

import java.util.List;

import at.ac.univie.dse.msbill.data.datatypes.InstanceData;

public interface InstanceDataDao {

	void createInstanceData(InstanceData data);

	void updateInstaceData(InstanceData data);

	void deleteInstanceData(InstanceData data);

	List<InstanceData> getAllInstanceData();

	/**
	 * @return InstanceData if msCalcInstanceId exists in database, otherwise null
	 */
	InstanceData getInstanceData(String instanceId);

}
