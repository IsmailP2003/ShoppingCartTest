package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

// Shopping cart class
public class ShoppingCart {
    private Map<Product, Integer> products;

    // constructor
    public ShoppingCart() {
        this.products = new HashMap<>();
    }

    // methods
    public void addProduct(Product product, int quantity) {
        products.put(product, products.getOrDefault(product, 0) + quantity);
    }
    // getters
    // Calculate the total price of the shopping cart
    public BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getOffer() != null) {
                double discountRate = product.getOffer().getDiscountRate();
                if (discountRate == 0.5) {
                    int fullPriceQuantity = (quantity + 1) / 2; // round up if quantity is odd
                    int discountedQuantity = quantity / 2; // round down if quantity is odd
                    totalPrice = totalPrice.add(product.getUnitPrice().multiply(new BigDecimal(fullPriceQuantity + discountedQuantity * 0.5)));
                } else {
                    int buyQuantity = product.getOffer().getBuyQuantity();
                    int freeQuantity = product.getOffer().getFreeQuantity();
                    int offerApplicableTimes = quantity / (buyQuantity + freeQuantity);
                    int remainingQuantity = quantity % (buyQuantity + freeQuantity);
                    totalPrice = totalPrice.add(product.getUnitPrice().multiply(new BigDecimal(offerApplicableTimes * buyQuantity + remainingQuantity)));
                }
            } else {
                totalPrice = totalPrice.add(product.getUnitPrice().multiply(new BigDecimal(quantity)));
            }
        }
        // Apply global discount if total price is greater than or equal to 500
        if (totalPrice.compareTo(new BigDecimal("500")) >= 0) {
            totalPrice = totalPrice.multiply(new BigDecimal("0.8")); // 20% discount
        }
        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }



    // getters
    // Calculate the total quantity of the shopping cart
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (int quantity : products.values()) {
            totalQuantity += quantity;
        }
        return totalQuantity;
    }

    // Calculate the total discount of the shopping cart
    // The discount is the total price of the products with an offer
    public BigDecimal calculateTotalDiscount() {
        BigDecimal totalDiscount = BigDecimal.ZERO;
        BigDecimal totalPriceWithoutDiscount = calculateTotalPrice().divide(new BigDecimal("0.8"), 2, RoundingMode.HALF_UP);
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getOffer() != null) {
                double discountRate = product.getOffer().getDiscountRate();
                if (discountRate == 0.5) {
                    int discountedQuantity = quantity / 2; // round down if quantity is odd
                    totalDiscount = totalDiscount.add(product.getUnitPrice().multiply(new BigDecimal(discountedQuantity * 0.5)));
                } else {
                    int buyQuantity = product.getOffer().getBuyQuantity();
                    int freeQuantity = product.getOffer().getFreeQuantity();
                    int offerApplicableTimes = quantity / (buyQuantity + freeQuantity);
                    totalDiscount = totalDiscount.add(product.getUnitPrice().multiply(new BigDecimal(offerApplicableTimes * freeQuantity)));
                }
            }
        }
        if (totalPriceWithoutDiscount.compareTo(new BigDecimal("500")) >= 0) {
            totalDiscount = totalDiscount.add(totalPriceWithoutDiscount.subtract(totalPriceWithoutDiscount.multiply(new BigDecimal("0.8")))); // 20% discount
        }

        return totalDiscount.setScale(2, RoundingMode.HALF_UP);
    }


    // Calculate the total tax of the shopping cart
    public BigDecimal calculateTotalTax(BigDecimal taxRate) {
        return calculateTotalPrice().multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate the total price of the shopping cart with tax
    public BigDecimal calculateTotalPriceWithTax(BigDecimal taxRate) {
        return calculateTotalPrice().add(calculateTotalTax(taxRate));
    }
    // Remove a product from the shopping cart
    public void removeProduct(Product product, int quantity) {
        int currentQuantity = products.getOrDefault(product, 0);
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("Not enough quantity to remove");
        }
        products.put(product, currentQuantity - quantity);
    }

    // getters
    public Map<Product, Integer> getProducts() {
        return products;
    }
}
