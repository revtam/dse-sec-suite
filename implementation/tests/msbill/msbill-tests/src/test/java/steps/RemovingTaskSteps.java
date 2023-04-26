package steps;

import java.util.function.Consumer;

import org.junit.jupiter.api.Assertions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import support.mockedmicroservices.MockedClnt;
import support.mockedmicroservices.MockedMsCalc;
import support.mockedmicroservices.clnt.MSCalcTaskCreated;
import support.mockedmicroservices.clnt.PriceInfoEntry;
import support.mockedmicroservices.mscalc.MSCalcTaskResult;

public class RemovingTaskSteps {

	private final LoadManagementCommonSteps commonSteps;
	private String msCalcInstanceId;
	private Double originalPrice;
	private Double newPrice;

	public RemovingTaskSteps(LoadManagementCommonSteps commonSteps) {
		this.commonSteps = commonSteps;
	}

	@And("a task is already registered for the same MSCalc instance")
	public void task_is_registered_for_the_same_mscalc_instace() {
		msCalcInstanceId = commonSteps.msCalcInstanceId;
		MockedClnt clnt = new MockedClnt(commonSteps.clntId);

		clnt.publishTask(new MSCalcTaskCreated(msCalcInstanceId, "", commonSteps.clntId, "", "", "", ""));

		clnt.setMsPriceListener(null);
		clnt.pollMsPricesTillPresent(msCalcInstanceId, new Consumer<>() {
			@Override
			public void accept(PriceInfoEntry t) {
				originalPrice = t.getPrice();
			}
		});
	}

	@When("that MSCalc instance completes a task")
	public void mscalc_instance_completes_task() {
		MockedMsCalc msCalc = new MockedMsCalc(msCalcInstanceId);

		msCalc.publish(new MSCalcTaskResult(msCalcInstanceId, "", commonSteps.clntId, "", ""));
	}

	@Then("the published price report should contain a decreased price for that MSCalc instance compared to the price before completing the task")
	public void price_report_contains_decreased_price_for_mscalc_instance() {
		MockedClnt clnt = new MockedClnt(commonSteps.clntId);

		clnt.setMsPriceListener(null);
		clnt.pollMsPricesTillPresent(msCalcInstanceId, new Consumer<>() {
			@Override
			public void accept(PriceInfoEntry t) {
				newPrice = t.getPrice();
			}
		});

		System.out.println("Task completed test for " + msCalcInstanceId + ": " + originalPrice
				+ " should be greater than " + newPrice);

		Assertions.assertTrue(newPrice < originalPrice);
	}

}
