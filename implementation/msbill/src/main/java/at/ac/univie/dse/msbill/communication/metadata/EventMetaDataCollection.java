package at.ac.univie.dse.msbill.communication.metadata;

public class EventMetaDataCollection {

	public static EventMetaData getMSCalcCreatedListenerMetaData() {
		return new EventMetaData("MS_CALC_CREATION", "msbill");
	}

	public static EventMetaData getMSCalcTerminatedListenerMetaData() {
		return new EventMetaData("MS_CALC_TERMINATION", "msbill");
	}

	public static EventMetaData getTaskResultListenerMetaData() {
		return new EventMetaData("CLNT_TASK_RESULT", "*");
	}

	public static EventMetaData getTaskPublishedListenerMetaData() {
		return new EventMetaData("CLNT_TASK", "*");
	}

	public static EventMetaData getMSCalcPricesPublisherMetaData() {
		return new EventMetaData("MS_INFO", "clnt");
	}

}
