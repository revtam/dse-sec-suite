package at.ac.univie.dse.msbill.communication.converters;

import at.ac.univie.dse.msbill.communication.networkobjects.incoming.MSCalcTerminated;
import at.ac.univie.dse.msbill.data.datatypes.MSCalcTermination;

public class MSCalcTerminatedConverter extends ObjectConverter<MSCalcTerminated, MSCalcTermination> {

	public MSCalcTerminatedConverter() {
		super(null, nMSCalcTerminated -> {
			return new MSCalcTermination(nMSCalcTerminated.getInstanceId());
		});
	}

}
