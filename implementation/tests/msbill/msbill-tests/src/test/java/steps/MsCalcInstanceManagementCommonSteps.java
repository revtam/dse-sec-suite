package steps;

import io.cucumber.java.en.When;
import support.mockedmicroservices.MockedMsCalc;
import support.mockedmicroservices.mscalc.MSCalcCreated;

public class MsCalcInstanceManagementCommonSteps {

	String msCalcInstanceId;
	String clntId = "clnt2";

	private final CommonStepsForAll commonStepsAll;

	public MsCalcInstanceManagementCommonSteps(CommonStepsForAll commonStepsAll) {
		this.commonStepsAll = commonStepsAll;
	}

	@When("an MSCalc instance with id {string} is created")
	public void an_mscalc_instance_with_id_is_created(String msCalcId) {
		this.msCalcInstanceId = msCalcId;
		MockedMsCalc msCalc = new MockedMsCalc(msCalcId);

		msCalc.publish(new MSCalcCreated(msCalcId));
	}

}
