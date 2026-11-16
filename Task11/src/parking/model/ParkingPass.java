package parking.model;

import java.time.LocalDate;

public interface ParkingPass {
    boolean isValidFor(DriverCategory category, LocalDate date);
}