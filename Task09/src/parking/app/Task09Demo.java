package parking.app;

import parking.model.ElectricCar;
import parking.model.Vehicle;
import parking.service.Chargeable;
import parking.service.Payable;

public final class Task09Demo {
    private static final int WARMUP_RUNS = 5_000;
    private static final int MEASURED_RUNS = 50_000;

    private Task09Demo() {
    }

    public static void main(String[] args) {
        Vehicle vehicle = new ElectricCar("TN03C3");
        double parkingFee = vehicle.calculateFee(130);
        ElectricCar electricCar = (ElectricCar) vehicle;
        int energyFee = (int) electricCar.chargingFee(10);
        electricCar.pay(parkingFee + energyFee);

        System.out.println("Abstract Vehicle fee: Rs " + parkingFee);
        System.out.println(((Chargeable) electricCar).chargingSummary(10));
        System.out.println(((Payable) electricCar).paymentStatus());
        System.out.println("Amount paid: Rs " + electricCar.getAmountPaid());
        System.out.println("Default charging rate: Rs " + Chargeable.defaultRatePerKWh() + "/kWh");
        System.out.println("Valid zero payment: " + Payable.isValidAmount(0.0));

        double checksum = 0;
        for (int i = 0; i < WARMUP_RUNS; i++) {
            checksum += vehicle.calculateFee(i % 180);
        }
        long start = System.nanoTime();
        for (int i = 0; i < MEASURED_RUNS; i++) {
            checksum += vehicle.calculateFee(i % 180);
        }
        long elapsed = System.nanoTime() - start;
        System.out.println("Average abstract-dispatch fee call after warm-up: "
                + (elapsed / MEASURED_RUNS) + " ns/op");
        if (checksum == 0) {
            throw new AssertionError("Fee calls must be evaluated");
        }
    }
}