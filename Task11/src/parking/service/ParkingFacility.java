package parking.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import parking.model.Car;
import parking.model.DriverCategory;
import parking.model.ElectricCar;
import parking.model.MonthlyPass;
import parking.model.ParkingPass;
import parking.model.ParkingTicket;
import parking.model.TwoWheeler;
import parking.model.Vehicle;

public final class ParkingFacility {
    private final String[][] slots;
    private final Map<String, ParkingTicket> activeTickets = new HashMap<>();

    public ParkingFacility(int[] slotsPerFloor) {
        if (slotsPerFloor == null) {
            throw new IllegalArgumentException("INVALID_FACILITY");
        }
        slots = new String[slotsPerFloor.length][];
        for (int floor = 0; floor < slotsPerFloor.length; floor++) {
            if (slotsPerFloor[floor] < 0) {
                throw new IllegalArgumentException("INVALID_FACILITY");
            }
            slots[floor] = new String[slotsPerFloor[floor]];
        }
    }

    public ParkingTicket enter(String type, String regNo, DriverCategory category, ParkingPass pass) {
        if (category == null) {
            throw new IllegalArgumentException("INVALID_CATEGORY");
        }
        if (activeTickets.containsKey(regNo)) {
            throw new IllegalArgumentException("DUPLICATE_REGISTRATION");
        }
        Vehicle vehicle = createVehicle(type, regNo);
        int[] location = allocate(regNo);
        if (location == null) {
            throw new IllegalStateException("NO_SLOT_FREE");
        }
        return activeTickets.computeIfAbsent(regNo, key -> new ParkingTicket(
                vehicle, category, pass, location[0], location[1], System.currentTimeMillis()));
    }

    public long exit(String regNo, int minutes, int kWh) {
        if (minutes < 0) {
            throw new IllegalArgumentException("NEGATIVE_DURATION");
        }
        if (kWh < 0) {
            throw new IllegalArgumentException("NEGATIVE_ENERGY");
        }
        ParkingTicket ticket = activeTickets.get(regNo);
        if (ticket == null) {
            throw new IllegalArgumentException("UNKNOWN_TICKET");
        }
        long parkingFee = ticket.getVehicle().calculateFee(minutes);
        boolean passFree = ticket.getCategory() != DriverCategory.VISITOR
                && ticket.getPass() != null
                && ticket.getPass().isValidFor(ticket.getCategory(), LocalDate.now());
        long totalFee = (passFree ? 0 : parkingFee) + ticket.getVehicle().chargingFee(kWh);
        slots[ticket.getFloor()][ticket.getSlot()] = null;
        activeTickets.remove(regNo);
        return totalFee;
    }

    public boolean isSlotAvailable(int floor, int slot) {
        validateSlot(floor, slot);
        return slots[floor][slot] == null;
    }

    public int[] getLocation(String regNo) {
        ParkingTicket ticket = activeTickets.get(regNo);
        if (ticket == null) {
            throw new IllegalArgumentException("UNKNOWN_TICKET");
        }
        return new int[] {ticket.getFloor(), ticket.getSlot()};
    }

    public String slotOccupant(int floor, int slot) {
        validateSlot(floor, slot);
        return slots[floor][slot];
    }

    private int[] allocate(String regNo) {
        for (int floor = 0; floor < slots.length; floor++) {
            for (int slot = 0; slot < slots[floor].length; slot++) {
                if (slots[floor][slot] == null) {
                    slots[floor][slot] = regNo;
                    return new int[] {floor, slot};
                }
            }
        }
        return null;
    }

    private void validateSlot(int floor, int slot) {
        if (floor < 0 || floor >= slots.length || slot < 0 || slot >= slots[floor].length) {
            throw new IllegalArgumentException("INVALID_SLOT");
        }
    }

    private Vehicle createVehicle(String type, String regNo) {
        if (type == null) {
            throw new IllegalArgumentException("UNKNOWN_VEHICLE_TYPE");
        }
        return switch (type) {
            case "TwoWheeler" -> new TwoWheeler(regNo);
            case "Car" -> new Car(regNo);
            case "ElectricCar" -> new ElectricCar(regNo);
            default -> throw new IllegalArgumentException("UNKNOWN_VEHICLE_TYPE");
        };
    }
}