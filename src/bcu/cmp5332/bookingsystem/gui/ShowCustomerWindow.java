package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Flight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * {@code ShowCustomerWindow} class represents a window that displays detailed information
 * about a specific {@link Customer} and their bookings within the flight booking system.
 * It allows users to view customer details, a list of their bookings, and provides functionality
 * to cancel a booking directly from this window.
 * <p>
 * This window is part of the graphical user interface for customer management and booking operations.
 * </p>
 * <p>
 * It extends {@link JFrame} for window functionalities and implements {@link ActionListener} to handle
 * button click events, specifically for booking cancellation.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see MainWindow
 * @see FlightBookingSystem
 * @see Customer
 * @see Booking
 */
public class ShowCustomerWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final Customer customer;
    private JTextArea detailsArea;
    private JButton cancelButton;
    private FlightBookingSystemData data;

    /**
     * Constructs a {@code ShowCustomerWindow} object for a given customer.
     *
     * @param fbs      The {@link FlightBookingSystem} instance to interact with.
     * @param customer The {@link Customer} object whose details are to be displayed.
     */
    public ShowCustomerWindow(FlightBookingSystem fbs, Customer customer) {
        this.fbs = fbs;
        this.customer = customer;
        initialize();
    }

    /**
     * Initializes the graphical components of the {@code ShowCustomerWindow}.
     * Sets up the layout, {@link JTextArea} for displaying customer details and bookings,
     * {@link JButton} for cancelling bookings, and loads the initial customer details.
     * This method is called internally during the construction of the window.
     */
    private void initialize() {
        setTitle("Customer Details");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the background color of the frame
        getContentPane().setBackground(new Color(245, 245, 245));  // Light gray background

        // Layout setup
        setLayout(new BorderLayout(10, 10));

        // Create a text area for displaying customer details
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsArea.setBackground(new Color(255, 255, 255));  // White background for text area
        detailsArea.setForeground(new Color(0, 0, 0));  // Black text for readability
        detailsArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));  // Light border

        JScrollPane scrollPane = new JScrollPane(detailsArea);

        // Button to cancel booking
        cancelButton = new JButton("Cancel Booking");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(255, 69, 58)); // Red background for cancel
        cancelButton.setForeground(Color.WHITE);  // White text
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(160, 40));  // Make button larger

        cancelButton.addActionListener(this);

        // Panel for the cancel button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));  // Same background color as window
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(cancelButton);

        // Adding components to the window
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        displayCustomerDetails();  // Display the details when the window opens
    }

    /**
     * Populates the {@link JTextArea} with detailed information about the customer and their bookings.
     * Retrieves customer attributes such as ID, name, phone, email, and iterates through their bookings
     * to display booking details including booking ID, flight number, price, and cancellation status.
     * If no bookings are found, it displays a message indicating no bookings for the customer.
     */
    private void displayCustomerDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(customer.getId()).append("\n");
        sb.append("Name: ").append(customer.getName()).append("\n");
        sb.append("Phone: ").append(customer.getPhone()).append("\n");
        sb.append("Email: ").append(customer.getEmail()).append("\n\n");
        sb.append("Bookings:\n");

        List<Booking> bookings = fbs.getBookings();
        if (bookings.isEmpty()) {
            sb.append("No bookings found for this customer.\n");
        } else {
            for (Booking booking : bookings) {
                if (booking.getCustomer().getId() == customer.getId()) {
                    Flight flight = booking.getFlight();
                    sb.append("  - Booking ID: ").append(booking.getId())
                            .append(", Flight: ").append(flight.getFlightNumber())
                            .append(", Price: $").append(flight.getPrice())
                            .append(", Cancelled: ").append(booking.isCancelled() ? "Yes" : "No")
                            .append("\n");
                }
            }
        }

        detailsArea.setText(sb.toString());
    }

    /**
     * Handles action events triggered by button clicks in the {@code ShowCustomerWindow}.
     * <p>
     * If the "Cancel Booking" button is clicked, it prompts the user to enter a booking ID to cancel.
     * Upon receiving a valid booking ID, it retrieves the booking, calculates refund and compensation,
     * cancels the booking using {@link FlightBookingSystem#cancelBooking(int)}, persists the data,
     * displays a success message with refund details, and refreshes the displayed customer details
     * to reflect the cancellation.
     * </p>
     * <p>
     * Error handling is in place for invalid booking ID formats, booking not found, and potential
     * {@link IOException} during data storage, displaying appropriate error or informational messages
     * via {@link JOptionPane}.
     * </p>
     *
     * @param e The {@link ActionEvent} representing the action that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            String bookingIdStr = JOptionPane.showInputDialog(this, "Enter Booking ID to cancel:");
            if (bookingIdStr != null && !bookingIdStr.isEmpty()) {
                try {
                    int bookingId = Integer.parseInt(bookingIdStr);
                    Booking booking = fbs.getBookingById(bookingId);

                    if (booking != null) {
                        Flight flight = booking.getFlight();
                        double flightPrice = flight.getPrice();
                        double refundAmount = flightPrice * 0.9;
                        fbs.cancelBooking(bookingId);

                        // Store updated data
                        FlightBookingSystemData data = new FlightBookingSystemData(fbs);
                        data.store(fbs);

                        String message = String.format("Booking cancelled successfully.\nRefund Amount: $%.2f\nCompensation Fee (10%%): $%.2f", refundAmount, flightPrice * 0.1);
                        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);

                        displayCustomerDetails();  // Refresh the displayed details
                    } else {
                        JOptionPane.showMessageDialog(this, "Booking not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Booking ID format.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}