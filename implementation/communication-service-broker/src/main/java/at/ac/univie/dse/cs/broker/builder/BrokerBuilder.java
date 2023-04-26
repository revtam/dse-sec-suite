package at.ac.univie.dse.cs.broker.builder;

import at.ac.univie.dse.cs.broker.controller.BrokerReceivedEventsController;
import at.ac.univie.dse.cs.broker.controller.ReceivedEventsController;
import at.ac.univie.dse.cs.broker.manager.ActiveSubscribersMonitor;
import at.ac.univie.dse.cs.broker.manager.EventPublishManager;
import at.ac.univie.dse.cs.broker.manager.EventSubscriptionsManager;
import at.ac.univie.dse.cs.broker.manager.SubscribersFilter;
import at.ac.univie.dse.cs.broker.model.SubscriptionEntry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class BrokerBuilder {

    public static Map<String, Set<SubscriptionEntry>> buildEventSubscriptionMap(){
        return new ConcurrentHashMap<>();
    }

    public static EventSubscriptionsManager buildEventSubscriptionsManager(Map<String, Set<SubscriptionEntry>> eventSubscriptionMap){
        return new EventSubscriptionsManager(eventSubscriptionMap,new SubscribersFilter());
    }

    public static EventPublishManager buildEventPublishManager(EventSubscriptionsManager eventSubscriptionsManager){
        return new EventPublishManager(new LinkedBlockingQueue<>(),eventSubscriptionsManager);
    }

    public static ReceivedEventsController buildReceivedEventsController(EventPublishManager eventPublishManager,
                                                                         EventSubscriptionsManager eventSubscriptionsManager){
        return new BrokerReceivedEventsController(eventSubscriptionsManager,eventPublishManager);
    }

    public static ActiveSubscribersMonitor buildActiveSubscribersMonitor(Map<String, Set<SubscriptionEntry>> buildEventSubscriptionMap,
                                                                         int delay,
                                                                         int period,
                                                                         int threads) {
        return new ActiveSubscribersMonitor(buildEventSubscriptionMap,delay,period,threads);
    }







}
