package at.ac.univie.dse.msbill.communication.metadata;

public class EventMetaData {

	private String eventName;
	private String eventTarget;

	public EventMetaData(String eventName, String eventTarget) {
		this.eventName = eventName;
		this.eventTarget = eventTarget;
	}

	public String getEventName() {
		return eventName;
	}

	public String getEventTarget() {
		return eventTarget;
	}
}
