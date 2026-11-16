package parking.service;

import java.time.LocalTime;

public interface PricingRule {
    long calculate(int minutes, LocalTime entryTime);
}