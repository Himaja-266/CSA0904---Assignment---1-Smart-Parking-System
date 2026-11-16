package parking.model;

public final class ElectricCar extends Car {
    public ElectricCar(String registrationNumber) {
        super(registrationNumber);
        trace("ElectricCar constructor");
    }
}