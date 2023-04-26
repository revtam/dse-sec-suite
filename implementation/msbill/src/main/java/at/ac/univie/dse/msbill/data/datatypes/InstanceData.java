package at.ac.univie.dse.msbill.data.datatypes;

public class InstanceData {

	private String msCalcInstanceId;
	private String taskType;
	private int tasksInQueue;
	private Double price;

	public InstanceData(String msCalcInstanceId, String taskType, Double price) {
		this.msCalcInstanceId = msCalcInstanceId;
		this.taskType = taskType;
		this.tasksInQueue = 0;
		this.price = price;
	}

	public String getMsCalcInstanceId() {
		return msCalcInstanceId;
	}

	public String getTaskType() {
		return taskType;
	}

	public int getTasksInQueue() {
		return tasksInQueue;
	}

	public void setTasksInQueue(int tasksInQueue) {
		this.tasksInQueue = tasksInQueue;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
