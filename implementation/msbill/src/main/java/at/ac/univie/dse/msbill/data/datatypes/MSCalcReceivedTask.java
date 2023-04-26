package at.ac.univie.dse.msbill.data.datatypes;

public class MSCalcReceivedTask {

	private String msCalcInstanceId;
	private String clntInstanceId;

	public MSCalcReceivedTask(String msCalcInstanceId, String clntInstanceId) {
		this.msCalcInstanceId = msCalcInstanceId;
		this.clntInstanceId = clntInstanceId;
	}

	public String getMsCalcInstanceId() {
		return msCalcInstanceId;
	}

	public String getClntInstanceId() {
		return clntInstanceId;
	}

}
