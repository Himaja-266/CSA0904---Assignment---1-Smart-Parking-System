package parking.model;

import java.time.LocalTime;
import parking.service.PricingRule;

public abstract class Vehicle {
    private final String regNo;
    private final PricingRule pricingRule;

    protected Vehicle(String regNo, PricingRule pricingRule) {
        if (regNo == null || regNo.isBlank() || pricingRule == null) {
            throw new IllegalArgumentException("Vehicle details and pricing rule are required");
        }
        this.regNo = regNo;
        this.pricingRule = pricingRule;
    }

    public final String getRegNo() {
        return regNo;
    }

    public final long calculateFee(int minutes, LocalTime entryTime) {
        return pricingRule.calculate(minutes, entryTime);
    }
}