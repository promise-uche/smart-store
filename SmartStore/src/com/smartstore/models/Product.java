package com.smartstore.models;

/**
 * Represents a product in the SmartStore catalog.
 *
 * Demonstrates:
 *   - Encapsulation (private fields, public getters/setters)
 *   - Constructor overloading (two constructors)
 *   - toString() override
 */
public class Product {

    // ── Fields ──────────────────────────────────────────────
    private String  id;
    private String  name;
    private double  price;
    private int     stock;
    private String  category;

    // ── Constructor 1: Full details ──────────────────────────
    public Product(String id, String name, double price, int stock, String category) {
        this.id       = id;
        this.name     = name;
        this.price    = price;
        this.stock    = stock;
        this.category = category;
    }

    // ── Constructor 2: No category (defaults to "General") ──
    public Product(String id, String name, double price, int stock) {
        this(id, name, price, stock, "General");
    }

    // ── Getters ──────────────────────────────────────────────
    public String getId()       { return id; }
    public String getName()     { return name; }
    public double getPrice()    { return price; }
    public int    getStock()    { return stock; }
    public String getCategory() { return category; }

    // ── Setters ──────────────────────────────────────────────
    public void setName(String name)         { this.name = name; }
    public void setPrice(double price)       { this.price = price; }
    public void setStock(int stock)          { this.stock = stock; }
    public void setCategory(String category) { this.category = category; }

    // ── Business method ─────────────────────────────────────
    /**
     * Reduces stock by the given quantity.
     * @throws IllegalArgumentException if quantity exceeds available stock.
     */
    public void reduceStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalArgumentException(
                "Not enough stock for '" + name + "'. Available: " + stock
            );
        }
        this.stock -= quantity;
    }

    public boolean isInStock() {
        return stock > 0;
    }

    public boolean isLowStock() {
        return stock > 0 && stock <= 5;
    }

    // ── File serialisation ───────────────────────────────────
    /**
     * Converts product to a pipe-delimited string for file storage.
     * Format: id|name|price|stock|category
     */
    public String toFileString() {
        return id + "|" + name + "|" + price + "|" + stock + "|" + category;
    }

    /**
     * Reconstructs a Product from a pipe-delimited file line.
     */
    public static Product fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid product data: " + line);
        }
        return new Product(
            parts[0],
            parts[1],
            Double.parseDouble(parts[2]),
            Integer.parseInt(parts[3]),
            parts[4]
        );
    }

    // ── Display ──────────────────────────────────────────────
    @Override
    public String toString() {
        String stockLabel = isLowStock() ? " ⚠ LOW STOCK" : "";
        return String.format("%-6s %-26s ₦%,-10.2f  Stock: %d%s",
            id, name, price, stock, stockLabel);
    }
}
