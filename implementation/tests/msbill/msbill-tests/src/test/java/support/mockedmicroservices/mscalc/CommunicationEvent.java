package support.mockedmicroservices.mscalc;

import java.io.Serializable;

public class CommunicationEvent implements Serializable {
    public boolean shouldBeIntercepted = false;
}
