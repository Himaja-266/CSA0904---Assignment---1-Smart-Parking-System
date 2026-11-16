package parking.service;

import java.time.LocalTime;

public final class DeliveryVanPricingRule implements PricingRule {
    private static final long DAILY_MAXIMUM = 400;

    @Override
    public long calculate(int minutes, LocalTime entryTime) {
        if (minutes < 0) {
            throw new IllegalArgumentException("NEGATIVE_DURATION");
        }
        int hours = (minutes + 59) / 60;
        long fee = 0;
        for (int hour = 0; hour < hours; hour++) {
            LocalTime billedHourStart = entryTime.plusHours(hour);
            boolean businessHours = !billedHourStart.isBefore(LocalTime.of(9, 0))
                    && billedHourStart.isBefore(LocalTime.of(17, 0));
            long carRate = hour == 0 ? 30 : 20;
            fee += businessHours ? carRate * 2 : carRate;
        }
        return Math.min(fee, DAILY_MAXIMUM);
    }
}