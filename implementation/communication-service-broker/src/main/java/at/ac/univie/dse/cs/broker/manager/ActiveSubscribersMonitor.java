package at.ac.univie.dse.cs.broker.manager;

import at.ac.univie.dse.cs.broker.model.SubscriptionEntry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ActiveSubscribersMonitor{

    private final int delay;
    private final int period;
    private final Map<String, Set<SubscriptionEntry>> eventSubscriptionMap;
    private final ScheduledExecutorService scheduler;

    public ActiveSubscribersMonitor(Map<String,Set<SubscriptionEntry>> eventSubscriptionMap,
                                    int delay,
                                    int period,
                                    int threads) {
        this.eventSubscriptionMap = eventSubscriptionMap;
        this.scheduler = Executors.newScheduledThreadPool(threads);
        this.delay = delay;
        this.period = period;
    }

    public void start() {
        SubscribersMonitoringThread subscribersMonitoringThread = new SubscribersMonitoringThread(this.eventSubscriptionMap);
        this.scheduler.scheduleAtFixedRate(subscribersMonitoringThread, delay, period, TimeUnit.MILLISECONDS);
    }

    public static class SubscribersMonitoringThread implements Runnable{

        private final Map<String, Set<SubscriptionEntry>> eventSubscriptionMap;

        public SubscribersMonitoringThread(Map<String, Set<SubscriptionEntry>> eventSubscriptionMap) {
            this.eventSubscriptionMap = eventSubscriptionMap;
        }

        @Override
        public void run() {
            for(Map.Entry<String,Set<SubscriptionEntry>> entry : this.eventSubscriptionMap.entrySet()){
                Set<SubscriptionEntry> updatedSubscribersSet = entry.getValue().stream()
                        .filter(SubscriptionEntry::applySubscriberPingingService)
                        .collect(Collectors.toSet());
                this.eventSubscriptionMap.put(entry.getKey(),updatedSubscribersSet);
            }
        }
    }
}
