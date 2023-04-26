package at.ac.univie.dse.msbill.exception;

public class ExistingKeyException extends LogicException {

	public ExistingKeyException(Object key) {
		super("Entry with this key already exists: " + key.toString());
	}

}
