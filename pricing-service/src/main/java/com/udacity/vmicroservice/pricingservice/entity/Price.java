package com.udacity.vmicroservice.pricingservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Represents the price of a given vehicle, including currency.
 */

@Entity
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleid;

    private String currency;
    private BigDecimal price;

    public Price() {
    }

    public Price(Long vehicleid, String currency, BigDecimal price) {
        this.currency = currency;
        this.price = price;
        this.vehicleid = vehicleid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getVehicleId() {
        return vehicleid;
    }

    public void setVehicleId(Long vehicleid) {
        this.vehicleid = vehicleid;
    }
}

