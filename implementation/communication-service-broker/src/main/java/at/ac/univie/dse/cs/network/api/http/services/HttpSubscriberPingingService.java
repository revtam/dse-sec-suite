package at.ac.univie.dse.cs.network.api.http.services;

import at.ac.univie.dse.cs.network.api.services.SubscriberPingingService;

import java.util.Map;

public class HttpSubscriberPingingService implements SubscriberPingingService {
    @Override
    public Boolean pingSubscriber(Map<String, String> data) {
        return true;
    }
}
