package parking.model;

import java.util.concurrent.atomic.AtomicLong;

public final class ParkingTicket {
    private static final AtomicLong NEXT_NUMBER = new AtomicLong(1);

    private final String ticketNumber;
    private final Vehicle vehicle;
    private final DriverCategory category;
    private final ParkingPass pass;
    private final int floor;
    private final int slot;
    private final long entryTimeMillis;

    public ParkingTicket(Vehicle vehicle, DriverCategory category, ParkingPass pass,
                         int floor, int slot, long entryTimeMillis) {
        this.ticketNumber = "P-" + NEXT_NUMBER.getAndIncrement();
        this.vehicle = vehicle;
        this.category = category;
        this.pass = pass;
        this.floor = floor;
        this.slot = slot;
        this.entryTimeMillis = entryTimeMillis;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public DriverCategory getCategory() {
        return category;
    }

    public ParkingPass getPass() {
        return pass;
    }

    public int getFloor() {
        return floor;
    }

    public int getSlot() {
        return slot;
    }

    public long getEntryTimeMillis() {
        return entryTimeMillis;
    }
}