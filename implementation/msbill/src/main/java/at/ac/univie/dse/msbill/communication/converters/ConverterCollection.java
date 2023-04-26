package at.ac.univie.dse.msbill.communication.converters;

import java.util.Map;

public class ConverterCollection {

	private Map<Class, ObjectConverter> convertersByNetworkClass;

	public ConverterCollection(Map<Class, ObjectConverter> convertersByNetworkClass) {
		this.convertersByNetworkClass = convertersByNetworkClass;
	}

	public ObjectConverter getConverter(Class networkObjectClass) {
		return convertersByNetworkClass.get(networkObjectClass);
	}

}
