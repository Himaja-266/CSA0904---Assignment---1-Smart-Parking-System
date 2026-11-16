package parking.service;

import parking.model.ParkingTicket;

public final class ParkingGate {
    private final String gateName;

    public ParkingGate(String gateName) {
        this.gateName = gateName;
    }

    public ParkingTicket enter(String registrationNumber) {
        return new ParkingTicket(registrationNumber);
    }

    public void exit(ParkingTicket ticket) {
        ticket.close();
    }

    public String getGateName() {
        return gateName;
    }
}