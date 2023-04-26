package at.ac.univie.dse.msbill.data.datatypes;

public class Account {

	private String clntInstanceId;
	private Double bookedBalance;

	public Account(String clntInstanceId, Double bookedBalance) {
		this.clntInstanceId = clntInstanceId;
		this.bookedBalance = bookedBalance;
	}

	public Account(String clntInstanceId) {
		this(clntInstanceId, 0.0);
	}

	public String getClntInstanceId() {
		return clntInstanceId;
	}

	public Double getBookedBalance() {
		return bookedBalance;
	}

	public void setBookedBalance(Double bookedBalance) {
		this.bookedBalance = bookedBalance;
	}

}
