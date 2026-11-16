package parking.model;

public final class Car extends Vehicle {
    public Car(String regNo) {
        super(regNo);
    }

    public int calculateFee(int minutes) {
        return validateDuration(minutes) == 0 ? 0 : ((minutes + 59) / 60) * 30;
    }
}