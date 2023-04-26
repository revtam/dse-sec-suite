package at.ac.univie.dse.msbill.data.instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.ac.univie.dse.msbill.data.datatypes.InstanceData;

public class InstanceDataStorage implements InstanceDataDao {

	private Map<String, InstanceData> instances;

	public InstanceDataStorage(Map<String, InstanceData> instances) {
		this.instances = instances;
	}

	public InstanceDataStorage() {
		this(new HashMap<>());
	}

	@Override
	public synchronized void createInstanceData(InstanceData data) {
		instances.put(data.getMsCalcInstanceId(), data);
	}

	@Override
	public synchronized void updateInstaceData(InstanceData data) {
		instances.put(data.getMsCalcInstanceId(), data);
	}

	@Override
	public void deleteInstanceData(InstanceData data) {
		instances.remove(data.getMsCalcInstanceId());
	}

	@Override
	public synchronized List<InstanceData> getAllInstanceData() {
		return new ArrayList<>(instances.values());
	}

	@Override
	public synchronized InstanceData getInstanceData(String instanceId) {
		return instances.get(instanceId);
	}

}
