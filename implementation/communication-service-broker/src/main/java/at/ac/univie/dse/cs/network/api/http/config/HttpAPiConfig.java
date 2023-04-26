package at.ac.univie.dse.cs.network.api.http.config;

import at.ac.univie.dse.cs.broker.builder.BrokerBuilder;
import at.ac.univie.dse.cs.broker.controller.ReceivedEventsController;
import at.ac.univie.dse.cs.broker.manager.ActiveSubscribersMonitor;
import at.ac.univie.dse.cs.broker.manager.EventPublishManager;
import at.ac.univie.dse.cs.broker.manager.EventSubscriptionsManager;
import at.ac.univie.dse.cs.broker.model.SubscriptionEntry;
import at.ac.univie.dse.cs.network.api.http.invoker.HttpBrokerInvoker;
import at.ac.univie.dse.cs.network.api.http.model.SubscriberPendingResponseKey;
import at.ac.univie.dse.cs.network.api.http.services.HttpPublishedEventListener;
import at.ac.univie.dse.cs.network.api.http.services.HttpSubscriberPingingService;
import at.ac.univie.dse.cs.network.api.http.services.PendingSubscriptionsResponsesService;
import at.ac.univie.dse.cs.network.api.http.validation.PayloadValidator;
import at.ac.univie.dse.cs.network.api.invoker.Invoker;
import at.ac.univie.dse.cs.network.api.services.PublishedEventListener;
import at.ac.univie.dse.cs.network.api.services.SubscriberPingingService;
import at.ac.univie.dse.cs.properties.ApplicationPropertiesCache;
import at.ac.univie.dse.cs.properties.NetworkProtocol;
import at.ac.univie.dse.cs.properties.PropertiesKeys;
import at.ac.univie.dse.cs.starter.BrokerApplicationStarter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Configuration
public class HttpAPiConfig {

    @Bean
    ReceivedEventsController receivedEventsController(){
        if(BrokerApplicationStarter.receivedEventsController != null){
            return BrokerApplicationStarter.receivedEventsController;
        }
        ApplicationPropertiesCache applicationPropertiesCache = ApplicationPropertiesCache.getInstance();
        int delay = applicationPropertiesCache.getIntProperty(PropertiesKeys.Broker.SUBSCRIBERS_MONITOR_DELAY);
        int period = applicationPropertiesCache.getIntProperty(PropertiesKeys.Broker.SUBSCRIBERS_MONITOR_PERIOD);
        int threads = applicationPropertiesCache.getIntProperty(PropertiesKeys.Broker.SUBSCRIBERS_MONITOR_THREADS);
        Map<String, Set<SubscriptionEntry>> eventSubscriptionMap = BrokerBuilder.buildEventSubscriptionMap();
        EventSubscriptionsManager eventSubscriptionsManager = BrokerBuilder.buildEventSubscriptionsManager(eventSubscriptionMap);
        EventPublishManager eventPublishManager = BrokerBuilder.buildEventPublishManager(eventSubscriptionsManager);
        ReceivedEventsController receivedEventsController = BrokerBuilder.buildReceivedEventsController(eventPublishManager,eventSubscriptionsManager);
        ActiveSubscribersMonitor activeSubscribersMonitor = BrokerBuilder.buildActiveSubscribersMonitor(eventSubscriptionMap,delay,period,threads);
        new Thread(eventPublishManager).start();
        activeSubscribersMonitor.start();
        return receivedEventsController;
    }

    @Bean
    public Invoker httpBrokerInvoker(PublishedEventListener publishedEventListener,
                             ReceivedEventsController receivedEventsController,
                             SubscriberPingingService subscriberPingingService
    ){
        return new HttpBrokerInvoker(publishedEventListener, receivedEventsController, subscriberPingingService);
    }

    @Bean
    public PendingSubscriptionsResponsesService pendingSubscriptionsResponsesService(){
        Map<SubscriberPendingResponseKey,Queue<String>> pendingSubscriptionsResponsesMap = new ConcurrentHashMap<>();
        return new PendingSubscriptionsResponsesService(pendingSubscriptionsResponsesMap);
    }

    @Bean
    public SubscriberPingingService subscriberPingingService(){
        return new HttpSubscriberPingingService();
    }

    @Bean
    public PublishedEventListener publishedEventListener(PendingSubscriptionsResponsesService pendingSubscriptionsResponsesService){
        return new HttpPublishedEventListener(pendingSubscriptionsResponsesService);
    }

    @Bean
    public PayloadValidator payloadValidator(ObjectMapper objectMapper){
        return new PayloadValidator(objectMapper);
    }
}
