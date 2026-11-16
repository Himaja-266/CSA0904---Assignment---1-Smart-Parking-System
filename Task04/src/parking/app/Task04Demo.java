package parking.app;

import parking.model.ParkingTicket;

public final class Task04Demo {
    private static volatile int checksum;
    private static final int WARMUP_RUNS = 10_000;
    private static final int MEASURED_RUNS = 100_000;

    private Task04Demo() {
    }

    public static void main(String[] args) {
        ParkingTicket defaultTicket = new ParkingTicket();
        ParkingTicket shortTicket = new ParkingTicket("T-100", "TN01A1");
        ParkingTicket fullTicket = new ParkingTicket("T-101", "TN02B2", 1, 4, 1_700_000_000_000L);
        ParkingTicket copiedTicket = new ParkingTicket(fullTicket);

        System.out.println("Default: " + defaultTicket);
        System.out.println("Overloaded: " + shortTicket);
        System.out.println("Parameterized: " + fullTicket);
        System.out.println("Copy has same ticket number: "
                + fullTicket.getTicketNumber().equals(copiedTicket.getTicketNumber()));

        for (int i = 0; i < WARMUP_RUNS; i++) {
            checksum += new ParkingTicket("W" + i, "TN" + i).getSlot();
        }
        long start = System.nanoTime();
        for (int i = 0; i < MEASURED_RUNS; i++) {
            checksum += new ParkingTicket("M" + i, "TN" + i).getSlot();
        }
        long elapsed = System.nanoTime() - start;
        System.out.println("Average ticket construction after warm-up: "
                + (elapsed / MEASURED_RUNS) + " ns/object");
    }
}