package at.ac.univie.dse.cs.network.api.udp.server.status;

import java.util.concurrent.atomic.AtomicBoolean;


public class ServerStatusManager {

    private AtomicBoolean serverStatus;

    public ServerStatusManager() {
        this.serverStatus = new AtomicBoolean(true);
    }

    /**
     * when turned off, the node wont receive any requests anymore because the
     * listening socket is closed
     */
    public void turnServerOff() {
        this.serverStatus.set(false);
    }

    /**
     * returns the current status
     */
    public Boolean getStatus() {
        return serverStatus.get();
    }

}
