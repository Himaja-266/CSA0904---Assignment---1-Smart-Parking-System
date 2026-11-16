package parking.service;

import java.time.LocalTime;

public final class HourlyPricingRule implements PricingRule {
    private final long firstHourRate;
    private final long additionalHourRate;
    private final long dailyMaximum;

    public HourlyPricingRule(long firstHourRate, long additionalHourRate, long dailyMaximum) {
        if (firstHourRate < 0 || additionalHourRate < 0 || dailyMaximum < 0) {
            throw new IllegalArgumentException("Rates must not be negative");
        }
        this.firstHourRate = firstHourRate;
        this.additionalHourRate = additionalHourRate;
        this.dailyMaximum = dailyMaximum;
    }

    @Override
    public long calculate(int minutes, LocalTime entryTime) {
        if (minutes < 0) {
            throw new IllegalArgumentException("NEGATIVE_DURATION");
        }
        int hours = (minutes + 59) / 60;
        if (hours == 0) {
            return 0;
        }
        return Math.min(firstHourRate + (hours - 1L) * additionalHourRate, dailyMaximum);
    }
}