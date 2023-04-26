package at.ac.univie.dse.msbill.communication.converters;

import java.util.function.Function;

/**
 * Source: SE1 project, 2021S, created by myself (11838105)
 * 
 * @param <NetworkObject> Object used for network communication
 * @param <DataObject>    Object used in internal infrastructure
 */
abstract class ObjectConverter<NetworkObject, DataObject> {

	private final Function<DataObject, NetworkObject> fromDataObject;
	private final Function<NetworkObject, DataObject> fromNetworkObject;

	public ObjectConverter(final Function<DataObject, NetworkObject> fromDataObject,
			final Function<NetworkObject, DataObject> fromNetworkObject) {
		this.fromDataObject = fromDataObject;
		this.fromNetworkObject = fromNetworkObject;
	}

	public final NetworkObject convertFromDataObject(final DataObject dataObj) {
		return fromDataObject.apply(dataObj);
	}

	public final DataObject convertFromNetworkObject(final NetworkObject networkObj) {
		return fromNetworkObject.apply(networkObj);
	}
}
