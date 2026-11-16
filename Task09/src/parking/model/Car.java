package parking.model;

import parking.service.Payable;

public class Car extends Vehicle implements Payable {
    private double amountPaid;

    public Car(String regNo) {
        super(regNo);
    }

    @Override
    protected double ratePerHour() {
        return 30.0;
    }

    @Override
    public void pay(double amount) {
        if (!Payable.isValidAmount(amount)) {
            throw new IllegalArgumentException("Payment amount must be finite and non-negative");
        }
        amountPaid += amount;
    }

    public double getAmountPaid() {
        return amountPaid;
    }
}