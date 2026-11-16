package parking.model;

public class Car extends Vehicle {
    private static final int DAILY_MAXIMUM = 500;

    public Car(String regNo) {
        super(regNo);
    }

    @Override
    protected int ratePerHour() {
        return 30;
    }

    @Override
    public int calculateFee(int minutes) {
        return Math.min(super.calculateFee(minutes), DAILY_MAXIMUM);
    }
}