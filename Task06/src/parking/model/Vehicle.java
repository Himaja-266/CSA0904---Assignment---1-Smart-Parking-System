package parking.model;

public abstract class Vehicle {
    private static boolean traceEnabled = true;
    private final String registrationNumber;

    protected Vehicle(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        trace("Vehicle constructor: " + registrationNumber);
    }

    public static void setTraceEnabled(boolean enabled) {
        traceEnabled = enabled;
    }

    protected static void trace(String message) {
        if (traceEnabled) {
            System.out.println(message);
        }
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
}