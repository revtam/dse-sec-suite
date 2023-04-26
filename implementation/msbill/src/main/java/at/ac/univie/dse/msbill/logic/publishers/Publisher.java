package at.ac.univie.dse.msbill.logic.publishers;

/**
 * @param <DataObject>
 */
public interface Publisher<DataObject> {

	void publish(DataObject messageObject);

}
