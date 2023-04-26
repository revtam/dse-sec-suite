package support.mockedmicroservices;

import java.io.IOException;
import java.util.function.Consumer;

import support.communication.ApiEndpoints;
import support.communication.HttpCommunicator;
import support.communication.Utils;
import support.mockedmicroservices.clnt.CLNTCommProtocol;
import support.mockedmicroservices.clnt.MSCalcPrices;
import support.mockedmicroservices.clnt.MSCalcTaskCreated;
import support.mockedmicroservices.clnt.PriceInfoEntry;

public class MockedClnt implements CLNTCommProtocol {

	private String clntId;

	public MockedClnt(String clntId) {
		this.clntId = clntId;
	}

	@Override
	public void setMsPriceListener(Consumer<Object> callback) {
		try {
			HttpCommunicator.postRequestBasic(ApiEndpoints.SUBSCRIBE(),
					ApiEndpoints.subscribeBody("MS_INFO", clntId, "clnt"));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void pollMsPrices(Consumer<Object> callback) {
		try {
			HttpCommunicator.postRequestWithCallback(ApiEndpoints.SUBSCRIPTIONS(),
					ApiEndpoints.subscriptionsBody("MS_INFO", clntId, "clnt"), callback, MSCalcPrices.class);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publishTask(MSCalcTaskCreated event) {
		try {
			HttpCommunicator.postRequestWithPayload(ApiEndpoints.PUBLISH(),
					ApiEndpoints.publishBody("CLNT_TASK", event.getInstanceId()), event);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Polls the MS_INFO event repeatedly until the specified MSCalc instance id
	 * shows up in the price report. It is necessary because it takes time till the
	 * price reports published by the MSBill mirror the changes in its state.
	 * 
	 * @param msCalcId  the MSCalc instance id it waits for
	 * @param testLogic additional test logic after the instance id is found
	 */
	public void pollMsPricesTillPresent(String msCalcId, Consumer<PriceInfoEntry> testLogic) {
		pollMsPrices(new Consumer<>() {
			@Override
			public void accept(Object t) {
				if (t == null) {
					System.out.println("Polling again");
					Utils.sleep(1500);
					pollMsPrices(this);
					return;
				}
				MSCalcPrices prices = (MSCalcPrices) t;
				PriceInfoEntry entry = prices.getPriceInfoEntries().stream()
						.filter(priceEntry -> priceEntry.getMsCalcInstanceId().equals(msCalcId)).findFirst()
						.orElseGet(() -> null);
				if (entry == null) {
					Utils.sleep(1500);
					pollMsPrices(this);
					return;
				}
				testLogic.accept(entry);
			}
		});
	}

}
