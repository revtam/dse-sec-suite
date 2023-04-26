package steps;

import io.cucumber.java.en.And;
import support.mockedmicroservices.MockedMsCalc;
import support.mockedmicroservices.mscalc.MSCalcCreated;

public class LoadManagementCommonSteps {

	String msCalcInstanceId;
	String clntId = "clnt1";

	private final CommonStepsForAll commonStepsAll;

	public LoadManagementCommonSteps(CommonStepsForAll commonStepsAll) {
		this.commonStepsAll = commonStepsAll;
	}

	@And("an MSCalc instance with id {string} is registered")
	public void mscalc_instance_with_id_is_registered(String msCalcInstanceId) {
		this.msCalcInstanceId = msCalcInstanceId;
		MockedMsCalc msCalc = new MockedMsCalc(msCalcInstanceId);

		msCalc.publish(new MSCalcCreated(msCalcInstanceId));
	}

}
