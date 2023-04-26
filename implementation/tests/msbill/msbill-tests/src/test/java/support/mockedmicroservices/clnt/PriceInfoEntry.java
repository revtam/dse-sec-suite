package support.mockedmicroservices.clnt;

public class PriceInfoEntry {

	private String msCalcInstanceId;
	private Double price;
	private String taskType;

	public PriceInfoEntry(String msCalcInstanceId, Double price, String taskType) {
		this.msCalcInstanceId = msCalcInstanceId;
		this.price = price;
		this.taskType = taskType;
	}

	public PriceInfoEntry() {
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
