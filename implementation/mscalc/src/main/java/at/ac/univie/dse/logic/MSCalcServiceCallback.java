package at.ac.univie.dse.logic;

import java.io.IOException;

public interface MSCalcServiceCallback {
    void shouldTerminateInstance();
    void shouldDuplicateInstance() throws IOException;
    void didCrackHash(String result, String clientId, String taskId);
    void didCrackPlainText(String result, String clientId, String taskId);
}
