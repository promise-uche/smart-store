package com.smartstore.models;

/**
 * Represents a customer of the SmartStore.
 *
 * Demonstrates:
 *   - Encapsulation (private fields, public getters/setters)
 *   - Constructor overloading (two constructors)
 *   - Input validation inside setters
 */
public class Customer {

    // ── Fields ──────────────────────────────────────────────
    private String name;
    private String email;
    private String phone;

    // ── Constructor 1: Full details ──────────────────────────
    public Customer(String name, String email, String phone) {
        setName(name);
        setEmail(email);
        this.phone = phone;
    }

    // ── Constructor 2: Name and email only ───────────────────
    public Customer(String name, String email) {
        this(name, email, "Not provided");
    }

    // ── Getters ──────────────────────────────────────────────
    public String getName()  { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    // ── Setters with validation ──────────────────────────────
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        this.name = name.trim();
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        this.email = email.trim().toLowerCase();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // ── Display ──────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("Customer: %s | Email: %s | Phone: %s", name, email, phone);
    }
}
