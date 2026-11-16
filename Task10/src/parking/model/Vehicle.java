package parking.model;

public abstract class Vehicle {
    private final String regNo;

    public Vehicle(String regNo) {
        if (regNo == null || regNo.isBlank()) {
            throw new IllegalArgumentException("Registration number is required");
        }
        this.regNo = regNo;
    }

    public final String getRegNo() {
        return regNo;
    }

    protected final int validateDuration(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Duration must not be negative");
        }
        return minutes;
    }

    boolean isRegistrationUsable() {
        return !regNo.isBlank();
    }

    public final boolean hasValidRegistration() {
        return isRegistrationUsable();
    }
}