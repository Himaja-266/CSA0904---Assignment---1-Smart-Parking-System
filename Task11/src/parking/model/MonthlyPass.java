package parking.model;

import java.time.LocalDate;

public final class MonthlyPass implements ParkingPass {
    private final DriverCategory category;
    private final LocalDate validFrom;
    private final LocalDate validUntil;

    public MonthlyPass(DriverCategory category, LocalDate validFrom, LocalDate validUntil) {
        if (category == DriverCategory.VISITOR || validFrom == null || validUntil == null
                || validUntil.isBefore(validFrom)) {
            throw new IllegalArgumentException("Invalid monthly pass details");
        }
        this.category = category;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    @Override
    public boolean isValidFor(DriverCategory requestedCategory, LocalDate date) {
        return category == requestedCategory && !date.isBefore(validFrom) && !date.isAfter(validUntil);
    }
}