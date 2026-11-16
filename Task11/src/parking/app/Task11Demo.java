package parking.app;

import java.time.LocalDate;
import java.util.function.Supplier;
import parking.model.DriverCategory;
import parking.model.MonthlyPass;
import parking.service.ParkingFacility;

public final class Task11Demo {
    private Task11Demo() {
    }

    public static void main(String[] args) {
        System.out.println("ID | Input | Expected | Actual | Result");
        test("T01", "visitor TwoWheeler, 130 min", "26", () -> {
            ParkingFacility facility = new ParkingFacility(new int[] {2});
            facility.enter("TwoWheeler", "TN01A1", DriverCategory.VISITOR, null);
            return Long.toString(facility.exit("TN01A1", 130, 0));
        });
        test("T02", "visitor Car, 130 min", "70", () -> {
            ParkingFacility facility = new ParkingFacility(new int[] {2});
            facility.enter("Car", "TN02B2", DriverCategory.VISITOR, null);
            return Long.toString(facility.exit("TN02B2", 130, 0));
        });
        test("T03", "visitor ElectricCar, 130 min + 10 kWh", "190", () -> {
            ParkingFacility facility = new ParkingFacility(new int[] {2});
            facility.enter("ElectricCar", "TN03C3", DriverCategory.VISITOR, null);
            return Long.toString(facility.exit("TN03C3", 130, 10));
        });
        test("T04", "one-slot facility, first entry", "[0, 0]", () -> {
            ParkingFacility facility = new ParkingFacility(new int[] {1});
            facility.enter("Car", "LAST", DriverCategory.VISITOR, null);
            return java.util.Arrays.toString(facility.getLocation("LAST"));
        });
        test("T05", "visitor Car, 0 minutes", "0", () -> Long.toString(fee("CAR0", "Car", 0, 0)));
        test("T06", "visitor Car, exactly 60 minutes", "30", () -> Long.toString(fee("CAR60", "Car", 60, 0)));
        test("T07", "visitor Car, exactly 120 minutes", "50", () -> Long.toString(fee("CAR120", "Car", 120, 0)));
        test("T08", "visitor Car, exactly 1440 minutes", "200", () -> Long.toString(fee("CARMAX", "Car", 1440, 0)));
        test("T09", "unknown vehicle type", "UNKNOWN_VEHICLE_TYPE", () -> {
            ParkingFacility facility = new ParkingFacility(new int[] {1});
            try {
                facility.enter("Bus", "BUS1", DriverCategory.VISITOR, null);
            } catch (IllegalArgumentException exception) {
                return exception.getMessage();
            }
            return "NOT_REJECTED";
        });
        test("T10", "same regNo entered twice", "DUPLICATE_REGISTRATION", () -> {
            ParkingFacility facility = new ParkingFacility(new int[] {2});
            facility.enter("Car", "DUP1", DriverCategory.VISITOR, null);
            try {
                facility.enter("Car", "DUP1", DriverCategory.VISITOR, null);
            } catch (IllegalArgumentException exception) {
                return exception.getMessage();
            }
            return "NOT_REJECTED";
        });
        test("T11", "slot index floor=-1, slot=0", "INVALID_SLOT", () -> {
            ParkingFacility facility = new ParkingFacility(new int[] {1});
            try {
                facility.slotOccupant(-1, 0);
            } catch (IllegalArgumentException exception) {
                return exception.getMessage();
            }
            return "NOT_REJECTED";
        });
        test("T12", "visitor Car, negative duration", "NEGATIVE_DURATION", () -> {
            ParkingFacility facility = new ParkingFacility(new int[] {1});
            facility.enter("Car", "NEG1", DriverCategory.VISITOR, null);
            try {
                facility.exit("NEG1", -1, 0);
            } catch (IllegalArgumentException exception) {
                return exception.getMessage();
            }
            return "NOT_REJECTED";
        });
        test("T13", "valid student monthly pass, 130 min", "0", () -> {
            ParkingFacility facility = new ParkingFacility(new int[] {1});
            MonthlyPass pass = currentPass(DriverCategory.STUDENT);
            facility.enter("Car", "STU1", DriverCategory.STUDENT, pass);
            return Long.toString(facility.exit("STU1", 130, 0));
        });
        test("T14", "valid staff monthly pass, 130 min", "0", () -> {
            ParkingFacility facility = new ParkingFacility(new int[] {1});
            MonthlyPass pass = currentPass(DriverCategory.STAFF);
            facility.enter("TwoWheeler", "STAFF1", DriverCategory.STAFF, pass);
            return Long.toString(facility.exit("STAFF1", 130, 0));
        });
        test("T15", "no free slot after first vehicle", "NO_SLOT_FREE", () -> {
            ParkingFacility facility = new ParkingFacility(new int[] {1});
            facility.enter("Car", "FULL1", DriverCategory.VISITOR, null);
            try {
                facility.enter("Car", "FULL2", DriverCategory.VISITOR, null);
            } catch (IllegalStateException exception) {
                return exception.getMessage();
            }
            return "NOT_REJECTED";
        });

        System.out.println("Day scenario:");
        long actualTotal = dayScenario();
        System.out.println("Hand calculation: TwoWheeler 61 min = 18; pass-holder Car = 0; EV 1 min + 2 kWh = 30 + 24; total = 72");
        System.out.println("Program total: " + actualTotal);
    }

    private static long fee(String regNo, String type, int minutes, int kWh) {
        ParkingFacility facility = new ParkingFacility(new int[] {1});
        facility.enter(type, regNo, DriverCategory.VISITOR, null);
        return facility.exit(regNo, minutes, kWh);
    }

    private static MonthlyPass currentPass(DriverCategory category) {
        LocalDate today = LocalDate.now();
        return new MonthlyPass(category, today.minusDays(15), today.plusDays(15));
    }

    private static long dayScenario() {
        ParkingFacility facility = new ParkingFacility(new int[] {3});
        facility.enter("TwoWheeler", "DAY1", DriverCategory.VISITOR, null);
        long twoWheelerFee = facility.exit("DAY1", 61, 0);
        facility.enter("Car", "DAY2", DriverCategory.STUDENT, currentPass(DriverCategory.STUDENT));
        long studentFee = facility.exit("DAY2", 130, 0);
        facility.enter("ElectricCar", "DAY3", DriverCategory.VISITOR, null);
        long evFee = facility.exit("DAY3", 1, 2);
        return twoWheelerFee + studentFee + evFee;
    }

    private static void test(String id, String input, String expected, Supplier<String> action) {
        String actual;
        try {
            actual = action.get();
        } catch (RuntimeException exception) {
            actual = exception.getMessage();
        }
        String result = expected.equals(actual) ? "PASS" : "FAIL";
        System.out.println(id + " | " + input + " | " + expected + " | " + actual + " | " + result);
    }
}