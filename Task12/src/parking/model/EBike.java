package parking.model;

import parking.service.FlatPricingRule;

public final class EBike extends Vehicle {
    public EBike(String regNo) {
        super(regNo, new FlatPricingRule(15));
    }
}