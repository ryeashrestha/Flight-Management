package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.io.IOException;

/**
 * {@code DeleteCustomerAction} class provides a static method to handle the deletion of a customer
 * from the flight booking system through a graphical user interface.
 * <p>
 * This class encapsulates the action of deleting a customer, which involves prompting the user for
 * a customer ID, retrieving the customer, marking them as deleted, and persisting the changes.
 * </p>
 * <p>
 * Note that the deletion implemented here is a soft deletion, meaning the customer is not permanently
 * removed from the data store but is marked as deleted.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see FlightBookingSystem
 * @see FlightBookingSystemData
 * @see Customer
 */
public class DeleteCustomerAction {

    /**
     * Initiates the process of deleting a customer from the flight booking system.
     * <p>
     * This method displays an input dialog to the user to enter the ID of the customer to be deleted.
     * Upon receiving a valid customer ID, it retrieves the corresponding {@link Customer} object from
     * the {@link FlightBookingSystem}. If the customer is found, it marks the customer as deleted
     * using {@link Customer#setDeleted(boolean)} and persists the changes using {@link FlightBookingSystemData#store(FlightBookingSystem)}.
     * Success or error messages are displayed to the user via {@link JOptionPane}.
     * </p>
     * <p>
     * Error handling is in place for invalid customer ID formats and potential {@link IOException}
     * during data storage.
     * </p>
     *
     * @param fbs    The {@link FlightBookingSystem} instance to operate on.
     * @param data   The {@link FlightBookingSystemData} instance for data persistence.
     * @param parent The parent {@link JFrame} for displaying dialogs, used for context and positioning.
     */
    public static void deleteCustomer(FlightBookingSystem fbs, FlightBookingSystemData data, JFrame parent) {
        String customerIdStr = JOptionPane.showInputDialog(parent, "Enter Customer ID to delete:");
        if (customerIdStr != null && !customerIdStr.isEmpty()) {
            try {
                int customerId = Integer.parseInt(customerIdStr);
                Customer customerToDelete = fbs.getCustomerById(customerId);

                if (customerToDelete != null) {
                    customerToDelete.setDeleted(true);  // Set deleted to true.
                    data.store(fbs);

                    JOptionPane.showMessageDialog(parent, "Customer deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(parent, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, "Invalid Customer ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}