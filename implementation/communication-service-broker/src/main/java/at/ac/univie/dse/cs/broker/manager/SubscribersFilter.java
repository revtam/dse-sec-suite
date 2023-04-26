package at.ac.univie.dse.cs.broker.manager;

import at.ac.univie.dse.cs.broker.model.SubscriptionEntry;
import at.ac.univie.dse.cs.network.model.Static;

import java.util.Map;
import java.util.function.Predicate;

public class SubscribersFilter implements Predicate<Map.Entry<SubscriptionEntry,String>> {


    @Override
    public boolean test(Map.Entry<SubscriptionEntry, String> entry) {
        SubscriptionEntry subscriptionEntry = entry.getKey();
        String publishTarget = entry.getValue();
        return subscriptionEntry.getPublishTarget().equals(Static.PUBLISH_TARGET_ALL) ||
                subscriptionEntry.getPublishTarget().equals(publishTarget);
    }
}
