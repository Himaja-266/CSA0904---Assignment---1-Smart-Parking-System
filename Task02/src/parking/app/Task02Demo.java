package parking.app;

import java.util.Arrays;

public final class Task02Demo {
    private static final int WARMUP_RUNS = 2_000;
    private static final int MEASURED_RUNS = 10_000;

    private Task02Demo() {
    }

    public static void main(String[] args) {
        String registrationNumber = args.length > 0 ? args[0] : "TN02CD2222";
        String[][] floors = {new String[2], new String[3], new String[1]};
        floors[1][1] = registrationNumber;

        int minutes = 130;
        int truncatedHours = minutes / 60;
        int roundedHours = (minutes + 59) / 60;

        System.out.println("Input registration number: " + registrationNumber);
        System.out.println("Jagged floors: " + Arrays.deepToString(floors));
        System.out.println("Wrong integer division: " + minutes + "/60 = " + truncatedHours + " hours");
        System.out.println("Started-hour billing: (" + minutes + " + 59)/60 = " + roundedHours + " hours");
        System.out.println("Slot number type: int; vehicle number type: String");
        System.out.println("Entry time type: long epoch milliseconds; duration type: long minutes; fee type: long rupees");

        for (int i = 0; i < WARMUP_RUNS; i++) {
            findVehicle(floors, registrationNumber);
        }
        long start = System.nanoTime();
        int result = -1;
        for (int i = 0; i < MEASURED_RUNS; i++) {
            result = findVehicle(floors, registrationNumber);
        }
        long elapsed = System.nanoTime() - start;
        System.out.println("Search result flattened index: " + result);
        System.out.println("Jagged search average after warm-up: " + (elapsed / MEASURED_RUNS) + " ns/op");
    }

    private static int findVehicle(String[][] floors, String registrationNumber) {
        int flattenedIndex = 0;
        for (String[] floor : floors) {
            for (String vehicleNumber : floor) {
                if (registrationNumber.equals(vehicleNumber)) {
                    return flattenedIndex;
                }
                flattenedIndex++;
            }
        }
        return -1;
    }
}