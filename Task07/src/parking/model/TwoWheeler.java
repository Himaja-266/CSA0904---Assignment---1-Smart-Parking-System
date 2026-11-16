package parking.model;

public final class TwoWheeler extends Vehicle {
    public TwoWheeler(String regNo) {
        super(regNo);
    }

    @Override
    protected int ratePerHour() {
        return 10;
    }

    @Override
    public int calculateFee(int minutes) {
        return super.calculateFee(minutes);
    }
}