package at.ac.univie.dse.msbill.data.datatypes;

public class MSCalcCreation {

	private String newMsCalcInstanceId;
	private String taskType;

	public MSCalcCreation(String newMsCalcInstanceId, String taskType) {
		super();
		this.newMsCalcInstanceId = newMsCalcInstanceId;
		this.taskType = taskType;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getNewMSCalcInstanceId() {
		return newMsCalcInstanceId;
	}

	public void setNewMSCalcInstanceId(String newMSCalcInstanceId) {
		this.newMsCalcInstanceId = newMSCalcInstanceId;
	}

}
