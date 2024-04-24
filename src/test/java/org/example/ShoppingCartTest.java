package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartTest {

    @Test
    @DisplayName("Add products to the shopping cart")
    public void testAddProduct() {
        // Given
        ShoppingCart cart = new ShoppingCart();
        Product doveSoap = new Product("Dove Soap", new BigDecimal("39.99"),null);

        // When
        // Add 5 Dove Soaps to the shopping cart
        cart.addProduct(doveSoap, 5);

        // Then
        // The shopping cart should contain 5 Dove Soaps
        // The total price should be 199.95
        assertEquals(5, cart.getTotalQuantity());
        assertEquals(new BigDecimal("199.95"), cart.calculateTotalPrice());
    }
    @Test
    @DisplayName("Add additional products of the same type to the shopping cart.")
    public void testAddAdditionalProduct() {
        // Given
        ShoppingCart cart = new ShoppingCart();
        Product doveSoap = new Product("Dove Soap", new BigDecimal("39.99"),null);

        // When
        // Add 5 Dove Soaps to the shopping cart
        // Add 3 more Dove Soaps to the shopping cart
        cart.addProduct(doveSoap, 5);
        cart.addProduct(doveSoap, 3);

        // Then
        // The shopping cart should contain 8 Dove Soaps
        // The total price should be 319.92
        assertEquals(8, cart.getTotalQuantity());
        assertEquals(new BigDecimal("319.92"), cart.calculateTotalPrice());
    }
    @Test
    @DisplayName("Calculate the tax rate of the shopping cart with multiple items")
    public void testCalculateTax() {
        // Given
        ShoppingCart cart = new ShoppingCart();
        Product doveSoap = new Product("Dove Soap", new BigDecimal("39.99"),null);
        Product axeDeo = new Product("Axe Deo", new BigDecimal("99.99"),null);
        BigDecimal taxRate = new BigDecimal("0.125"); // 12.5%

        // When
        // Add 2 Dove Soaps to the shopping cart
        // Add 2 Axe Deos to the shopping cart
        cart.addProduct(doveSoap, 2);
        cart.addProduct(axeDeo, 2);

        // Then
        // The shopping cart should contain 4 products
        // The total tax should be 35.00
        // The total price with tax should be 314.96
        assertEquals(4, cart.getTotalQuantity());
        assertEquals(new BigDecimal("35.00"), cart.calculateTotalTax(taxRate));
        assertEquals(new BigDecimal("314.96"), cart.calculateTotalPriceWithTax(taxRate));
    }

    @Test
    @DisplayName("Add products to the shopping cart, which have 'Buy X, Get Y free' offer")
    public void testAddProductWithOffer() {
        // Given
        ShoppingCart cart = new ShoppingCart();
        Product doveSoap = new Product("Dove Soap", new BigDecimal("39.99"), new Offer(2, 1, 0.0));
        Product axeDeo = new Product("Axe Deo", new BigDecimal("89.99"), null);
        BigDecimal taxRate = new BigDecimal("0.125"); // 12.5%

        // When
        cart.addProduct(doveSoap, 3);

        // Then
        // The shopping cart should contain 3 Dove Soaps
        // The total price should be 79.98
        // The total discount should be 39.99
        // The total tax should be 10.00
        assertEquals(3, cart.getProducts().get(doveSoap));
        assertEquals(new BigDecimal("79.98"), cart.calculateTotalPrice());
        assertEquals(new BigDecimal("39.99"), cart.calculateTotalDiscount());
        assertEquals(new BigDecimal("10.00"), cart.calculateTotalTax(taxRate));

        // When
        cart.addProduct(doveSoap, 2);

        // Then
        // The shopping cart should contain 5 Dove Soaps
        // The total price should be 159.96
        // The total discount should be 39.99
        // The total tax should be 20.00
        assertEquals(5, cart.getProducts().get(doveSoap));
        assertEquals(new BigDecimal("159.96"), cart.calculateTotalPrice());
        assertEquals(new BigDecimal("39.99"), cart.calculateTotalDiscount());
        assertEquals(new BigDecimal("20.00"), cart.calculateTotalTax(taxRate));

        // When
        // Add 2 Axe Deos to the shopping cart
        // Remove 2 Dove Soaps from the shopping cart
        cart.removeProduct(doveSoap, 2);
        cart.addProduct(axeDeo, 2);

        // Then
        // The shopping cart should contain 3 products
        // The total price should be 259.96
        // The total discount should be 39.99
        // The total tax should be 32.50
        assertEquals(3, cart.getProducts().get(doveSoap));
        assertEquals(2, cart.getProducts().get(axeDeo));
        assertEquals(new BigDecimal("259.96"), cart.calculateTotalPrice());
        assertEquals(new BigDecimal("39.99"), cart.calculateTotalDiscount());
        assertEquals(new BigDecimal("32.50"), cart.calculateTotalTax(taxRate));
    }

    @Test
    @DisplayName("Add products to the shopping cart, which have 'Buy 1, Get 50% discount on next one' offer")
    public void testAddProductWithDiscountOffer() {
        // Given
        ShoppingCart cart = new ShoppingCart();
        Product doveSoap = new Product("Dove Soap", new BigDecimal("39.99"), new Offer(1, 0, 0.5));
        BigDecimal taxRate = new BigDecimal("0.125"); // 12.5%

        // When
        // Add 2 Dove Soaps to the shopping cart
        cart.addProduct(doveSoap, 2);

        // Then
        // The shopping cart should contain 2 Dove Soaps
        // The total price should be 59.99
        // The total discount should be 20.00
        // The total tax should be 7.50
        assertEquals(2, cart.getProducts().get(doveSoap));
        assertEquals(new BigDecimal("59.99"), cart.calculateTotalPrice());
        assertEquals(new BigDecimal("20.00"), cart.calculateTotalDiscount());
        assertEquals(new BigDecimal("7.50"), cart.calculateTotalTax(taxRate));
    }
    @Test
    @DisplayName("Apply 20% discount on ALL items in the Shopping Cart if the total cost of your purchase is greater than or equal to 500")
    public void testGlobalDiscount() {
        // Given
        ShoppingCart cart = new ShoppingCart();
        Product doveSoap = new Product("Dove Soap", new BigDecimal("39.99"), null);
        Product axeDeo = new Product("Axe Deo", new BigDecimal("89.99"), null);
        BigDecimal taxRate = new BigDecimal("0.125"); // 12.5%

        // When
        // Add 5 Dove Soaps to the shopping cart
        // Add 4 Axe Deos to the shopping cart
        cart.addProduct(doveSoap, 5);
        cart.addProduct(axeDeo, 4);

        // Then
        // The shopping cart should contain 9 products
        // The total price should be 447.93
        // The total discount should be 111.98
        // The total tax should be 55.99
        assertEquals(5, cart.getProducts().get(doveSoap));
        assertEquals(4, cart.getProducts().get(axeDeo));
        assertEquals(new BigDecimal("447.93"), cart.calculateTotalPrice());
        assertEquals(new BigDecimal("111.98"), cart.calculateTotalDiscount());
        assertEquals(new BigDecimal("55.99"), cart.calculateTotalTax(taxRate));
    }


}