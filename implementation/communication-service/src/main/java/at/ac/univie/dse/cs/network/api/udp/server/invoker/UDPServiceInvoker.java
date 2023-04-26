package at.ac.univie.dse.cs.network.api.udp.server.invoker;

import at.ac.univie.dse.cs.network.model.Static;
import at.ac.univie.dse.cs.network.api.invoker.InvocationEntry;
import at.ac.univie.dse.cs.network.api.invoker.Invoker;
import at.ac.univie.dse.cs.network.api.marshaller.StringToObjectUnmarshaller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class UDPServiceInvoker implements Invoker {

     private final StringToObjectUnmarshaller stringToObjectUnmarshaller;
    private final Set<InvocationEntry> invocationEntries;

    public UDPServiceInvoker(StringToObjectUnmarshaller stringToObjectUnmarshaller,
                             Set<InvocationEntry> invocationEntries) {
        this.stringToObjectUnmarshaller = stringToObjectUnmarshaller;
        this.invocationEntries = invocationEntries;
    }

    @Override
    public void invoke(Map<String, String> data) {
        var eventName = data.get(Static.EVENT_NAME_KEY);
        var payLoad = data.get(Static.PAYLOAD_KEY);
        InvocationEntry invocationEntry = getByEventName(eventName);
        try {
            Class<?> classType = Class.forName(invocationEntry.getClassName());
            Object payLoadObject = this.stringToObjectUnmarshaller.unmarshall(payLoad,classType);
            Consumer<Object> listener = invocationEntry.getOnReceivedPublishEventListener();
            listener.accept(payLoadObject);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private InvocationEntry getByEventName(String eventName) {
        return this.invocationEntries.stream()
                .filter(invocationEntry -> invocationEntry.getEventName().equals(eventName))
                .findAny().orElseThrow(() -> new RuntimeException(""));
    }
}
