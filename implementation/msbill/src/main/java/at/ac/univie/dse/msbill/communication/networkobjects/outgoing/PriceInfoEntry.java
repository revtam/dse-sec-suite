package at.ac.univie.dse.msbill.communication.networkobjects.outgoing;

public class PriceInfoEntry {

	private String msCalcInstanceId;
	private Double price;
	private String taskType;

	public PriceInfoEntry(String msCalcInstanceId, Double price, String taskType) {
		super();
		this.msCalcInstanceId = msCalcInstanceId;
		this.price = price;
		this.taskType = taskType;
	}

	public String getTaskType() {
		return taskType;
	}

	public String getMsCalcInstanceId() {
		return msCalcInstanceId;
	}

	public Double getPrice() {
		return price;
	}

}
