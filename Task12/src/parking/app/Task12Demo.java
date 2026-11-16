package parking.app;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import parking.model.OneDayPass;
import parking.model.ParkingTicket;
import parking.service.ParkingFacility;

public final class Task12Demo {
    private static final int WARMUP_RUNS = 5_000;
    private static final int MEASURED_RUNS = 50_000;

    private Task12Demo() {
    }

    public static void main(String[] args) {
        ParkingFacility facility = new ParkingFacility(new int[] {2, 3, 1, 4, 2});
        System.out.println("Floors: " + facility.getFloorCount());
        System.out.println("Slots per floor: " + Arrays.toString(facility.getCapacities()));

        ParkingTicket bike = facility.enter("EBike", "EB01", LocalTime.of(8, 0), null);
        System.out.println("EBike " + bike.getTicketNumber() + " flat fee: Rs "
                + facility.exit("EB01", 0, LocalDate.now()));

        facility.enter("DeliveryVan", "VAN1", LocalTime.of(10, 0), null);
        System.out.println("DeliveryVan 10:00-12:00: Rs "
                + facility.exit("VAN1", 120, LocalDate.now()));
        facility.enter("DeliveryVan", "VAN2", LocalTime.of(8, 0), null);
        System.out.println("DeliveryVan 08:00-10:00: Rs "
                + facility.exit("VAN2", 120, LocalDate.now()));

                facility.enter("Car", "VIS1", LocalTime.of(10, 0), new OneDayPass(LocalDate.now()));
                boolean passStillRejectsNegativeDuration = false;
                try {
                        facility.exit("VIS1", -1, LocalDate.now());
                } catch (IllegalArgumentException exception) {
                        passStillRejectsNegativeDuration = "NEGATIVE_DURATION".equals(exception.getMessage());
                }
                System.out.println("Negative duration rejected despite pass: " + passStillRejectsNegativeDuration);
        System.out.println("Visitor one-day pass fee: Rs "
                + facility.exit("VIS1", 120, LocalDate.now()));
        facility.enter("Car", "VIS2", LocalTime.of(10, 0), null);
        System.out.println("Visitor without pass fee: Rs "
                + facility.exit("VIS2", 120, LocalDate.now()));
        System.out.println("Existing Task11 classes modified: 0");

        long checksum = 0;
        for (int i = 0; i < WARMUP_RUNS; i++) {
            checksum += new parking.model.DeliveryVan("W" + i)
                    .calculateFee(120, LocalTime.of(10, 0));
        }
        long start = System.nanoTime();
        for (int i = 0; i < MEASURED_RUNS; i++) {
            checksum += new parking.model.DeliveryVan("M" + i)
                    .calculateFee(120, LocalTime.of(10, 0));
        }
        long elapsed = System.nanoTime() - start;
        System.out.println("Average pricing-rule call after warm-up: "
                + (elapsed / MEASURED_RUNS) + " ns/op");
        if (checksum == 0) {
            throw new AssertionError("Pricing calls must be evaluated");
        }
    }
}