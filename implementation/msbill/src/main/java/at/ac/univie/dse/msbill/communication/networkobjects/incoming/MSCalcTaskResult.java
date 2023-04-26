package at.ac.univie.dse.msbill.communication.networkobjects.incoming;

public class MSCalcTaskResult {

	private String msCalcId;
	private String result;
	private String clientId;
	private String taskType;
	private String taskId;
	private boolean shouldBeIntercepted;

	public MSCalcTaskResult(String msCalcId, String result, String clientId, String taskType, String taskId,
			boolean shouldBeIntercepted) {
		super();
		this.msCalcId = msCalcId;
		this.result = result;
		this.clientId = clientId;
		this.taskType = taskType;
		this.taskId = taskId;
		this.shouldBeIntercepted = shouldBeIntercepted;
	}

	public MSCalcTaskResult() {
	}

	public String getMsCalcId() {
		return msCalcId;
	}

	public void setMsCalcId(String msCalcId) {
		this.msCalcId = msCalcId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public boolean isShouldBeIntercepted() {
		return shouldBeIntercepted;
	}

	public void setShouldBeIntercepted(boolean shouldBeIntercepted) {
		this.shouldBeIntercepted = shouldBeIntercepted;
	}

}
