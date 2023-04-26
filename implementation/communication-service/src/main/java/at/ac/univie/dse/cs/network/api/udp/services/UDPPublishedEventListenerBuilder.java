package at.ac.univie.dse.cs.network.api.udp.services;

import at.ac.univie.dse.cs.network.api.requester.Requester;

public class UDPPublishedEventListenerBuilder {

    public static UDPPublishedEventListener build(Requester requester, String ipAddress, String port, int sequenceNumberUpperBound){
        return new UDPPublishedEventListener(requester, ipAddress, port, sequenceNumberUpperBound);
    }
}
