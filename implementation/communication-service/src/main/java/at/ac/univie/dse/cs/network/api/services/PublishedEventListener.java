package at.ac.univie.dse.cs.network.api.services;

import at.ac.univie.dse.cs.network.exceptions.NetworkException;

import java.io.IOException;
import java.util.Map;

public interface PublishedEventListener {

    void accept(Map<String, String> data, String payLoad) throws NetworkException, IOException;
}
