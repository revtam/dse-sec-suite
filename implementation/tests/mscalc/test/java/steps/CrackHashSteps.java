package steps;


import at.ac.univie.dse.communication.interfaces.MSCalcTaskResultEventCallback;
import at.ac.univie.dse.communication.models.MSCalcTaskCreated;
import at.ac.univie.dse.communication.models.MSCalcTaskResult;
import at.ac.univie.dse.logic.CrackingUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import java.util.concurrent.Semaphore;

public class CrackHashSteps {
    private static Semaphore semaphore = new Semaphore(1);

    @And("a crack hash request with task id {string} with hash {string} is received")
    public void crackHashReceiveCrackHashRequest(String taskId, String hash) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(50);
                    MSCalcTaskCreated task = new MSCalcTaskCreated("primary", "CRACK_HASH", "client1", null, null, hash, taskId);
                    StepsSharedData.eventsCommunicator.publish(task);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Then("the result for task id {string} and hash {string} is sent back to the client and it should have a valid value")
    public void crackHashResult(String taskId, String input) throws InterruptedException {
        semaphore.acquire();
        StepsSharedData.eventsCommunicator.registerCallback(new MSCalcTaskResultEventCallback() {
          @Override
          public void didReceiveEvent(MSCalcTaskResult event) {
              Assertions.assertTrue(event.shouldBeIntercepted);
              Assertions.assertEquals(event.getClientId(), "client1");
              Assertions.assertEquals(event.getTaskType(), "CRACK_HASH");
              Assertions.assertEquals(event.getTaskId(), taskId);
              Assertions.assertEquals(CrackingUtilities.generateCRC16Hash(event.getResult()), input);
              semaphore.release();
          }
      });
      semaphore.acquire();
    }
}
