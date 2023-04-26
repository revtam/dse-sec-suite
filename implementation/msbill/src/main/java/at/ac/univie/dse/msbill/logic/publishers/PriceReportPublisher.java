package at.ac.univie.dse.msbill.logic.publishers;

import java.util.logging.Logger;

import at.ac.univie.dse.msbill.communication.MSBillCommProtocol;
import at.ac.univie.dse.msbill.communication.converters.MSCalcPricesConverter;
import at.ac.univie.dse.msbill.communication.networkobjects.outgoing.MSCalcPrices;
import at.ac.univie.dse.msbill.data.datatypes.PriceReport;

public class PriceReportPublisher implements Publisher<PriceReport> {

	private MSBillCommProtocol communicator;
	private MSCalcPricesConverter converter;
	private static Logger logger = Logger.getLogger(PriceReportPublisher.class.getName());

	public PriceReportPublisher(MSBillCommProtocol communicator, MSCalcPricesConverter converter) {
		this.communicator = communicator;
		this.converter = converter;
	}

	@Override
	public void publish(PriceReport messageObject) {
		MSCalcPrices prices = converter.convertFromDataObject(messageObject);
		communicator.publishPriceReport(prices);
		logger.info("Price report published");
	}

}
