package support.mockedmicroservices.clnt;

import java.util.function.Consumer;

public interface CLNTCommProtocol {

	void setMsPriceListener(Consumer<Object> callback);

	void publishTask(MSCalcTaskCreated event);

}
