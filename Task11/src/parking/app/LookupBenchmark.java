package parking.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class LookupBenchmark {
    private static final int WARMUP_RUNS = 100;
    private static final int MEASURED_RUNS = 300;

    private LookupBenchmark() {
    }

    public static void main(String[] args) {
        for (int size : new int[] {1_000, 10_000, 100_000}) {
            benchmark(size);
        }
    }

    private static void benchmark(int size) {
        String[] array = new String[size];
        ArrayList<String> list = new ArrayList<>(size);
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String registration = "TN" + i;
            array[i] = registration;
            list.add(registration);
            map.put(registration, i);
        }
        String target = "TN" + (size - 1);
        for (int i = 0; i < WARMUP_RUNS; i++) {
            scan(array, target);
            list.indexOf(target);
            map.get(target);
        }
        long arrayNanos = time(() -> scan(array, target));
        long listNanos = time(() -> list.indexOf(target));
        long mapNanos = time(() -> map.get(target));
        System.out.println("n=" + size + " average ns/lookup: array=" + arrayNanos
                + ", ArrayList=" + listNanos + ", HashMap=" + mapNanos);
    }

    private static long time(Runnable lookup) {
        long start = System.nanoTime();
        for (int i = 0; i < MEASURED_RUNS; i++) {
            lookup.run();
        }
        return (System.nanoTime() - start) / MEASURED_RUNS;
    }

    private static int scan(String[] values, String target) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }
}