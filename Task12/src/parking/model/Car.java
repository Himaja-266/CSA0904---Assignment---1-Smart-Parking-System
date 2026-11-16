package parking.model;

import parking.service.HourlyPricingRule;

public class Car extends Vehicle {
    public Car(String regNo) {
        super(regNo, new HourlyPricingRule(30, 20, 200));
    }
}