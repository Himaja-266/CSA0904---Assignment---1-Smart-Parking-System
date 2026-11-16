package parking.model;

import parking.service.Chargeable;

public final class ElectricCar extends Car implements Chargeable {
    public ElectricCar(String regNo) {
        super(regNo);
    }

    @Override
    public double chargingFee(int kWh) {
        if (kWh < 0) {
            throw new IllegalArgumentException("Energy must not be negative");
        }
        return kWh * Chargeable.defaultRatePerKWh();
    }
}