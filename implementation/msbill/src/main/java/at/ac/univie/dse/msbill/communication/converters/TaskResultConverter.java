package at.ac.univie.dse.msbill.communication.converters;

import at.ac.univie.dse.msbill.communication.networkobjects.incoming.MSCalcTaskResult;
import at.ac.univie.dse.msbill.data.datatypes.MSCalcCompletedTask;

public class TaskResultConverter extends ObjectConverter<MSCalcTaskResult, MSCalcCompletedTask> {

	public TaskResultConverter() {
		super(null, nTaskResult -> {
			return new MSCalcCompletedTask(nTaskResult.getMsCalcId());
		});
	}

}
