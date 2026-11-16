package parking.service;

public interface Payable {
    void pay(double amount);

    default String paymentStatus() {
        return "Payment accepted";
    }

    static boolean isValidAmount(double amount) {
        return Double.isFinite(amount) && amount >= 0.0;
    }
}