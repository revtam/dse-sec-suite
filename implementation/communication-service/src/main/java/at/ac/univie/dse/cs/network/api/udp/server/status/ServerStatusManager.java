package at.ac.univie.dse.cs.network.api.udp.server.status;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;


public class ServerStatusManager {

    private AtomicBoolean serverStatus;
    private static final Logger LOGGER = Logger.getLogger(ServerStatusManager.class.getName());

    public ServerStatusManager() {
        this.serverStatus = new AtomicBoolean(true);
    }

    /**
     * when turned off, the node wont receive any requests anymore because the
     * listening socket is closed
     */
    public void turnServerOff() {
        LOGGER.info("turning UDP Server off ...");
        this.serverStatus.set(false);
    }

    /**
     * returns the current status
     */
    public Boolean getStatus() {
        return serverStatus.get();
    }

}
