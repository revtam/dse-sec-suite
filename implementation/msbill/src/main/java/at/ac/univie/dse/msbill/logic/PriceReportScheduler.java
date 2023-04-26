package at.ac.univie.dse.msbill.logic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class PriceReportScheduler {

	private Runnable dispatcher;
	private long delayMilliseconds;
	private ScheduledExecutorService executor;
	private static Logger logger = Logger.getLogger(PriceReportScheduler.class.getName());

	public PriceReportScheduler(Runnable dispatcher, long delayMilliseconds) {
		this.dispatcher = dispatcher;
		this.delayMilliseconds = delayMilliseconds;
		this.executor = Executors.newScheduledThreadPool(1);
	}

	public void start() {
		executor.scheduleWithFixedDelay(dispatcher, 0, delayMilliseconds, TimeUnit.MILLISECONDS);
		logger.info("PriceReportScheduler started");
	}

	public void stop() {
		try {
			executor.awaitTermination(0, TimeUnit.SECONDS);
			logger.info("PriceReportScheduler stopped");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
