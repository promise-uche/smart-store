package com.smartstore.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a completed customer order.
 * Created at checkout and written to a receipt file.
 */
public class Order {

    private static final double VAT_RATE = 0.075;   // 7.5% VAT
    private static final DateTimeFormatter FMT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String           orderId;
    private Customer         customer;
    private List<CartItem>   items;
    private LocalDateTime    timestamp;
    private double           subtotal;
    private double           vat;
    private double           total;

    public Order(String orderId, Customer customer, List<CartItem> items) {
        this.orderId   = orderId;
        this.customer  = customer;
        this.items     = new ArrayList<>(items);
        this.timestamp = LocalDateTime.now();
        this.subtotal  = items.stream().mapToDouble(CartItem::getLineTotal).sum();
        this.vat       = subtotal * VAT_RATE;
        this.total     = subtotal + vat;
    }

    // ── Getters ──────────────────────────────────────────────
    public String        getOrderId()   { return orderId; }
    public Customer      getCustomer()  { return customer; }
    public List<CartItem> getItems()    { return items; }
    public double        getSubtotal()  { return subtotal; }
    public double        getVat()       { return vat; }
    public double        getTotal()     { return total; }
    public String        getTimestamp() { return timestamp.format(FMT); }

    // ── Receipt text ─────────────────────────────────────────
    /**
     * Produces a formatted receipt string for console display and file output.
     */
    public String toReceipt() {
        StringBuilder sb = new StringBuilder();
        String divider = "═".repeat(50);

        sb.append("\n").append(divider).append("\n");
        sb.append("         SMARTSTORE — ORDER RECEIPT\n");
        sb.append(divider).append("\n");
        sb.append(String.format("Order ID  : %s%n", orderId));
        sb.append(String.format("Customer  : %s%n", customer.getName()));
        sb.append(String.format("Email     : %s%n", customer.getEmail()));
        sb.append(String.format("Date/Time : %s%n", getTimestamp()));
        sb.append(divider).append("\n");
        sb.append(String.format("%-26s %6s   %12s%n", "Item", "Qty", "Total"));
        sb.append("─".repeat(50)).append("\n");

        for (CartItem item : items) {
            sb.append(String.format("%-26s x%-5d ₦%,10.2f%n",
                item.getProduct().getName(),
                item.getQuantity(),
                item.getLineTotal()));
        }

        sb.append("─".repeat(50)).append("\n");
        sb.append(String.format("%-33s ₦%,10.2f%n", "Subtotal:", subtotal));
        sb.append(String.format("%-33s ₦%,10.2f%n", "VAT (7.5%):", vat));
        sb.append(divider).append("\n");
        sb.append(String.format("%-33s ₦%,10.2f%n", "TOTAL PAID:", total));
        sb.append(divider).append("\n");
        sb.append("     Thank you for shopping at SmartStore!\n");
        sb.append(divider).append("\n");

        return sb.toString();
    }
}
