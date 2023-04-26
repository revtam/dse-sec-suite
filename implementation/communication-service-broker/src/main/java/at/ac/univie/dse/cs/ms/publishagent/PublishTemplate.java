package at.ac.univie.dse.cs.ms.publishagent;

import at.ac.univie.dse.cs.network.api.requester.Requester;
import at.ac.univie.dse.cs.network.exceptions.NetworkException;
import at.ac.univie.dse.cs.network.exceptions.PublishTemplateException;
import at.ac.univie.dse.cs.network.model.EventKeyValueMapBuilder;
import at.ac.univie.dse.cs.network.model.EventType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PublishTemplate {

    private final Requester requester;

    public PublishTemplate(Requester requester) {
        this.requester = requester;
    }

    public <T> void publish(T object, String eventName, String publishTarget){
        try {
            this.requester.request(object,eventName,publishTarget,EventType.PUBLISH);
        } catch (NetworkException | IOException e) {
            e.printStackTrace();
            throw new PublishTemplateException(e.getMessage(),e);
        }
    }

    public void unSubscribe(String eventName){
        try {
            this.requester.request(new Object(),eventName,"*",EventType.UNSUBSCRIBE);
        } catch (NetworkException | IOException e) {
            e.printStackTrace();
            throw new PublishTemplateException(e.getMessage(),e);
        }
    }


    public void publishSubscriptionRequests(List<Map.Entry<String,String>> subscribedEvents){
        for(Map.Entry<String,String> subscribedEvent : subscribedEvents){
            publishSubscriptionRequest(subscribedEvent.getKey(),subscribedEvent.getValue());
        }
    }

    public void publishSubscriptionRequest(String eventName, String publishTarget){
        try {
            this.requester.request(new Object(),eventName,publishTarget,EventType.SUBSCRIBE);
        } catch (NetworkException | IOException e) {
            e.printStackTrace();
            throw new PublishTemplateException(e.getMessage(),e);
        }
    }


}
