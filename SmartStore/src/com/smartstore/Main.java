package com.smartstore;

import com.smartstore.services.StoreService;
import com.smartstore.utils.ConsoleUI;

/**
 * SmartStore — Java Capstone Project
 * ICT Training Academy | Week 4 Capstone
 *
 * Entry point for the SmartStore console application.
 * Run this class to launch the store.
 */
public class Main {

    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        StoreService store = new StoreService();

        store.loadInventory();          // Load products from file on startup
        ui.showWelcomeBanner();

        boolean running = true;
        while (running) {
            ui.showMainMenu();
            int choice = ui.getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> store.browseProducts(ui);
                case 2 -> store.searchProduct(ui);
                case 3 -> store.viewCart(ui);
                case 4 -> store.checkout(ui);
                case 5 -> store.adminPanel(ui);
                case 0 -> {
                    store.saveInventory();
                    ui.print("\nThank you for shopping at SmartStore. Goodbye!\n");
                    running = false;
                }
                default -> ui.printError("Invalid choice. Please enter a number from the menu.");
            }
        }
    }
}
