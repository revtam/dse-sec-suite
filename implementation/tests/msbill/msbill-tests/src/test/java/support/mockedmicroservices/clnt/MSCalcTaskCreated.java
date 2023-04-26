package support.mockedmicroservices.clnt;

public class MSCalcTaskCreated {

	private String instanceId;
	private String taskType;
	private String clientId;
	private String plainText;
	private String cipherText;
	private String hash;
	private String taskId;

	public MSCalcTaskCreated(String instanceId, String taskType, String clientId, String plainText, String cipherText,
			String hash, String taskId) {
		this.instanceId = instanceId;
		this.taskType = taskType;
		this.clientId = clientId;
		this.plainText = plainText;
		this.cipherText = cipherText;
		this.hash = hash;
		this.taskId = taskId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getPlainText() {
		return plainText;
	}

	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}

	public String getCipherText() {
		return cipherText;
	}

	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}
