package at.ac.univie.dse.msbill.communication.converters;

import at.ac.univie.dse.msbill.communication.networkobjects.incoming.MSCalcTaskCreated;
import at.ac.univie.dse.msbill.data.datatypes.MSCalcReceivedTask;

public class TaskPublishedConverter extends ObjectConverter<MSCalcTaskCreated, MSCalcReceivedTask> {

	public TaskPublishedConverter() {
		super(null, nTaskPublished -> {
			return new MSCalcReceivedTask(nTaskPublished.getInstanceId(), nTaskPublished.getClientId());
		});
	}

}
