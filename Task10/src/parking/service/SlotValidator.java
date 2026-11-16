package parking.service;

public final class SlotValidator {
    private final int[] floorSizes;

    public SlotValidator(int[] floorSizes) {
        this.floorSizes = floorSizes.clone();
    }

    public boolean isValidSlot(int floor, int slotNumber) {
        return isValidFloor(floor) && slotNumber >= 1 && slotNumber <= floorSizes[floor];
    }

    boolean isValidFloor(int floor) {
        return floor >= 0 && floor < floorSizes.length;
    }

    private int capacity() {
        int total = 0;
        for (int size : floorSizes) {
            total += size;
        }
        return total;
    }

    public int totalCapacity() {
        return capacity();
    }
}