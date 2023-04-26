package at.ac.univie.dse.cs.network.api.services;

import java.util.Map;

public interface SubscriberPingingService {
    Boolean pingSubscriber(Map<String,String> data);
}
