package parking.service;

public interface Chargeable {
    double chargingFee(int kWh);

    default String chargingSummary(int kWh) {
        return kWh + " kWh charged for Rs " + chargingFee(kWh);
    }

    static int defaultRatePerKWh() {
        return 12;
    }
}