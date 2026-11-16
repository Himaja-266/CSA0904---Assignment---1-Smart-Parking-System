package parking.model;

public final class ParkingTicket {
    private final String ticketNumber;
    private final String registrationNumber;
    private final int floor;
    private final int slot;
    private final long entryTimeMillis;

    public ParkingTicket() {
        this("UNASSIGNED", "UNKNOWN", -1, -1, 0L);
    }

    public ParkingTicket(String ticketNumber, String registrationNumber) {
        this(ticketNumber, registrationNumber, 0, 0, System.currentTimeMillis());
    }

    public ParkingTicket(String ticketNumber, String registrationNumber,
                         int floor, int slot, long entryTimeMillis) {
        if (ticketNumber == null || ticketNumber.isBlank()
                || registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException("Ticket and registration numbers are required");
        }
        this.ticketNumber = ticketNumber;
        this.registrationNumber = registrationNumber;
        this.floor = floor;
        this.slot = slot;
        this.entryTimeMillis = entryTimeMillis;
    }

    public ParkingTicket(ParkingTicket source) {
        this(source.ticketNumber, source.registrationNumber, source.floor,
                source.slot, source.entryTimeMillis);
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
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

    @Override
    public String toString() {
        return "ParkingTicket{" + ticketNumber + ", " + registrationNumber
                + ", floor=" + floor + ", slot=" + slot + "}";
    }
}