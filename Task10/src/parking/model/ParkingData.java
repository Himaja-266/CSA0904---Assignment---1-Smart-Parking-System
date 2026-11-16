package parking.model;

public final class ParkingData {
    private int durationMinutes;
    private int slotNumber;

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes < 0) {
            throw new IllegalArgumentException("Duration must not be negative");
        }
        this.durationMinutes = durationMinutes;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber, int slotsOnFloor) {
        if (slotsOnFloor <= 0 || slotNumber < 1 || slotNumber > slotsOnFloor) {
            throw new IllegalArgumentException("Slot number is outside this floor");
        }
        this.slotNumber = slotNumber;
    }
}