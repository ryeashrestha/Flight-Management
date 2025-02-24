package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * {@code ShowFlightWindow} class represents a window that displays detailed information
 * about a specific {@link Flight} in the flight booking system.
 * It presents comprehensive details including flight number, destination, origin, departure date,
 * capacity, price, duration, available seats, and a list of passengers booked on the flight.
 * <p>
 * This window is designed to provide a user-friendly interface for viewing all relevant information
 * about a selected flight, including passenger details, within the graphical user interface of the system.
 * </p>
 * <p>
 * It extends {@link JFrame} for window functionalities and utilizes {@link JTextArea} to display
 * the flight details in a readable format.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see MainWindow
 * @see FlightBookingSystem
 * @see Flight
 * @see Booking
 * @see Customer
 */
public class ShowFlightWindow extends JFrame {

    private final FlightBookingSystem fbs;
    private final Flight flight;
    private JTextArea detailsArea;

    /**
     * Constructs a {@code ShowFlightWindow} object for a given flight.
     *
     * @param fbs    The {@link FlightBookingSystem} instance to interact with.
     * @param flight The {@link Flight} object for which to display details.
     */
    public ShowFlightWindow(FlightBookingSystem fbs, Flight flight) {
        this.fbs = fbs;
        this.flight = flight;
        initialize();
    }

    /**
     * Initializes the graphical components of the {@code ShowFlightWindow}.
     * Sets up the layout, {@link JTextArea} for displaying flight details, and loads the initial flight information.
     * This method is called internally during the construction of the window.
     */
    private void initialize() {
        // Frame settings
        setTitle("Flight Details");
        setSize(600, 600); // Increased size for better readability
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for adding color and structured layout
        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250)); // Light background color
        panel.setLayout(new BorderLayout());

        // Details Area with Scroll
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setBackground(new Color(240, 240, 240)); // Light grey background
        detailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Clean font
        detailsArea.setForeground(new Color(0, 0, 0)); // Text color black
        JScrollPane scrollPane = new JScrollPane(detailsArea);

        // Add the scroll pane to the panel
        panel.add(scrollPane, BorderLayout.CENTER);

        // Adding the panel to the frame
        add(panel);

        // Set default color scheme for buttons, etc.
        UIManager.put("Button.background", new Color(77, 144, 255)); // Light blue buttons
        UIManager.put("Button.foreground", Color.WHITE); // Button text color
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12)); // Button font

        displayFlightDetails();
    }

    /**
     * Populates the {@link JTextArea} with detailed information about the flight, including
     * flight attributes, available seats, and a list of passengers booked on this flight.
     * If there are bookings, it lists each booking with customer details, seat class, meal preference, and number of bags.
     * If no bookings exist, it indicates that no passengers are booked on the flight.
     */
    private void displayFlightDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Flight ID: ").append(flight.getId()).append("\n");
        sb.append("Flight Number: ").append(flight.getFlightNumber()).append("\n");
        sb.append("Destination: ").append(flight.getDestination()).append("\n");
        sb.append("Origin: ").append(flight.getOrigin()).append("\n");
        sb.append("Departure Date: ").append(flight.getDepartureDate()).append("\n");
        sb.append("Capacity: ").append(flight.getCapacity()).append("\n");
        sb.append("Price: $").append(flight.getBasePrice()).append("\n");
        sb.append("Duration: ").append(flight.getDuration()).append(" hours\n");
        sb.append("Available Seats: ").append(flight.getAvailableSeats()).append("\n\n");

        sb.append("Passengers:\n");
        List<Booking> bookings = flight.getBookings();
        if (bookings.isEmpty()) {
            sb.append("No passengers booked on this flight.\n");
        } else {
            for (Booking booking : bookings) {
                Customer customer = booking.getCustomer();
                sb.append("  - Booking ID: ").append(booking.getId()).append("\n");
                sb.append("    Customer Name: ").append(customer.getName()).append("\n");
                sb.append("    Customer ID: ").append(customer.getId()).append("\n");
                sb.append("    Phone: ").append(customer.getPhone()).append("\n");
                sb.append("    Email: ").append(customer.getEmail()).append("\n");
                sb.append("    Seat Class: ").append(booking.getSeatClass()).append("\n");
                sb.append("    Meal Preference: ").append(booking.getMealPreference()).append("\n");
                sb.append("    Number of Bags: ").append(booking.getNumberOfBags()).append("\n\n"); // All other details
            }
        }

        detailsArea.setText(sb.toString());
    }
}