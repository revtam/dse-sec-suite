package support.mockedmicroservices;

import java.io.IOException;

import support.communication.ApiEndpoints;
import support.communication.HttpCommunicator;
import support.mockedmicroservices.mscalc.EventsCommunicatorProtocol;
import support.mockedmicroservices.mscalc.MSCalcCreated;
import support.mockedmicroservices.mscalc.MSCalcTaskResult;
import support.mockedmicroservices.mscalc.MSCalcTerminated;

public class MockedMsCalc implements EventsCommunicatorProtocol {

	private String msCalcId;

	public MockedMsCalc(String msCalcId) {
		this.msCalcId = msCalcId;
	}

	@Override
	public void publish(MSCalcCreated event) {
		try {
			HttpCommunicator.postRequestWithPayload(ApiEndpoints.PUBLISH(),
					ApiEndpoints.publishBody("MS_CALC_CREATION", "msbill"), event);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(MSCalcTerminated event) {
		try {
			HttpCommunicator.postRequestWithPayload(ApiEndpoints.PUBLISH(),
					ApiEndpoints.publishBody("MS_CALC_TERMINATION", "msbill"), event);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(MSCalcTaskResult event) {
		try {
			HttpCommunicator.postRequestWithPayload(ApiEndpoints.PUBLISH(),
					ApiEndpoints.publishBody("CLNT_TASK_RESULT", event.getClientId()), event);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
