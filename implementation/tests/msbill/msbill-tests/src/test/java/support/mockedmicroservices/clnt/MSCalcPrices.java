package support.mockedmicroservices.clnt;

import java.util.List;

public class MSCalcPrices {

	private List<PriceInfoEntry> priceInfoEntries;

	public MSCalcPrices(List<PriceInfoEntry> priceInfoEntries) {
		this.priceInfoEntries = priceInfoEntries;
	}

	public MSCalcPrices() {
	}

	public List<PriceInfoEntry> getPriceInfoEntries() {
		return priceInfoEntries;
	}

	public void setPriceInfoEntries(List<PriceInfoEntry> priceInfoEntries) {
		this.priceInfoEntries = priceInfoEntries;
	}

}
