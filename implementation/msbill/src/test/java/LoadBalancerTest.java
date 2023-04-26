import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import at.ac.univie.dse.msbill.data.datatypes.InstanceData;
import at.ac.univie.dse.msbill.data.instance.InstanceDataDao;
import at.ac.univie.dse.msbill.data.instance.InstanceDataRepository;
import at.ac.univie.dse.msbill.data.instance.InstanceDataStorage;
import at.ac.univie.dse.msbill.exception.ExistingKeyException;
import at.ac.univie.dse.msbill.exception.MissingKeyException;
import at.ac.univie.dse.msbill.logic.LoadBalancer;
import at.ac.univie.dse.msbill.logic.PriceCalculator;

@TestMethodOrder(OrderAnnotation.class)
class LoadBalancerTest {

	static LoadBalancer loadBalancer;

	@BeforeAll
	static void init() {
		InstanceDataDao dataDao = new InstanceDataStorage();
		InstanceDataRepository dataRepo = InstanceDataRepository.getInstance(dataDao);
		PriceCalculator priceCalc = new PriceCalculator(100.0, 1.2);
		loadBalancer = new LoadBalancer(dataRepo, priceCalc);
	}

	@Test
	@Order(1)
	void givenNewMsCalcInstanceStored_whenInstanceRetrieved_thenIsPresent() {
		loadBalancer.addInstance("id1", "type1");
		InstanceData instanceData = loadBalancer.getInstance("id1");
		Assertions.assertEquals("id1", instanceData.getMsCalcInstanceId());
		Assertions.assertEquals(0, instanceData.getTasksInQueue());
		Assertions.assertEquals("type1", instanceData.getTaskType());
		Assertions.assertEquals(100.0, instanceData.getPrice());
	}

	@Test
	@Order(2)
	void givenInstanceStored_whenInstanceWithSameIdAdded_thenNewInstanceNotStoredAndExceptionThrown() {
		Assertions.assertThrows(ExistingKeyException.class, () -> loadBalancer.addInstance("id1", "type1"));
		Assertions.assertThrows(ExistingKeyException.class, () -> loadBalancer.addInstance("id1", "type2"));
		Assertions.assertEquals(1, loadBalancer.getAllInstances().size());
		Assertions.assertEquals("type1", loadBalancer.getInstance("id1").getTaskType());
	}

	@Test
	@Order(2)
	void givenInstanceLoadWithLoadZero_whenLoadIncremented_thenCorrectLoadAndPriceRetrieved() {
		loadBalancer.incrementLoad("id1");
		InstanceData instanceData = loadBalancer.getInstance("id1");
		Assertions.assertEquals(1, instanceData.getTasksInQueue());
		Assertions.assertEquals(120.0, instanceData.getPrice());
	}

	@Test
	@Order(3)
	void givenInstanceWithLoadOne_whenLoadDecremented_thenCorrectLoadAndPriceRetrieved() {
		loadBalancer.decrementLoad("id1");
		InstanceData instanceData = loadBalancer.getInstance("id1");
		Assertions.assertEquals(0, instanceData.getTasksInQueue());
		Assertions.assertEquals(100.0, instanceData.getPrice());
	}

	@Test
	@Order(4)
	void givenInstanceWithLoadZero_whenDecrementationInvalid_thenResetThrowsException() {
		Assertions.assertThrows(RuntimeException.class, () -> loadBalancer.decrementLoad("id1"));
		InstanceData instanceData = loadBalancer.getInstance("id1");
		Assertions.assertEquals(0, instanceData.getTasksInQueue());
		Assertions.assertEquals(100.0, instanceData.getPrice());
	}

	@Test
	@Order(2)
	void givenInstanceNotExisting_whenInstanceModifiedOrRetrieved_thenExceptionThrown() {
		Assertions.assertThrows(MissingKeyException.class, () -> loadBalancer.decrementLoad("id2"));
		Assertions.assertThrows(MissingKeyException.class, () -> loadBalancer.incrementLoad("id2"));
		Assertions.assertThrows(MissingKeyException.class, () -> loadBalancer.getPrice("id2"));
		Assertions.assertThrows(MissingKeyException.class, () -> loadBalancer.terminateInstance("id2"));
		Assertions.assertThrows(MissingKeyException.class, () -> loadBalancer.getInstance("id2"));
	}

	@Test
	@Order(5)
	void givenInstanceStored_whenPriceReportCalled_thenPriceReportContentCorrect() {
		InstanceData instanceData = loadBalancer.getPriceReport().getPriceReportEntries().get(0);
		Assertions.assertEquals("id1", instanceData.getMsCalcInstanceId());
		Assertions.assertEquals(100.0, instanceData.getPrice());
	}

	@Test
	@Order(6)
	void givenInstanceStored_whenTerminated_thenPriceReportEmpty() {
		loadBalancer.terminateInstance("id1");
		Assertions.assertEquals(0, loadBalancer.getPriceReport().getPriceReportEntries().size());
	}

}
