package support.mockedmicroservices.mscalc;

public interface EventsCommunicatorProtocol {

	void publish(MSCalcCreated event);

	void publish(MSCalcTerminated event);

	void publish(MSCalcTaskResult event);

}
