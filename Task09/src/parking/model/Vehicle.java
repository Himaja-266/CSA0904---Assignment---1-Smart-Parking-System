package parking.model;

public abstract class Vehicle {
    private final String regNo;

    protected Vehicle(String regNo) {
        if (regNo == null || regNo.isBlank()) {
            throw new IllegalArgumentException("Registration number is required");
        }
        this.regNo = regNo;
    }

    public final String getRegNo() {
        return regNo;
    }

    protected abstract double ratePerHour();

    public double calculateFee(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Duration must not be negative");
        }
        return ((minutes + 59) / 60) * ratePerHour();
    }
}