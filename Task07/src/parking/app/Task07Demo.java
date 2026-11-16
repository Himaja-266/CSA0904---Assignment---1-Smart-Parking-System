package parking.app;

import parking.model.Car;
import parking.model.TwoWheeler;
import parking.model.Vehicle;

public final class Task07Demo {
    private static final int WARMUP_RUNS = 5_000;
    private static final int MEASURED_RUNS = 50_000;

    private Task07Demo() {
    }

    public static void main(String[] args) {
        final Vehicle first = new Car("TN02B2");
        Vehicle sameRegistrationDifferentType = new TwoWheeler("TN02B2");
        System.out.println("Vehicle: " + first);
        System.out.println("Cross-type equality: " + first.equals(sameRegistrationDifferentType));
        System.out.println("Equal objects have equal hash codes: "
                + (first.hashCode() == sameRegistrationDifferentType.hashCode()));
        System.out.println("130-minute car fee: Rs " + first.calculateFee(130));
        System.out.println("Daily cap: Rs " + first.calculateFee(24 * 60));

        int checksum = 0;
        for (int i = 0; i < WARMUP_RUNS; i++) {
            checksum += first.calculateFee(i % 180);
        }
        long start = System.nanoTime();
        for (int i = 0; i < MEASURED_RUNS; i++) {
            checksum += first.calculateFee(i % 180);
        }
        long elapsed = System.nanoTime() - start;
        System.out.println("Average fee call after warm-up: " + (elapsed / MEASURED_RUNS) + " ns/op");
        if (checksum == 0) {
            throw new AssertionError("Fee calls must be evaluated");
        }
    }
}