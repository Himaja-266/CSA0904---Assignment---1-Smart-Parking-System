package parking.model;

public final class ParkingTicket {
    private static long nextTicketNumber;
    private static int totalOccupancy;

    static {
        nextTicketNumber = 1L;
        totalOccupancy = 0;
        System.out.println("ParkingTicket class initialized");
    }

    private final String ticketNumber;
    private final String registrationNumber;
    private boolean active;

    public ParkingTicket(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException("Registration number is required");
        }
        this.ticketNumber = "P-" + nextTicketNumber++;
        this.registrationNumber = registrationNumber;
        this.active = true;
        totalOccupancy++;
    }

    public static int getTotalOccupancy() {
        return totalOccupancy;
    }

    public static long getNextTicketNumber() {
        return nextTicketNumber;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void close() {
        if (active) {
            active = false;
            totalOccupancy--;
        }
    }
}