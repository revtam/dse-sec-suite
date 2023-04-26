package steps;

import java.util.function.Consumer;

import org.junit.jupiter.api.Assertions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import support.mockedmicroservices.MockedClnt;
import support.mockedmicroservices.clnt.MSCalcTaskCreated;
import support.mockedmicroservices.clnt.PriceInfoEntry;

public class RegisteringNewTaskSteps {

	private final LoadManagementCommonSteps commonSteps;
	private String msCalcInstanceId;
	private Double originalPrice;
	private Double newPrice;

	public RegisteringNewTaskSteps(LoadManagementCommonSteps commonSteps) {
		this.commonSteps = commonSteps;
	}

	@And("its original price is noted")
	public void its_original_price_is_noted() {
		msCalcInstanceId = commonSteps.msCalcInstanceId;
		MockedClnt clnt = new MockedClnt(commonSteps.clntId);

		clnt.setMsPriceListener(null);
		clnt.pollMsPricesTillPresent(msCalcInstanceId, new Consumer<>() {
			@Override
			public void accept(PriceInfoEntry t) {
				originalPrice = t.getPrice();
			}
		});
	}

	@When("new task is published for the same MSCalc instance")
	public void new_task_is_published_for_the_same_ms_calc_instance() {
		MockedClnt clnt = new MockedClnt(commonSteps.clntId);

		clnt.publishTask(new MSCalcTaskCreated(msCalcInstanceId, "", commonSteps.clntId, "", "", "", ""));
	}

	@Then("the published price report should contain an increased price for that MSCalc instance compared to the price before publishing the task")
	public void the_published_price_report_should_contain_an_increased_price_for_that_ms_calc_instance_compared_to_the_price_before_publishing_the_task() {
		MockedClnt clnt = new MockedClnt(commonSteps.clntId);

		clnt.setMsPriceListener(null);
		clnt.pollMsPricesTillPresent(msCalcInstanceId, new Consumer<>() {
			@Override
			public void accept(PriceInfoEntry t) {
				newPrice = t.getPrice();
			}
		});

		System.out.println(
				"New task test for " + msCalcInstanceId + ": " + originalPrice + " should be less than " + newPrice);

		Assertions.assertTrue(newPrice > originalPrice);
	}

}
