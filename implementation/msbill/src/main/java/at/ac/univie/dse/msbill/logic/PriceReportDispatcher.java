package at.ac.univie.dse.msbill.logic;

import at.ac.univie.dse.msbill.logic.publishers.PriceReportPublisher;

public class PriceReportDispatcher implements Runnable {

	private PriceReportPublisher publisher;
	private LoadBalancer loadBalancer;

	public PriceReportDispatcher(PriceReportPublisher publisher, LoadBalancer loadBalancer) {
		this.publisher = publisher;
		this.loadBalancer = loadBalancer;
	}

	@Override
	public void run() {
		publisher.publish(loadBalancer.getPriceReport());
	}

}
