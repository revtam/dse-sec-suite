package at.ac.univie.dse.msbill.communication.networkobjects.incoming;

public class MSCalcCreated {

	private String instanceId;
	private boolean shouldBeIntercepted;

	public MSCalcCreated(String instanceId, boolean shouldBeIntercepted) {
		this.instanceId = instanceId;
		this.shouldBeIntercepted = shouldBeIntercepted;
	}

	public MSCalcCreated() {
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public boolean isShouldBeIntercepted() {
		return shouldBeIntercepted;
	}

	public void setShouldBeIntercepted(boolean shouldBeIntercepted) {
		this.shouldBeIntercepted = shouldBeIntercepted;
	}

}
