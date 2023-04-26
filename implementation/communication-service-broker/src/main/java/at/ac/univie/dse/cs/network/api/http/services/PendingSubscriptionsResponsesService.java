package at.ac.univie.dse.cs.network.api.http.services;

import at.ac.univie.dse.cs.network.api.http.model.SubscriberPendingResponseKey;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PendingSubscriptionsResponsesService {

    private final Map<SubscriberPendingResponseKey,Queue<String>> pendingSubscriptionsResponses;

    public PendingSubscriptionsResponsesService(Map<SubscriberPendingResponseKey, Queue<String>> pendingSubscriptionsResponses) {
        this.pendingSubscriptionsResponses = pendingSubscriptionsResponses;
    }

    public void addPendingSubscription(SubscriberPendingResponseKey subscriberPendingResponseKey,String payload){
        synchronized (PendingSubscriptionsResponsesService.class){
            Queue<String> pendingSubscriptionsResponsesQueue = this.pendingSubscriptionsResponses
                    .getOrDefault(subscriberPendingResponseKey,new ConcurrentLinkedQueue<>());
            pendingSubscriptionsResponsesQueue.add(payload);
            this.pendingSubscriptionsResponses.put(subscriberPendingResponseKey,pendingSubscriptionsResponsesQueue);
        }
    }



    public Optional<String[]> getPendingSubscriptions(SubscriberPendingResponseKey subscriberPendingResponseKey,
                                                      String fetchMode){
        Queue<String> pendingSubscriptionsResponsesQueue = this.pendingSubscriptionsResponses
                .getOrDefault(subscriberPendingResponseKey,new ConcurrentLinkedQueue<>());
        if(fetchMode.equals("SINGLE")){
            String pendingSubscriptionResponse = pendingSubscriptionsResponsesQueue.poll();
            if(pendingSubscriptionResponse == null){
                return Optional.empty();
            }else{
                return Optional.ofNullable(new String[]{pendingSubscriptionResponse});
            }
        }else{
            this.pendingSubscriptionsResponses.put(subscriberPendingResponseKey,new ConcurrentLinkedQueue<>());
            String[] subscriptionsResponses = pendingSubscriptionsResponsesQueue.toArray(String[]::new);
            if(subscriptionsResponses.length == 0){
                return Optional.empty();
            }else{
                return Optional.ofNullable(subscriptionsResponses);
            }
        }
    }
}
