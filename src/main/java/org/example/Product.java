package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Product class
public class Product {
    private String name;
    private BigDecimal unitPrice;
    private Offer offer;

    // constructor
    public Product(String name, BigDecimal unitPrice, Offer offer) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.offer = offer;
    }

    // getters
    public String getName() {
        return name;
    }

    // getters
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Offer getOffer() {
        return offer;
    }
}
