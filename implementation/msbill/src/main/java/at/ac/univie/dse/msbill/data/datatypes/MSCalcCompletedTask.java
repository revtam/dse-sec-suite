package at.ac.univie.dse.msbill.data.datatypes;

public class MSCalcCompletedTask {

	private String msCalcInstanceId;

	public MSCalcCompletedTask(String msCalcInstanceId) {
		super();
		this.msCalcInstanceId = msCalcInstanceId;
	}

	public String getMsCalcInstanceId() {
		return msCalcInstanceId;
	}

	public void setMsCalcInstanceId(String msCalcInstanceId) {
		this.msCalcInstanceId = msCalcInstanceId;
	}

}
