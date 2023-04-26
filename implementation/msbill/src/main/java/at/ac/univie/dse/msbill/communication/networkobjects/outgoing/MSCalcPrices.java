package at.ac.univie.dse.msbill.communication.networkobjects.outgoing;

import java.util.List;

public class MSCalcPrices {

	private List<PriceInfoEntry> priceInfoEntries;

	public MSCalcPrices(List<PriceInfoEntry> priceInfoEntries) {
		super();
		this.priceInfoEntries = priceInfoEntries;
	}

	public List<PriceInfoEntry> getPriceInfoEntries() {
		return priceInfoEntries;
	}

	public void setPriceInfoEntries(List<PriceInfoEntry> priceInfoEntries) {
		this.priceInfoEntries = priceInfoEntries;
	}

}
