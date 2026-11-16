package parking.app;

public final class Task01Analysis {
    private static final int VEHICLE_COUNT = 10_000;
    private static final int SEARCH_REPETITIONS = 1_000;

    private Task01Analysis() {
    }

    public static void main(String[] args) {
        String[] registrationNumbers = new String[VEHICLE_COUNT];
        String[] vehicleTypes = new String[VEHICLE_COUNT];
        long[] entryMinutes = new long[VEHICLE_COUNT];
        ParkingRecord[] records = new ParkingRecord[VEHICLE_COUNT];

        for (int i = 0; i < VEHICLE_COUNT; i++) {
            registrationNumbers[i] = "TN" + i;
            vehicleTypes[i] = "Car";
            entryMinutes[i] = 540L + i;
            records[i] = new ParkingRecord(registrationNumbers[i], vehicleTypes[i], entryMinutes[i]);
        }

        String target = registrationNumbers[VEHICLE_COUNT - 1];
        long start = System.nanoTime();
        int parallelArrayIndex = -1;
        for (int i = 0; i < SEARCH_REPETITIONS; i++) {
            parallelArrayIndex = findInParallelArrays(registrationNumbers, target);
        }
        long parallelArrayNanos = System.nanoTime() - start;

        start = System.nanoTime();
        int objectArrayIndex = -1;
        for (int i = 0; i < SEARCH_REPETITIONS; i++) {
            objectArrayIndex = findInObjects(records, target);
        }
        long objectArrayNanos = System.nanoTime() - start;

        System.out.println("Sample record: " + records[0].registrationNumber()
                + ", " + records[0].vehicleType()
                + ", entry minute " + records[0].entryMinute());
        System.out.println("Search target: " + target);
        System.out.println("Parallel-array result index: " + parallelArrayIndex);
        System.out.println("Object-array result index: " + objectArrayIndex);
        System.out.println("Parallel-array search (" + SEARCH_REPETITIONS + " runs): "
                + parallelArrayNanos + " ns");
        System.out.println("Object-array search (" + SEARCH_REPETITIONS + " runs): "
                + objectArrayNanos + " ns");
    }

    private static int findInParallelArrays(String[] registrationNumbers, String target) {
        for (int i = 0; i < registrationNumbers.length; i++) {
            if (registrationNumbers[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    private static int findInObjects(ParkingRecord[] records, String target) {
        for (int i = 0; i < records.length; i++) {
            if (records[i].registrationNumber().equals(target)) {
                return i;
            }
        }
        return -1;
    }

    private record ParkingRecord(String registrationNumber, String vehicleType, long entryMinute) {
    }
}