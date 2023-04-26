package at.ac.univie.dse.msbill.exception;

public class MissingKeyException extends LogicException {

	public MissingKeyException(Object key) {
		super("Entry with this key does not exist: " + key.toString());
	}

}