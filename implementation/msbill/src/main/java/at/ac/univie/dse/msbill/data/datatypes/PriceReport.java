package at.ac.univie.dse.msbill.data.datatypes;

import java.util.List;

public class PriceReport {

	private List<InstanceData> priceReportEntries;

	public PriceReport(List<InstanceData> priceReportEntries) {
		this.priceReportEntries = priceReportEntries;
	}

	public List<InstanceData> getPriceReportEntries() {
		return priceReportEntries;
	}

}
