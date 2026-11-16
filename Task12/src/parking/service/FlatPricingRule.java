package parking.service;

import java.time.LocalTime;

public final class FlatPricingRule implements PricingRule {
    private final long flatFee;

    public FlatPricingRule(long flatFee) {
        if (flatFee < 0) {
            throw new IllegalArgumentException("Flat fee must not be negative");
        }
        this.flatFee = flatFee;
    }

    @Override
    public long calculate(int minutes, LocalTime entryTime) {
        if (minutes < 0) {
            throw new IllegalArgumentException("NEGATIVE_DURATION");
        }
        return flatFee;
    }
}