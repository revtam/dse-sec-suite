package steps;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import org.junit.jupiter.api.Assertions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import support.mockedmicroservices.MockedClnt;
import support.mockedmicroservices.MockedMsCalc;
import support.mockedmicroservices.clnt.PriceInfoEntry;
import support.mockedmicroservices.mscalc.MSCalcTerminated;

public class TerminatingMsCalcSteps {

	private final MsCalcInstanceManagementCommonSteps commonSteps;
	private String msCalcInstanceId;
	private boolean isFound = false;

	public TerminatingMsCalcSteps(MsCalcInstanceManagementCommonSteps commonSteps) {
		this.commonSteps = commonSteps;
	}

	@When("the same MSCalc instance terminates")
	public void the_same_mscalc_instance_terminates() {
		msCalcInstanceId = commonSteps.msCalcInstanceId;

		MockedMsCalc msCalc = new MockedMsCalc(msCalcInstanceId);

		msCalc.publish(new MSCalcTerminated(msCalcInstanceId));
	}

	@Then("the instance should be gone from the published price report within {int} seconds")
	public void the_instance_should_be_gone_from_the_published_price_report_within_seconds(int waitSeconds) {
		MockedClnt clnt = new MockedClnt(commonSteps.clntId);

		clnt.setMsPriceListener(null);

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future future = executor.submit(new Thread() {
			@Override
			public void run() {
				clnt.pollMsPricesTillPresent(msCalcInstanceId, new Consumer<>() {
					@Override
					public void accept(PriceInfoEntry t) {
						isFound = true;
					}
				});
			}
		});
		try {
			future.get(waitSeconds, TimeUnit.SECONDS);
		} catch (TimeoutException | InterruptedException | ExecutionException e) {
			future.cancel(true);
		} finally {
			executor.shutdownNow();
		}

		Assertions.assertEquals(false, isFound);
	}

}
