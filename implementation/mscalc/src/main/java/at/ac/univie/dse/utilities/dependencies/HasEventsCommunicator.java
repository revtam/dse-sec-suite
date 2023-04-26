package at.ac.univie.dse.utilities.dependencies;

import at.ac.univie.dse.communication.interfaces.EventsCommunicatorProtocol;

public interface HasEventsCommunicator {
    EventsCommunicatorProtocol getEventsCommunicatorProtocol();
}
