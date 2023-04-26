package steps;

import at.ac.univie.dse.MSCalcApplication;
import at.ac.univie.dse.communication.interfaces.MSCalcCreatedEventCallback;
import at.ac.univie.dse.communication.models.MSCalcCreated;
import at.ac.univie.dse.cs.properties.ApplicationPropertiesCache;
import at.ac.univie.dse.utilities.dependencies.DependenciesContainerFactory;
import io.cucumber.core.gherkin.Step;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import java.io.IOException;
import java.util.concurrent.Semaphore;


public class InstanceCreationSteps {
    private static Semaphore semaphore = new Semaphore(1);

    @Given("the communication service is running")
    public void communicationServiceRunning() throws IOException, InterruptedException {
        ApplicationPropertiesCache.getInstance().setProperty("udp.listener.ip", "localhost");
        StepsSharedData.eventsCommunicator = DependenciesContainerFactory
                .getDependenciesContainer()
                .getEventsCommunicatorProtocol();
        Assertions.assertNotNull(StepsSharedData.eventsCommunicator);
    }

    @When("an instance with id {string} and port {string} is created")
    public void instanceIsCreated(String id, String port) throws IOException {
        StepsSharedData.identifier = id;
        StepsSharedData.port = port;
        MSCalcApplication application = new MSCalcApplication();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(50);
                    MSCalcApplication.main(new String[]{id, port});
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Then("the instance is started and event for this is received")
    public void startInstance() throws InterruptedException {
        semaphore.acquire();
        StepsSharedData.eventsCommunicator.registerCallback(new MSCalcCreatedEventCallback() {
            @Override
            public void didReceiveEvent(MSCalcCreated event) {
              Assertions.assertTrue(event.shouldBeIntercepted);
              Assertions.assertEquals(StepsSharedData.identifier, "primary");
              semaphore.release();
            }
        });
        semaphore.acquire();
    }
}
