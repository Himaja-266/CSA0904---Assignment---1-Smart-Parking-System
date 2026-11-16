package parking.model;

public final class ElectricCar extends Car {
    public ElectricCar(String regNo) {
        super(regNo);
    }

    @Override
    public long chargingFee(int kWh) {
        if (kWh < 0) {
            throw new IllegalArgumentException("NEGATIVE_ENERGY");
        }
        return 12L * kWh;
    }
}