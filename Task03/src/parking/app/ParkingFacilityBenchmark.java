package parking.app;

import parking.service.ParkingFacility;

public final class ParkingFacilityBenchmark {
    private static final int WARMUP_RUNS = 10_000;
    private static final int MEASURED_RUNS = 100_000;

    private ParkingFacilityBenchmark() {
    }

    public static void main(String[] args) {
        ParkingFacility facility = new ParkingFacility(new int[] {100});
        for (int i = 0; i < WARMUP_RUNS; i++) {
            facility.allocate("WARMUP");
            facility.release(0, 0);
        }
        long start = System.nanoTime();
        for (int i = 0; i < MEASURED_RUNS; i++) {
            facility.allocate("BENCH");
            facility.release(0, 0);
        }
        long elapsed = System.nanoTime() - start;
        System.out.println("Average allocate/release pair after warm-up: "
                + (elapsed / MEASURED_RUNS) + " ns/op");
    }
}