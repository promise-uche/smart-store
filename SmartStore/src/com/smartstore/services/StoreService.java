package com.smartstore.services;

import com.smartstore.models.*;
import com.smartstore.utils.ConsoleUI;
import com.smartstore.utils.FileManager;

import java.util.ArrayList;
import java.util.List;

/**
 * StoreService — the heart of the SmartStore application.
 *
 * Manages:
 *   - Product catalog (ArrayList<Product>)
 *   - Shopping cart  (ArrayList<CartItem>)
 *   - Order history  (ArrayList<Order>)
 *   - All user-facing store operations
 *
 * Demonstrates: ArrayList usage, exception handling, File I/O delegation.
 */
public class StoreService {

    private ArrayList<Product>  catalog  = new ArrayList<>();
    private ArrayList<CartItem> cart     = new ArrayList<>();
    private ArrayList<Order>    orders   = new ArrayList<>();
    private int                 orderCounter = 1;

    // ── Inventory File I/O ───────────────────────────────────

    public void loadInventory() {
        List<String> lines = FileManager.readLines("data/inventory.txt");
        for (String line : lines) {
            try {
                catalog.add(Product.fromFileString(line));
            } catch (Exception e) {
                System.out.println("  [Warning] Skipping bad line in inventory: " + line);
            }
        }
        System.out.println("  ✔  " + catalog.size() + " products loaded from inventory.\n");
    }

    public void saveInventory() {
        List<String> lines = new ArrayList<>();
        for (Product p : catalog) {
            lines.add(p.toFileString());
        }
        FileManager.writeLines("data/inventory.txt", lines);
        System.out.println("  ✔  Inventory saved to data/inventory.txt");
    }

    // ── Browse Products ──────────────────────────────────────

    public void browseProducts(ConsoleUI ui) {
        ui.printHeader("PRODUCT CATALOG");
        if (catalog.isEmpty()) {
            ui.printError("No products available.");
            return;
        }

        ui.print(String.format("  %-6s %-26s %-14s %-8s %s",
            "ID", "Name", "Price (₦)", "Stock", "Category"));
        ui.printDivider();

        for (Product p : catalog) {
            ui.print("  " + p.toString() + "  [" + p.getCategory() + "]");
        }
        ui.printDivider();

        String choice = ui.getStringInput("\nEnter Product ID to add to cart (or 0 to go back): ");
        if (choice.equals("0")) return;

        addToCart(choice, ui);
    }

    // ── Search Product ───────────────────────────────────────

    public void searchProduct(ConsoleUI ui) {
        ui.printHeader("SEARCH PRODUCTS");
        String keyword = ui.getStringInput("Enter product name or category to search: ").toLowerCase();

        List<Product> results = new ArrayList<>();
        for (Product p : catalog) {
            if (p.getName().toLowerCase().contains(keyword) ||
                p.getCategory().toLowerCase().contains(keyword)) {
                results.add(p);
            }
        }

        if (results.isEmpty()) {
            ui.printError("No products found matching: \"" + keyword + "\"");
        } else {
            ui.print("\n  Found " + results.size() + " result(s):\n");
            for (Product p : results) {
                ui.print("  " + p);
            }

            String choice = ui.getStringInput("\nEnter Product ID to add to cart (or 0 to go back): ");
            if (!choice.equals("0")) addToCart(choice, ui);
        }
    }

    // ── Add to Cart ──────────────────────────────────────────

