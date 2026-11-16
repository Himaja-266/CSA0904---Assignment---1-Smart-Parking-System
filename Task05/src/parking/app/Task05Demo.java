package parking.app;

import parking.model.ParkingTicket;
import parking.service.ParkingGate;

public final class Task05Demo {
    private static final int WARMUP_RUNS = 10_000;
    private static final int MEASURED_RUNS = 100_000;

    private Task05Demo() {
    }

    public static void main(String[] args) {
        ParkingGate northGate = new ParkingGate("North");
        ParkingGate southGate = new ParkingGate("South");
        ParkingTicket activeTicket = northGate.enter("TN01A1");
        ParkingTicket secondTicket = southGate.enter("TN02B2");
        System.out.println("Tickets: " + activeTicket.getTicketNumber() + ", " + secondTicket.getTicketNumber());
        System.out.println("Occupancy across gates: " + ParkingTicket.getTotalOccupancy());

        northGate.exit(activeTicket);
        activeTicket = null;
        System.out.println("Occupancy after first exit: " + ParkingTicket.getTotalOccupancy());
        System.out.println("Exited ticket eligible for GC after references clear: " + (activeTicket == null));
        southGate.exit(secondTicket);
        secondTicket = null;
        System.out.println("Occupancy after both exits: " + ParkingTicket.getTotalOccupancy());

        for (int i = 0; i < WARMUP_RUNS; i++) {
            ParkingTicket ticket = northGate.enter("W" + i);
            northGate.exit(ticket);
        }
        long start = System.nanoTime();
        for (int i = 0; i < MEASURED_RUNS; i++) {
            ParkingTicket ticket = northGate.enter("M" + i);
            northGate.exit(ticket);
        }
        long elapsed = System.nanoTime() - start;
        System.out.println("Average entry/exit pair after warm-up: "
                + (elapsed / MEASURED_RUNS) + " ns/op");
        System.out.println("Next ticket sequence: " + ParkingTicket.getNextTicketNumber());
    }
}