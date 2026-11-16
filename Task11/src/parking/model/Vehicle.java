package parking.model;

public abstract class Vehicle {
    private final String regNo;

    protected Vehicle(String regNo) {
        if (regNo == null || regNo.isBlank()) {
            throw new IllegalArgumentException("INVALID_REGISTRATION");
        }
        this.regNo = regNo;
    }

    public final String getRegNo() {
        return regNo;
    }

    protected abstract long firstHourRate();

    protected abstract long additionalHourRate();

    protected abstract long dailyMaximum();

    public final long calculateFee(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("NEGATIVE_DURATION");
        }
        long remaining = minutes;
        long total = 0;
        while (remaining > 0) {
            long dayMinutes = Math.min(remaining, 24L * 60L);
            long hours = (dayMinutes + 59) / 60;
            long slabFee = firstHourRate() + Math.max(0, hours - 1) * additionalHourRate();
            total += Math.min(slabFee, dailyMaximum());
            remaining -= dayMinutes;
        }
        return total;
    }

    public long chargingFee(int kWh) {
        if (kWh < 0) {
            throw new IllegalArgumentException("NEGATIVE_ENERGY");
        }
        return 0;
    }
}