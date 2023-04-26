package at.ac.univie.dse.msbill.communication.converters;

import at.ac.univie.dse.msbill.communication.networkobjects.outgoing.PriceInfoEntry;
import at.ac.univie.dse.msbill.data.datatypes.InstanceData;

public class PriceInfoEntryConverter extends ObjectConverter<PriceInfoEntry, InstanceData> {

	public PriceInfoEntryConverter() {
		super(dInstanceData -> {
			return new PriceInfoEntry(dInstanceData.getMsCalcInstanceId(), dInstanceData.getPrice(),
					dInstanceData.getTaskType());
		}, null);
	}

}
