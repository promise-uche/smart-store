package com.smartstore.models;

/**
 * Represents a single item in a customer's shopping cart.
 * Holds a reference to the Product and the chosen quantity.
 */
public class CartItem {

    private Product product;
    private int     quantity;

    public CartItem(Product product, int quantity) {
        this.product  = product;
        this.quantity = quantity;
    }

    // ── Getters ──────────────────────────────────────────────
    public Product getProduct()  { return product; }
    public int     getQuantity() { return quantity; }

    // ── Business logic ───────────────────────────────────────
    public double getLineTotal() {
        return product.getPrice() * quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // ── Display ──────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("%-26s x%-4d  ₦%,.2f",
            product.getName(), quantity, getLineTotal());
    }
}
