package parking.app;

import java.time.LocalTime;

public final class PolymorphismDemo {
    private static final int WARMUP_RUNS = 5_000;
    private static final int MEASURED_RUNS = 50_000;

    private PolymorphismDemo() {
    }

    public static void main(String[] args) {
        Vehicle vehicle = new Car("TN02B2");
        System.out.println("Declared type: Vehicle; actual type: Car");
        System.out.println("calculateFee(130) runs Vehicle.calculateFee; its rate call dispatches to Car.ratePerHour");
        System.out.println("Integer overload: Rs " + vehicle.calculateFee(130));
        System.out.println("LocalTime overload: Rs "
                + vehicle.calculateFee(LocalTime.of(8, 0), LocalTime.of(10, 10)));

        double checksum = 0.0;
        for (int i = 0; i < WARMUP_RUNS; i++) {
            checksum += vehicle.calculateFee(i % 180);
        }
        long start = System.nanoTime();
        for (int i = 0; i < MEASURED_RUNS; i++) {
            checksum += vehicle.calculateFee(i % 180);
        }
        long elapsed = System.nanoTime() - start;
        System.out.println("Average polymorphic fee call after warm-up: "
                + (elapsed / MEASURED_RUNS) + " ns/op");
        if (checksum == 0.0) {
            throw new AssertionError("Fee calls must be evaluated");
        }
    }
}