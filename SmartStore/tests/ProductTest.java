package com.smartstore.tests;

import com.smartstore.models.Product;

/**
 * ProductTest — manual unit tests for the Product class.
 *
 * Run this class directly to verify Product behaviour.
 * (For a real project, replace with JUnit 5 @Test methods.)
 *
 * Tests cover:
 *   - Both constructors
 *   - Getters and setters
 *   - reduceStock() — valid and invalid cases
 *   - isInStock() and isLowStock()
 *   - File serialisation (toFileString / fromFileString)
 */
public class ProductTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("═".repeat(50));
        System.out.println("  SMARTSTORE — Product Unit Tests");
        System.out.println("═".repeat(50));

        testConstructorFull();
        testConstructorNoCategory();
        testGettersAndSetters();
        testReduceStockValid();
        testReduceStockInvalid();
        testIsInStock();
        testIsLowStock();
        testFileSerialisation();

        System.out.println("\n" + "─".repeat(50));
        System.out.println("  Results: " + passed + " passed, " + failed + " failed.");
        System.out.println("═".repeat(50));
    }

    // ── Tests ────────────────────────────────────────────────

    static void testConstructorFull() {
        Product p = new Product("P001", "Java Book", 4500.0, 10, "Books");
        assertEqual("Full constructor — ID",       "P001",    p.getId());
        assertEqual("Full constructor — name",     "Java Book", p.getName());
        assertEqual("Full constructor — category", "Books",   p.getCategory());
        assertDoubleEqual("Full constructor — price", 4500.0, p.getPrice());
        assertIntEqual("Full constructor — stock",    10,     p.getStock());
    }

    static void testConstructorNoCategory() {
        Product p = new Product("P002", "Mouse", 6200.0, 8);
        assertEqual("No-category constructor defaults to 'General'", "General", p.getCategory());
    }

    static void testGettersAndSetters() {
        Product p = new Product("P003", "Keyboard", 18500.0, 3, "Peripherals");
        p.setName("Mech Keyboard");
        p.setPrice(20000.0);
        p.setStock(5);
        p.setCategory("Input Devices");
        assertEqual("setName",     "Mech Keyboard",  p.getName());
        assertDoubleEqual("setPrice", 20000.0,         p.getPrice());
        assertIntEqual("setStock",    5,               p.getStock());
        assertEqual("setCategory", "Input Devices",  p.getCategory());
    }

    static void testReduceStockValid() {
        Product p = new Product("P004", "Cable", 1500.0, 10, "Accessories");
        p.reduceStock(3);
        assertIntEqual("reduceStock valid — stock after reduction", 7, p.getStock());
    }

    static void testReduceStockInvalid() {
        Product p = new Product("P005", "Stand", 9000.0, 2, "Accessories");
        try {
            p.reduceStock(5);   // Should throw — only 2 in stock
            fail("reduceStock should throw when quantity exceeds stock");
        } catch (IllegalArgumentException e) {
            pass("reduceStock throws IllegalArgumentException when over-reducing");
        }
    }

    static void testIsInStock() {
        Product inStock  = new Product("P006", "Webcam", 14000.0, 7, "Peripherals");
        Product outOfStock = new Product("P007", "Kit", 1200.0, 0, "Accessories");
        assertTrue("isInStock — true when stock > 0", inStock.isInStock());
        assertFalse("isInStock — false when stock == 0", outOfStock.isInStock());
    }

    static void testIsLowStock() {
        Product low  = new Product("P008", "Drive", 2800.0, 3, "Storage");
        Product high = new Product("P009", "Book",  4500.0, 15, "Books");
        Product zero = new Product("P010", "Pad",   800.0,  0,  "Accessories");
        assertTrue("isLowStock — true when stock <= 5 and > 0", low.isLowStock());
        assertFalse("isLowStock — false when stock > 5", high.isLowStock());
        assertFalse("isLowStock — false when stock == 0", zero.isLowStock());
    }

    static void testFileSerialisation() {
        Product original = new Product("P011", "Test Product", 3000.0, 20, "Test");
        String  fileLine = original.toFileString();
        Product restored = Product.fromFileString(fileLine);
        assertEqual("fromFileString — ID",       original.getId(),       restored.getId());
        assertEqual("fromFileString — name",     original.getName(),     restored.getName());
        assertEqual("fromFileString — category", original.getCategory(), restored.getCategory());
        assertDoubleEqual("fromFileString — price", original.getPrice(), restored.getPrice());
        assertIntEqual("fromFileString — stock",    original.getStock(), restored.getStock());
    }

    // ── Assertion helpers ────────────────────────────────────

    static void assertEqual(String label, String expected, String actual) {
        if (expected.equals(actual)) pass(label);
        else fail(label + " | Expected: '" + expected + "', Got: '" + actual + "'");
    }

    static void assertIntEqual(String label, int expected, int actual) {
        if (expected == actual) pass(label);
        else fail(label + " | Expected: " + expected + ", Got: " + actual);
    }

    static void assertDoubleEqual(String label, double expected, double actual) {
        if (Math.abs(expected - actual) < 0.001) pass(label);
        else fail(label + " | Expected: " + expected + ", Got: " + actual);
    }

    static void assertTrue(String label, boolean condition) {
        if (condition) pass(label); else fail(label + " — expected true, got false");
    }

    static void assertFalse(String label, boolean condition) {
        if (!condition) pass(label); else fail(label + " — expected false, got true");
    }

    static void pass(String label) {
        System.out.println("  ✔  " + label);
        passed++;
    }

    static void fail(String label) {
        System.out.println("  ✘  FAIL: " + label);
        failed++;
    }
}
