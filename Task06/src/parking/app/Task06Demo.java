package parking.app;

import parking.model.ElectricCar;
import parking.model.Vehicle;

public final class Task06Demo {
    private static final int WARMUP_RUNS = 2_000;
    private static final int MEASURED_RUNS = 20_000;

    private Task06Demo() {
    }

    public static void main(String[] args) {
        System.out.println("Creating ElectricCar:");
        new ElectricCar("TN03C3");

        Vehicle.setTraceEnabled(false);
        for (int i = 0; i < WARMUP_RUNS; i++) {
            new ElectricCar("W" + i);
        }
        long start = System.nanoTime();
        for (int i = 0; i < MEASURED_RUNS; i++) {
            new ElectricCar("M" + i);
        }
        long elapsed = System.nanoTime() - start;
        System.out.println("Average ElectricCar construction after warm-up: "
                + (elapsed / MEASURED_RUNS) + " ns/object");
    }
}