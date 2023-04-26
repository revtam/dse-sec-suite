package at.ac.univie.dse.api;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import at.ac.univie.dse.communication.interfaces.EventTaskCallback;
import at.ac.univie.dse.communication.interfaces.EventsCommunicatorProtocol;
import at.ac.univie.dse.communication.models.MSCalcCreated;
import at.ac.univie.dse.communication.models.MSCalcTaskCreated;
import at.ac.univie.dse.communication.models.MSCalcTaskResult;
import at.ac.univie.dse.communication.models.MSCalcTerminated;
import at.ac.univie.dse.cs.properties.ApplicationPropertiesCache;
import at.ac.univie.dse.logic.CrackHashRequest;
import at.ac.univie.dse.logic.MSCalcService;
import at.ac.univie.dse.logic.MSCalcServiceCallback;
import at.ac.univie.dse.logic.PlainTextAttackRequest;
import at.ac.univie.dse.utilities.dependencies.DependenciesContainerFactory;
import at.ac.univie.dse.utilities.dependencies.DependenciesContainerProtocol;

public class MSCalcAPI {
	private static String identifier;
	private static DependenciesContainerProtocol dependenciesContainer = DependenciesContainerFactory
			.getDependenciesContainer();
	private static EventsCommunicatorProtocol eventsCommunicator;
	private static String port;

	public static void start(String identifier, String port) throws IOException {
		if(!identifier.equals("primary")) {
			ApplicationPropertiesCache.getInstance().setProperty("udp.listener.port", port);
		}
		MSCalcAPI.port = port;

		eventsCommunicator = dependenciesContainer.getEventsCommunicatorProtocol();
		MSCalcAPI.identifier = identifier;
		System.out.println("MS instance identifier: " + identifier);
		MSCalcCreated mscalcCreated = new MSCalcCreated(identifier);
		eventsCommunicator.publish(mscalcCreated);

		MSCalcService service = new MSCalcService(new MSCalcServiceCallback() {

			@Override
			public void shouldTerminateInstance() {
				if (identifier.equals("primary")) {
					return;
				}
				MSCalcAPI.shouldTerminateInstance();
			}

			@Override
			public void shouldDuplicateInstance() throws IOException {
				MSCalcAPI.shouldDuplicateInstance();
			}

			@Override
			public void didCrackHash(String result, String clientId, String taskId) {
				System.out.println("Received result: " + result + " from client: " + clientId);
				eventsCommunicator.publish(new MSCalcTaskResult(identifier, result, clientId, "CRACK_HASH", taskId));
			}

			@Override
			public void didCrackPlainText(String result, String clientId, String taskId) {
				System.out.println("Received result: " + result + " from client: " + clientId);
				eventsCommunicator
						.publish(new MSCalcTaskResult(identifier, result, clientId, "PLAIN_TEXT_ATTACK", taskId));
			}
		}, dependenciesContainer);

		eventsCommunicator.registerCallback(new EventTaskCallback() {
			@Override
			public void didReceiveEventTask(MSCalcTaskCreated task) {
				if (!task.getInstanceId().equals(identifier)) {
					return;
				}

				MSCalcTaskCreated.TaskType taskType = task.getTaskType();
				switch (taskType) {
				case CRACK_HASH:
					if (task.getHash() == null) {
						return;
					}
					CrackHashRequest crackHashRequest = new CrackHashRequest(task.getClientId(), task.getHash(),
							task.getTaskId());
					try {
						service.dispatchTask(crackHashRequest);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case PLAIN_TEXT_ATTACK:
					if (task.getPlainText() == null || task.getClientId() == null) {
						return;
					}
					PlainTextAttackRequest plainTextAttackRequest = new PlainTextAttackRequest(task.getTaskId(),
							task.getClientId(), task.getCipherText(), task.getPlainText());
					try {
						service.dispatchTask(plainTextAttackRequest);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case UNKNOWN:
					break;
				}
			}
		});

		while (true) {
		}
	}

	public static void shouldTerminateInstance() {
		MSCalcTerminated mscalcTerminated = new MSCalcTerminated(identifier);
		eventsCommunicator.publish(mscalcTerminated);
		System.exit(0);
	}

	public static void shouldDuplicateInstance() throws IOException {
		Integer portToInt = Integer.parseInt(port);
		String newPort;
		//avoid port collisions with msbill
		if(portToInt + 1 == 8105) {
			newPort =  Integer.toString(portToInt + 2);
		} else {
			newPort = Integer.toString(portToInt + 1);
		}

		String identifier = UUID.randomUUID().toString();
		ProcessBuilder msBuilder = new ProcessBuilder("/usr/bin/java", "-jar", "MSCalc-1.0-SNAPSHOT.jar", identifier, newPort);
		msBuilder.directory(new File("build/libs"));
		Process microserviceInstance = msBuilder.start();
	}
}
