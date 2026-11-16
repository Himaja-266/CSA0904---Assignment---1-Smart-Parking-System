package parking.model;

public final class TwoWheeler extends Vehicle {
    public TwoWheeler(String regNo) {
        super(regNo);
    }

    @Override
    protected long firstHourRate() {
        return 10;
    }

    @Override
    protected long additionalHourRate() {
        return 8;
    }

    @Override
    protected long dailyMaximum() {
        return 100;
    }
}