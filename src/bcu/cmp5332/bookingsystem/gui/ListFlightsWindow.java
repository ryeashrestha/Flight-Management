package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;

/**
 * {@code ListFlightsWindow} class represents a window that displays lists of flights
 * in the flight booking system. It utilizes {@link JTable} components within a {@link JTabbedPane}
 * to organize and present flight information, categorized into "All Flights", "Departed Flights",
 * and "Future Flights".
 * <p>
 * This window provides functionalities to view flight details, seat availability, and deleted flights.
 * It also includes filtering and sorting options to manage and analyze the displayed flight data.
 * The window is designed to offer a comprehensive overview of the flight inventory within the system.
 * </p>
 * <p>
 * It extends {@link JFrame} for window functionalities and implements {@link ActionListener} to handle
 * button click events and user interactions.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see MainWindow
 * @see FlightBookingSystem
 * @see FlightBookingSystemData
 * @see Flight
 * @see ShowFlightWindow
 * @see SeatAvailabilityWindow
 * @see ShowDeletedFlightsWindow
 */
public class ListFlightsWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final FlightBookingSystemData data;
    private JTable flightTable;
    private JTable departedFlightTable;
    private JTable futureFlightTable;
    private DefaultTableModel tableModel;
    private DefaultTableModel departedTableModel;
    private DefaultTableModel futureTableModel;
    private JButton showFlightButton;
    private JButton showSeatsButton;
    private JButton showDeletedButton;
    private JButton refreshButton;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JTextField airlineField;
    private JTextField destinationField;
    private JButton filterButton;
    private JButton sortButton;

    /**
     * Constructs a {@code ListFlightsWindow} object.
     *
     * @param fbs  The {@link FlightBookingSystem} instance to interact with.
     * @param data The {@link FlightBookingSystemData} instance for data access.
     */
    public ListFlightsWindow(FlightBookingSystem fbs, FlightBookingSystemData data) {
        this.fbs = fbs;
        this.data = data;
        initialize();
    }

    /**
     * Initializes the graphical components of the {@code ListFlightsWindow}.
     * Sets up the layout, table models, {@link JTable} instances for different flight categories,
     * {@link JTabbedPane} to organize tables, filter and sort panel, buttons, and loads initial flight data.
     * This method is called internally during the construction of the window.
     */
    private void initialize() {
        setTitle("List of Flights");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set Background Color
        getContentPane().setBackground(new Color(245, 245, 245));

        // Table Model Setup
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Flight Number");
        tableModel.addColumn("Destination");
        tableModel.addColumn("Origin");
        tableModel.addColumn("Departure Date");
        tableModel.addColumn("Capacity");
        tableModel.addColumn("Price");
        tableModel.addColumn("Duration");

        // Departed Table Model Setup
        departedTableModel = new DefaultTableModel();
        departedTableModel.addColumn("ID");
        departedTableModel.addColumn("Flight Number");
        departedTableModel.addColumn("Destination");
        departedTableModel.addColumn("Origin");
        departedTableModel.addColumn("Departure Date");
        departedTableModel.addColumn("Capacity");
        departedTableModel.addColumn("Price");
        departedTableModel.addColumn("Duration");

        // Future Table Model Setup
        futureTableModel = new DefaultTableModel();
        futureTableModel.addColumn("ID");
        futureTableModel.addColumn("Flight Number");
        futureTableModel.addColumn("Destination");
        futureTableModel.addColumn("Origin");
        futureTableModel.addColumn("Departure Date");
        futureTableModel.addColumn("Capacity");
        futureTableModel.addColumn("Price");
        futureTableModel.addColumn("Duration");

        // Initialize Tables
        flightTable = new JTable(tableModel);
        departedFlightTable = new JTable(departedTableModel);
        futureFlightTable = new JTable(futureTableModel);

        // Customizing Table Appearance
        flightTable.setFont(new Font("Arial", Font.PLAIN, 14));
        departedFlightTable.setFont(new Font("Arial", Font.PLAIN, 14));
        futureFlightTable.setFont(new Font("Arial", Font.PLAIN, 14));
        setTableProperties(flightTable);
        setTableProperties(departedFlightTable);
        setTableProperties(futureFlightTable);

        // Tabbed Pane for organizing tables
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("All Flights", new JScrollPane(flightTable));
        tabbedPane.addTab("Departed Flights", new JScrollPane(departedFlightTable));
        tabbedPane.addTab("Future Flights", new JScrollPane(futureFlightTable));

        // Filter and Sort Panel
        JPanel filterPanel = new JPanel(new FlowLayout());
        minPriceField = new JTextField(5);
        maxPriceField = new JTextField(5);
        airlineField = new JTextField(5);
        destinationField = new JTextField(5);
        filterButton = new JButton("Filter");
        sortButton = new JButton("Sort by Price");

        filterButton.addActionListener(this);
        sortButton.addActionListener(this);

        filterPanel.add(new JLabel("Min Price:"));
        filterPanel.add(minPriceField);
        filterPanel.add(new JLabel("Max Price:"));
        filterPanel.add(maxPriceField);
        filterPanel.add(new JLabel("Airline:"));
        filterPanel.add(airlineField);
        filterPanel.add(new JLabel("Destination:"));
        filterPanel.add(destinationField);
        filterPanel.add(filterButton);
        filterPanel.add(sortButton);

        // Button Panel with customized buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        showFlightButton = new JButton("Show Flight Details");
        showSeatsButton = new JButton("Show Seat Availability");
        showDeletedButton = new JButton("Show Cancelled Flights");
        refreshButton = new JButton("Refresh");

        customizeButton(showFlightButton, new Color(0, 123, 255), Color.WHITE);
        customizeButton(showSeatsButton, new Color(28, 200, 138), Color.WHITE);
        customizeButton(showDeletedButton, new Color(220, 53, 69), Color.WHITE);
        customizeButton(refreshButton, new Color(255, 159, 67), Color.WHITE);

        showFlightButton.addActionListener(this);
        showSeatsButton.addActionListener(this);
        showDeletedButton.addActionListener(this);
        refreshButton.addActionListener(this);

        buttonPanel.add(showFlightButton);
        buttonPanel.add(showSeatsButton);
        buttonPanel.add(showDeletedButton);
        buttonPanel.add(refreshButton);

        // Layout configuration
        add(filterPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load Flight Data
        loadFlights(data.fbs.getActiveFlights());
        loadDepartedFlights();
        loadFutureFlights();
    }

    /**
     * Helper method to customize the appearance of a {@link JButton}.
     * Sets background color, text color, font, removes focus border, and adds padding.
     *
     * @param button          The {@link JButton} to customize.
     * @param backgroundColor The background {@link Color} for the button.
     * @param textColor       The text {@link Color} for the button.
     */
    private void customizeButton(JButton button, Color backgroundColor, Color textColor) {
        button.setBackground(backgroundColor); // Set background color
        button.setForeground(textColor); // Set text color
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding inside the button
    }

    /**
     * Helper method to set common properties for {@link JTable} instances.
     * Sets row height, selection background and foreground colors, header background and foreground colors, and header font.
     *
     * @param table The {@link JTable} to apply properties to.
     */
    private void setTableProperties(JTable table) {
        table.setRowHeight(30); // Adjust row height for better readability
        table.setSelectionBackground(new Color(173, 216, 230)); // Light blue for selected rows
        table.setSelectionForeground(Color.BLACK); // Black text for selected rows
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(70, 130, 180)); // SteelBlue header background
        tableHeader.setForeground(Color.WHITE); // White header text
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16)); // Bold header font
    }

    /**
     * Loads active flight data into the "All Flights" {@link JTable}.
     * Clears existing table data and populates the table with information from the provided list of flights.
     *
     * @param flights A list of {@link Flight} objects to be displayed in the table.
     */
    private void loadFlights(List<Flight> flights) {
        tableModel.setRowCount(0);

        for (Flight flight : flights) {
            Object[] rowData = {flight.getId(), flight.getFlightNumber(), flight.getDestination(), flight.getOrigin(),
                    flight.getDepartureDate(), flight.getCapacity(), flight.getPrice(), flight.getDuration()};
            tableModel.addRow(rowData);
        }
    }

    /**
     * Loads departed flight data into the "Departed Flights" {@link JTable}.
     * Retrieves departed flights from the {@link FlightBookingSystem} and populates the table.
     */
    private void loadDepartedFlights() {
        departedTableModel.setRowCount(0);

        List<Flight> flights = data.fbs.getDepartedFlights();
        for (Flight flight : flights) {
            Object[] rowData = {flight.getId(), flight.getFlightNumber(), flight.getDestination(), flight.getOrigin(),
                    flight.getDepartureDate(), flight.getCapacity(), flight.getPrice(), flight.getDuration()};
            departedTableModel.addRow(rowData);
        }
    }

    /**
     * Loads future flight data into the "Future Flights" {@link JTable}.
     * Retrieves future flights from the {@link FlightBookingSystem} and populates the table.
     */
    private void loadFutureFlights() {
        futureTableModel.setRowCount(0);

        List<Flight> flights = data.fbs.getFutureFlights();
        for (Flight flight : flights) {
            Object[] rowData = {flight.getId(), flight.getFlightNumber(), flight.getDestination(), flight.getOrigin(),
                    flight.getDepartureDate(), flight.getCapacity(), flight.getPrice(), flight.getDuration()};
            futureTableModel.addRow(rowData);
        }
    }

    /**
     * Handles action events triggered by button clicks in the {@code ListFlightsWindow}.
     * <p>
     * If the "Show Flight Details" button is clicked, it opens a {@link ShowFlightWindow} for the selected flight.
     * If the "Show Seat Availability" button is clicked, it opens a {@link SeatAvailabilityWindow} for the selected flight.
     * If the "Show Cancelled Flights" button is clicked, it opens a {@link ShowDeletedFlightsWindow}.
     * If the "Refresh" button is clicked, it reloads and updates all flight tables.
     * If the "Filter" button is clicked, it applies filters based on price range, airline, and destination (functionality not fully implemented in the provided code).
     * If the "Sort by Price" button is clicked, it sorts the flights by price and reloads the "All Flights" table.
     * </p>
     *
     * @param e The {@link ActionEvent} representing the action that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showFlightButton) {
            int selectedRow = flightTable.getSelectedRow();
            if (selectedRow >= 0) {
                int flightId = (int) tableModel.getValueAt(selectedRow, 0);
                Flight flight = fbs.getFlightById(flightId);
                if (flight != null) {
                    new ShowFlightWindow(fbs, flight).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Flight not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a flight.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == showSeatsButton) {
            int selectedRow = flightTable.getSelectedRow();
            if (selectedRow >= 0) {
                int flightId = (int) tableModel.getValueAt(selectedRow, 0);
                Flight flight = fbs.getFlightById(flightId);
                if (flight != null) {
                    new SeatAvailabilityWindow(fbs, flight).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Flight not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a flight.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == showDeletedButton) {
            new ShowDeletedFlightsWindow(fbs, data).setVisible(true);
        } else if (e.getSource() == refreshButton) {
            loadFlights(fbs.getActiveFlights());
            loadDepartedFlights();
            loadFutureFlights();
        } else if (e.getSource() == filterButton) {
            // Handle filtering logic
        } else if (e.getSource() == sortButton) {
            List<Flight> sortedFlights = fbs.sortFlightsByPrice();
            loadFlights(sortedFlights);
        }
    }
}