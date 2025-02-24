package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * {@code SeatAvailabilityWindow} class represents a window that displays the seat availability
 * for a specific {@link Flight} in the flight booking system.
 * It shows details such as flight number, destination, departure date, capacity,
 * available seats, and the number of bookings made for the flight.
 * <p>
 * This window provides a read-only view of the current seat status of a flight and includes
 * a refresh button to update the displayed information to the most current state.
 * </p>
 * <p>
 * It extends {@link JFrame} for window functionalities and uses {@link JTextArea} to display
 * the seat availability information.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see MainWindow
 * @see FlightBookingSystem
 * @see Flight
 * @see Booking
 */
public class SeatAvailabilityWindow extends JFrame {

    private final Flight flight;
    private final FlightBookingSystem fbs;
    private JTextArea seatsArea;
    private JButton refreshButton;

    /**
     * Constructs a {@code SeatAvailabilityWindow} object for a given flight.
     *
     * @param fbs    The {@link FlightBookingSystem} instance to interact with.
     * @param flight The {@link Flight} for which to display seat availability.
     */
    public SeatAvailabilityWindow(FlightBookingSystem fbs, Flight flight) {
        this.flight = flight;
        this.fbs = fbs;
        initialize();
    }

    /**
     * Initializes the graphical components of the {@code SeatAvailabilityWindow}.
     * Sets up the layout, {@link JTextArea} for displaying seat information,
     * {@link JButton} for refreshing the display, and loads the initial seat availability data.
     * This method is called internally during the construction of the window.
     */
    private void initialize() {
        setTitle("Seat Availability");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the background color of the frame
        getContentPane().setBackground(new Color(245, 245, 245));  // Light gray background

        // Layout setup
        setLayout(new BorderLayout(10, 10));

        // Create text area for seat availability
        seatsArea = new JTextArea();
        seatsArea.setEditable(false);
        seatsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        seatsArea.setBackground(new Color(255, 255, 255));  // White background for text area
        seatsArea.setForeground(new Color(0, 0, 0));  // Black text for readability
        seatsArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));  // Light border for the text area

        JScrollPane scrollPane = new JScrollPane(seatsArea);

        // Refresh button with customized colors
        refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(new Color(51, 153, 255)); // Blue background
        refreshButton.setForeground(Color.WHITE);  // White text
        refreshButton.setFocusPainted(false);  // Remove focus border when clicked
        refreshButton.setPreferredSize(new Dimension(120, 40)); // Make button larger for better click area

        // Add action listener to the refresh button
        refreshButton.addActionListener(e -> displaySeats());

        // Panel for the refresh button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245)); // Matching background color
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(refreshButton);

        // Adding components
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Display seat availability
        displaySeats();
    }

    /**
     * Updates the {@link JTextArea} with the current seat availability information for the flight.
     * Retrieves flight details such as capacity and bookings, calculates available seats,
     * and formats the information into a string for display in the {@code seatsArea}.
     */
    private void displaySeats() {
        if (flight == null) {
            seatsArea.setText("No flight selected.");
            return;
        }

        int capacity = flight.getCapacity();
        List<Booking> bookings = flight.getBookings();
        int availableSeats = capacity - bookings.size();
        StringBuilder sb = new StringBuilder();

        sb.append("Flight: ").append(flight.getFlightNumber()).append("\n");
        sb.append("Destination: ").append(flight.getDestination()).append("\n");
        sb.append("Departure: ").append(flight.getDepartureDate()).append("\n");
        sb.append("Capacity: ").append(capacity).append("\n");
        sb.append("Available Seats: ").append(availableSeats).append("\n");
        sb.append("Booked: ").append(bookings.size()).append("\n");

        seatsArea.setText(sb.toString());
    }
}