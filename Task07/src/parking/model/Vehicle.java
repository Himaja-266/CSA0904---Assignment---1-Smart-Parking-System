package parking.model;

import java.util.Objects;

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

    protected abstract int ratePerHour();

    public int calculateFee(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Duration must not be negative");
        }
        int hours = (minutes + 59) / 60;
        return hours * ratePerHour();
    }

    @Override
    public final String toString() {
        return getClass().getSimpleName() + " " + regNo;
    }

    @Override
    public final boolean equals(Object other) {
        return other instanceof Vehicle vehicle && regNo.equals(vehicle.regNo);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(regNo);
    }
}