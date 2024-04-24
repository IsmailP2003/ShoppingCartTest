package org.example;

public class Offer {
    private int buyQuantity;
    private int freeQuantity;
    private double discountRate;

    // constructor
    public Offer(int buyQuantity, int freeQuantity, double discountRate) {
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.discountRate = discountRate;
    }

    // getters
    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }
    public double getDiscountRate() {
        return discountRate;
    }
}
