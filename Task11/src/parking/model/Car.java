package parking.model;

public class Car extends Vehicle {
    public Car(String regNo) {
        super(regNo);
    }

    @Override
    protected long firstHourRate() {
        return 30;
    }

    @Override
    protected long additionalHourRate() {
        return 20;
    }

    @Override
    protected long dailyMaximum() {
        return 200;
    }
}