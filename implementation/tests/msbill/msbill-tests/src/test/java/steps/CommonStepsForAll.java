package steps;

import java.io.IOException;
import java.util.function.Consumer;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;

import io.cucumber.java.en.Given;
import support.communication.ApiEndpoints;
import support.communication.HttpCommunicator;

public class CommonStepsForAll {

	@Given("the communication service and the MSBill are running")
	public void communicationService_and_msbill_are_running() {
		try {
			HttpCommunicator.postRequestWithStatusCallback(ApiEndpoints.SUBSCRIBE(),
					ApiEndpoints.subscribeBody("TEST_EVENT", "testId", "*"), new Consumer<>() {
						@Override
						public void accept(Integer t) {
							Assertions.assertEquals(HttpStatus.SC_OK, t);
						}
					});
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
