package at.ac.univie.dse.msbill.communication.converters;

import at.ac.univie.dse.msbill.communication.networkobjects.incoming.MSCalcCreated;
import at.ac.univie.dse.msbill.data.datatypes.MSCalcCreation;

public class MSCalcCreatedConverter extends ObjectConverter<MSCalcCreated, MSCalcCreation> {

	public MSCalcCreatedConverter() {
		super(null, nMsCalcCreated -> {
			return new MSCalcCreation(nMsCalcCreated.getInstanceId(), "");
		});
	}

}
