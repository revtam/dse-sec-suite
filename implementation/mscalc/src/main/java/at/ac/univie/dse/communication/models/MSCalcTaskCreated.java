package at.ac.univie.dse.communication.models;

import java.io.Serializable;

public class MSCalcTaskCreated extends CommunicationEvent implements Serializable {
    public static enum TaskType {
        CRACK_HASH,
        PLAIN_TEXT_ATTACK,
        UNKNOWN;

        static TaskType TaskType(String s) {
            switch(s) {
                case "CRACK_HASH":
                    return CRACK_HASH;
                case "PLAIN_TEXT_ATTACK":
                    return PLAIN_TEXT_ATTACK;
                default:
                    return UNKNOWN;
            }
        }

        TaskType() {

        }
    }

    private String instanceId;
    private String taskType;
    private String clientId;
    private String plainText;
    private String cipherText;
    private String hash;
    private String taskId;

    public TaskType getTaskType() {
        return TaskType.TaskType(taskType);
    }

    public MSCalcTaskCreated(String instanceId, String taskType, String clientId, String plainText, String cipherText, String hash, String taskId) {
        this.instanceId = instanceId;
        this.taskType = taskType;
        this.clientId = clientId;
        this.plainText = plainText;
        this.cipherText = cipherText;
        this.hash = hash;
        this.taskId = taskId;
        shouldBeIntercepted = true;
    }

    public MSCalcTaskCreated() {
    }

    @Override
    public String toString() {
        return "ClientTask{" +
                "instanceId='" + instanceId + '\'' +
                ", taskType='" + taskType + '\'' +
                ", clientId='" + clientId + '\'' +
                ", plainText='" + plainText + '\'' +
                ", cipherText='" + cipherText + '\'' +
                ", hash='" + hash + '\'' +
                ", shouldBeIntercepted=" + shouldBeIntercepted +
                '}';
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getPlainText() {
        return plainText;
    }

    public String getCipherText() {
        return cipherText;
    }

    public String getHash() {
        return hash;
    }

    public String getTaskId() {
        return taskId;
    }
}
