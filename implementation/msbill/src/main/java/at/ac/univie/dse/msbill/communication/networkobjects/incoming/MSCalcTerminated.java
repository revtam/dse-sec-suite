package at.ac.univie.dse.msbill.communication.networkobjects.incoming;

public class MSCalcTerminated {

	private String instanceId;
	private boolean shouldBeIntercepted;

	public MSCalcTerminated(String instanceId, boolean shouldBeIntercepted) {
		super();
		this.instanceId = instanceId;
		this.shouldBeIntercepted = shouldBeIntercepted;
	}

	public MSCalcTerminated() {
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String msCalcInstanceId) {
		this.instanceId = msCalcInstanceId;
	}

	public boolean isShouldBeIntercepted() {
		return shouldBeIntercepted;
	}

	public void setShouldBeIntercepted(boolean shouldBeIntercepted) {
		this.shouldBeIntercepted = shouldBeIntercepted;
	}

}
