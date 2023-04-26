package steps;

import at.ac.univie.dse.communication.interfaces.MSCalcTaskResultEventCallback;
import at.ac.univie.dse.communication.models.MSCalcTaskCreated;
import at.ac.univie.dse.communication.models.MSCalcTaskResult;
import at.ac.univie.dse.logic.CrackingUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import java.util.concurrent.Semaphore;

public class PlainTextAttackSteps {

    private static Semaphore semaphore = new Semaphore(1);

    @And("a plain text attack request with id {string} with plain text {string} and cipher text {string} is received")
    public void crackHashReceiveCrackHashRequest(String id, String plainText, String cipherText) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(50);
                    MSCalcTaskCreated task = new MSCalcTaskCreated("primary", "PLAIN_TEXT_ATTACK", "client1", plainText, cipherText, null, id);
                    StepsSharedData.eventsCommunicator.publish(task);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Then("the result of task with id {string} is sent back to the client and its value should be {string}")
    public void crackHashResult(String id, String result) throws InterruptedException {
        semaphore.acquire();
        StepsSharedData.eventsCommunicator.registerCallback(new MSCalcTaskResultEventCallback() {
            @Override
            public void didReceiveEvent(MSCalcTaskResult event) {
                Assertions.assertTrue(event.shouldBeIntercepted);
                Assertions.assertEquals(event.getClientId(), "client1");
                Assertions.assertEquals(event.getTaskType(), "PLAIN_TEXT_ATTACK");
                Assertions.assertEquals(event.getResult(), result);
                Assertions.assertEquals(event.getTaskId(), id);
                semaphore.release();
            }
        });
        semaphore.acquire();
    }
}
