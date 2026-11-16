package parking.model;

import parking.service.DeliveryVanPricingRule;

public final class DeliveryVan extends Vehicle {
    public DeliveryVan(String regNo) {
        super(regNo, new DeliveryVanPricingRule());
    }
}