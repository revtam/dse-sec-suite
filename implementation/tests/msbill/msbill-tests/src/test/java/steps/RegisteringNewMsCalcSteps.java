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
import support.mockedmicroservices.MockedClnt;
import support.mockedmicroservices.clnt.PriceInfoEntry;

public class RegisteringNewMsCalcSteps {

	private final MsCalcInstanceManagementCommonSteps commonSteps;
	private String msCalcInstanceId;
	private boolean isFound = false;

	public RegisteringNewMsCalcSteps(MsCalcInstanceManagementCommonSteps commonSteps) {
		this.commonSteps = commonSteps;
	}

	@Then("the new instance should be present in the published price report within {int} seconds")
	public void the_new_instance_should_be_present_in_the_published_price_report_within_seconds(int waitSeconds) {
		msCalcInstanceId = commonSteps.msCalcInstanceId;
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

		Assertions.assertEquals(true, isFound);
	}

}
