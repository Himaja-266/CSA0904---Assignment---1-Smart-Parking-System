package parking.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import parking.model.Car;
import parking.model.DeliveryVan;
import parking.model.EBike;
import parking.model.OneDayPass;
import parking.model.ParkingTicket;
import parking.model.Pass;
import parking.model.Vehicle;

public final class ParkingFacility {
    private final String[][] slots;
    private final Map<String, ParkingTicket> active = new HashMap<>();

    public ParkingFacility(int[] slotsPerFloor) {
        slots = new String[slotsPerFloor.length][];
        for (int floor = 0; floor < slotsPerFloor.length; floor++) {
            if (slotsPerFloor[floor] < 0) {
                throw new IllegalArgumentException("Negative floor capacity");
            }
            slots[floor] = new String[slotsPerFloor[floor]];
        }
    }

    public ParkingTicket enter(String type, String regNo, LocalTime entryTime, Pass pass) {
        if (entryTime == null) {
            throw new IllegalArgumentException("INVALID_ENTRY_TIME");
        }
        if (active.containsKey(regNo)) {
            throw new IllegalArgumentException("DUPLICATE_REGISTRATION");
        }
        Vehicle vehicle = createVehicle(type, regNo);
        for (int floor = 0; floor < slots.length; floor++) {
            for (int slot = 0; slot < slots[floor].length; slot++) {
                if (slots[floor][slot] == null) {
                    slots[floor][slot] = regNo;
                    ParkingTicket ticket = new ParkingTicket(vehicle, floor, slot, entryTime, pass);
                    active.put(regNo, ticket);
                    return ticket;
                }
            }
        }
        throw new IllegalStateException("NO_SLOT_FREE");
    }

    public long exit(String regNo, int minutes, LocalDate exitDate) {
        if (minutes < 0) {
            throw new IllegalArgumentException("NEGATIVE_DURATION");
        }
        ParkingTicket ticket = active.get(regNo);
        if (ticket == null) {
            throw new IllegalArgumentException("UNKNOWN_TICKET");
        }
        boolean passFree = ticket.getPass() != null && ticket.getPass().isValidOn(exitDate);
        long fee = passFree ? 0 : ticket.getVehicle().calculateFee(minutes, ticket.getEntryTime());
        slots[ticket.getFloor()][ticket.getSlot()] = null;
        active.remove(regNo);
        return fee;
    }

    public int getFloorCount() {
        return slots.length;
    }

    public int[] getCapacities() {
        int[] capacities = new int[slots.length];
        for (int i = 0; i < slots.length; i++) {
            capacities[i] = slots[i].length;
        }
        return capacities;
    }

    private Vehicle createVehicle(String type, String regNo) {
        if (type == null) {
            throw new IllegalArgumentException("UNKNOWN_VEHICLE_TYPE");
        }
        return switch (type) {
            case "Car" -> new Car(regNo);
            case "EBike" -> new EBike(regNo);
            case "DeliveryVan" -> new DeliveryVan(regNo);
            default -> throw new IllegalArgumentException("UNKNOWN_VEHICLE_TYPE");
        };
    }
}