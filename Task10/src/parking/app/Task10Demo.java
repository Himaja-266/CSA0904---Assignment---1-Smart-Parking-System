package parking.app;

import parking.model.Car;
import parking.model.ParkingData;
import parking.service.SlotValidator;

public final class Task10Demo {
    private static final int WARMUP_RUNS = 5_000;
    private static final int MEASURED_RUNS = 50_000;

    private Task10Demo() {
    }

    public static void main(String[] args) {
        ParkingData data = new ParkingData();
        data.setDurationMinutes(130);
        data.setSlotNumber(3, 10);
        SlotValidator validator = new SlotValidator(new int[] {20, 50, 100});
        Car car = new Car("TN02B2");
        System.out.println("Fee: Rs " + car.calculateFee(data.getDurationMinutes()));
        System.out.println("Valid slot (floor 1, slot 50): " + validator.isValidSlot(1, 50));
        System.out.println("Capacity: " + validator.totalCapacity());
        System.out.println("Public registration API valid: " + car.hasValidRegistration());
        try {
            data.setDurationMinutes(-1);
        } catch (IllegalArgumentException exception) {
            System.out.println("Negative duration rejected");
        }
        try {
            data.setSlotNumber(11, 10);
        } catch (IllegalArgumentException exception) {
            System.out.println("Invalid slot number rejected");
        }

        Integer cachedA = 127;
        Integer cachedB = 127;
        Integer uncachedA = 128;
        Integer uncachedB = 128;
        System.out.println("Integer 127 ==: " + (cachedA == cachedB));
        System.out.println("Integer 128 ==: " + (uncachedA == uncachedB));
        System.out.println("Integer 128 equals: " + uncachedA.equals(uncachedB));

        long checksum = 0;
        for (int i = 0; i < WARMUP_RUNS; i++) {
            checksum += validator.isValidSlot(1, 25) ? 1 : 0;
        }
        long start = System.nanoTime();
        for (int i = 0; i < MEASURED_RUNS; i++) {
            checksum += validator.isValidSlot(1, 25) ? 1 : 0;
        }
        long elapsed = System.nanoTime() - start;
        System.out.println("Average slot validation after warm-up: "
                + (elapsed / MEASURED_RUNS) + " ns/op");
        if (checksum == 0) {
            throw new AssertionError("Validation calls must be evaluated");
        }
    }
}