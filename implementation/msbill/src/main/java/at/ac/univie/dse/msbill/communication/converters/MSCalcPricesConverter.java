package at.ac.univie.dse.msbill.communication.converters;

import java.util.List;
import java.util.stream.Collectors;

import at.ac.univie.dse.msbill.communication.networkobjects.outgoing.MSCalcPrices;
import at.ac.univie.dse.msbill.communication.networkobjects.outgoing.PriceInfoEntry;
import at.ac.univie.dse.msbill.data.datatypes.PriceReport;

public class MSCalcPricesConverter extends ObjectConverter<MSCalcPrices, PriceReport> {

	public MSCalcPricesConverter() {
		super(dPriceReport -> {
			PriceInfoEntryConverter entryConverter = new PriceInfoEntryConverter();
			List<PriceInfoEntry> nEntries = dPriceReport.getPriceReportEntries().stream()
					.map(dEntry -> entryConverter.convertFromDataObject(dEntry)).collect(Collectors.toList());
			return new MSCalcPrices(nEntries);
		}, null);
	}

}
