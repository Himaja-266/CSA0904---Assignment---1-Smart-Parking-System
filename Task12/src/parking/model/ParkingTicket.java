package parking.model;

import java.util.concurrent.atomic.AtomicLong;

public final class ParkingTicket {
    private static final AtomicLong NEXT_NUMBER = new AtomicLong(1);
    private final String ticketNumber = "P-" + NEXT_NUMBER.getAndIncrement();
    private final Vehicle vehicle;
    private final int floor;
    private final int slot;
    private final java.time.LocalTime entryTime;
    private final Pass pass;

    public ParkingTicket(Vehicle vehicle, int floor, int slot,
                         java.time.LocalTime entryTime, Pass pass) {
        this.vehicle = vehicle;
        this.floor = floor;
        this.slot = slot;
        this.entryTime = entryTime;
        this.pass = pass;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getFloor() {
        return floor;
    }

    public int getSlot() {
        return slot;
    }

    public java.time.LocalTime getEntryTime() {
        return entryTime;
    }

    public Pass getPass() {
        return pass;
    }
}