    private void addToCart(String productId, ConsoleUI ui) {
        Product found = findProductById(productId);
        if (found == null) {
            ui.printError("Product ID '" + productId + "' not found.");
            return;
        }
        if (!found.isInStock()) {
            ui.printError("Sorry, '" + found.getName() + "' is out of stock.");
            return;
        }

        int qty = ui.getIntInput("Quantity: ");
        if (qty <= 0) {
            ui.printError("Quantity must be at least 1.");
            return;
        }
        if (qty > found.getStock()) {
            ui.printError("Only " + found.getStock() + " units available.");
            return;
        }

        // Check if already in cart — update quantity
        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + qty);
                ui.printSuccess("Updated cart: " + found.getName() + " × " + item.getQuantity());
                return;
            }
        }

        cart.add(new CartItem(found, qty));
        ui.printSuccess("Added to cart: " + found.getName() + " × " + qty
            + "  (₦" + String.format("%,.2f", found.getPrice() * qty) + ")");
    }

    // ── View Cart ────────────────────────────────────────────

    public void viewCart(ConsoleUI ui) {
        ui.printHeader("YOUR SHOPPING CART");
        if (cart.isEmpty()) {
            ui.print("  Your cart is empty. Browse products to add items.\n");
            return;
        }

        double subtotal = 0;
        ui.print(String.format("  %-26s %-6s  %-12s %s", "Item", "Qty", "Unit Price", "Line Total"));
        ui.printDivider();

        for (CartItem item : cart) {
            ui.print(String.format("  %-26s x%-5d ₦%,-10.2f ₦%,.2f",
                item.getProduct().getName(),
                item.getQuantity(),
                item.getProduct().getPrice(),
                item.getLineTotal()));
            subtotal += item.getLineTotal();
        }

        double vat   = subtotal * 0.075;
        double total = subtotal + vat;

        ui.printDivider();
        ui.print(String.format("  %-40s ₦%,.2f", "Subtotal:", subtotal));
        ui.print(String.format("  %-40s ₦%,.2f", "VAT (7.5%):", vat));
        ui.printDivider();
        ui.print(String.format("  %-40s ₦%,.2f", "TOTAL:", total));
        ui.print("");

        String action = ui.getStringInput("Options: [C] Checkout  [R] Remove item  [X] Clear cart  [0] Back: ").toUpperCase();
        switch (action) {
            case "C" -> checkout(ui);
            case "R" -> removeFromCart(ui);
            case "X" -> { cart.clear(); ui.printSuccess("Cart cleared."); }
        }
    }

    // ── Remove From Cart ─────────────────────────────────────

    private void removeFromCart(ConsoleUI ui) {
        String id = ui.getStringInput("Enter Product ID to remove: ");
        boolean removed = cart.removeIf(item -> item.getProduct().getId().equalsIgnoreCase(id));
        if (removed) ui.printSuccess("Item removed from cart.");
        else         ui.printError("Product ID '" + id + "' not found in cart.");
    }

    // ── Checkout ─────────────────────────────────────────────

    public void checkout(ConsoleUI ui) {
        if (cart.isEmpty()) {
            ui.printError("Your cart is empty. Add items before checking out.");
            return;
        }

        ui.printHeader("CHECKOUT");

        // Collect customer details
        String name  = "";
        String email = "";

        while (name.isBlank()) {
            name = ui.getStringInput("Full name: ");
            if (name.isBlank()) ui.printError("Name cannot be empty.");
        }

        Customer customer = null;
        while (customer == null) {
            email = ui.getStringInput("Email address: ");
            try {
                customer = new Customer(name, email);
            } catch (IllegalArgumentException e) {
                ui.printError(e.getMessage());
            }
        }

        String phone = ui.getStringInput("Phone number (optional — press Enter to skip): ");
        if (!phone.isBlank()) customer.setPhone(phone);

        // Confirm order
        double subtotal = cart.stream().mapToDouble(CartItem::getLineTotal).sum();
        double total    = subtotal * 1.075;
        ui.print(String.format("%n  Order total: ₦%,.2f  (incl. 7.5%% VAT)", total));
        String confirm = ui.getStringInput("Confirm order? (yes/no): ").toLowerCase();

        if (!confirm.equals("yes") && !confirm.equals("y")) {
            ui.print("  Order cancelled. Your cart has been kept.\n");
            return;
        }

        // Deduct stock and create order
        try {
            for (CartItem item : cart) {
                item.getProduct().reduceStock(item.getQuantity());
            }
        } catch (IllegalArgumentException e) {
            ui.printError("Stock error: " + e.getMessage());
            return;
        }

        String orderId = String.format("#ORD-%04d", orderCounter++);
        Order order = new Order(orderId, customer, cart);
        orders.add(order);

        // Save receipt to file
        String receiptPath = "receipts/" + orderId.replace("#", "") + ".txt";
        FileManager.writeFile(receiptPath, order.toReceipt());

        // Display receipt
        ui.print(order.toReceipt());
        ui.printSuccess("Receipt saved to " + receiptPath);

        // Save updated inventory
        saveInventory();

        cart.clear();
    }

    // ── Admin Panel ──────────────────────────────────────────

    public void adminPanel(ConsoleUI ui) {
        ui.printHeader("ADMIN PANEL");
        ui.print("  [1] Add new product");
        ui.print("  [2] Update stock quantity");
        ui.print("  [3] Remove product");
        ui.print("  [4] View all orders");
        ui.print("  [0] Back to main menu");
        ui.print("");

        int choice = ui.getIntInput("Admin choice: ");
        switch (choice) {
            case 1 -> adminAddProduct(ui);
            case 2 -> adminUpdateStock(ui);
            case 3 -> adminRemoveProduct(ui);
            case 4 -> adminViewOrders(ui);
        }
    }

    private void adminAddProduct(ConsoleUI ui) {
        ui.printHeader("ADD NEW PRODUCT");
        String id   = ui.getStringInput("Product ID (e.g. P007): ");
        if (findProductById(id) != null) {
            ui.printError("A product with ID '" + id + "' already exists.");
            return;
        }
        String name     = ui.getStringInput("Product name: ");
        double price    = ui.getDoubleInput("Price (₦): ");
        int    stock    = ui.getIntInput("Initial stock quantity: ");
        String category = ui.getStringInput("Category: ");

        catalog.add(new Product(id, name, price, stock, category));
        saveInventory();
        ui.printSuccess("Product '" + name + "' added successfully.");
    }

    private void adminUpdateStock(ConsoleUI ui) {
        String id = ui.getStringInput("Enter Product ID to update: ");
        Product p = findProductById(id);
        if (p == null) { ui.printError("Product not found."); return; }
        int newStock = ui.getIntInput("New stock quantity for '" + p.getName() + "': ");
        p.setStock(newStock);
        saveInventory();
        ui.printSuccess("Stock updated to " + newStock + " for " + p.getName());
    }

    private void adminRemoveProduct(ConsoleUI ui) {
        String id = ui.getStringInput("Enter Product ID to remove: ");
        boolean removed = catalog.removeIf(p -> p.getId().equalsIgnoreCase(id));
        if (removed) { saveInventory(); ui.printSuccess("Product removed."); }
        else           ui.printError("Product ID '" + id + "' not found.");
    }

    private void adminViewOrders(ConsoleUI ui) {
        ui.printHeader("ORDER HISTORY");
        if (orders.isEmpty()) {
            ui.print("  No orders placed in this session.\n");
            return;
        }
        for (Order o : orders) {
            ui.print(String.format("  %s | %s | Total: ₦%,.2f",
                o.getOrderId(), o.getTimestamp(), o.getTotal()));
        }
        ui.print("");
    }

    // ── Helpers ──────────────────────────────────────────────

    private Product findProductById(String id) {
        for (Product p : catalog) {
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }
}
