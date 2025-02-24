package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * {@code ShowDeletedFlightsWindow} class represents a window that displays a list of deleted flights
 * from the flight booking system. It uses a {@link JTable} to present the information of deleted flights
 * and provides functionality to restore a deleted flight back to active status.
 * <p>
 * This window is part of the graphical user interface for managing flights, specifically for viewing
 * and restoring flights that have been marked as deleted (soft deleted).
 * </p>
 * <p>
 * It extends {@link JFrame} for window functionalities and implements {@link ActionListener} to handle
 * button click events, particularly for the "Restore Flight" action.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see MainWindow
 * @see FlightBookingSystem
 * @see FlightBookingSystemData
 * @see Flight
 */
public class ShowDeletedFlightsWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final FlightBookingSystemData data;
    private JTable deletedFlightTable;
    private DefaultTableModel tableModel;
    private JButton restoreFlightButton;

    /**
     * Constructs a {@code ShowDeletedFlightsWindow} object.
     *
     * @param fbs  The {@link FlightBookingSystem} instance to interact with.
     * @param data The {@link FlightBookingSystemData} instance for data access and persistence.
     */
    public ShowDeletedFlightsWindow(FlightBookingSystem fbs, FlightBookingSystemData data) {
        this.fbs = fbs;
        this.data = data;
        initialize();
    }

    /**
     * Initializes the graphical components of the {@code ShowDeletedFlightsWindow}.
     * Sets up the layout, table model, {@link JTable} for displaying deleted flights,
     * {@link JButton} for restoring flights, and loads the initial list of deleted flights into the table.
     * This method is called internally during the construction of the window.
     */
    private void initialize() {
        setTitle("List of Deleted Flights");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the background color of the frame
        getContentPane().setBackground(new Color(245, 245, 245));  // Light gray background

        // Layout setup
        setLayout(new BorderLayout(10, 10));

        // Table Model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Flight Number");
        tableModel.addColumn("Destination");
        tableModel.addColumn("Origin");
        tableModel.addColumn("Departure Date");
        tableModel.addColumn("Capacity");
        tableModel.addColumn("Price");
        tableModel.addColumn("Duration");

        deletedFlightTable = new JTable(tableModel);
        deletedFlightTable.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for readability
        deletedFlightTable.setRowHeight(25);
        deletedFlightTable.setSelectionBackground(new Color(0, 123, 255)); // Blue background on selection

        // Add alternating row colors for better readability
        deletedFlightTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    cell.setBackground(new Color(240, 240, 240)); // Light gray for even rows
                } else {
                    cell.setBackground(Color.WHITE); // White for odd rows
                }
                return cell;
            }
        });

        JScrollPane scrollPane = new JScrollPane(deletedFlightTable);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        restoreFlightButton = new JButton("Restore Flight");
        restoreFlightButton.setFont(new Font("Arial", Font.BOLD, 14));
        restoreFlightButton.setBackground(new Color(0, 123, 255)); // Blue background for restore button
        restoreFlightButton.setForeground(Color.WHITE);  // White text
        restoreFlightButton.setFocusPainted(false);
        restoreFlightButton.setPreferredSize(new Dimension(160, 40));  // Make button larger

        restoreFlightButton.addActionListener(this);
        buttonPanel.add(restoreFlightButton);

        loadFlights();

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Loads deleted flight data into the {@link JTable}.
     * Clears existing table data and populates the table with information from the list of deleted flights
     * obtained from the {@link FlightBookingSystem}. If no deleted flights are found, it displays an information message
     * to the user.
     */
    private void loadFlights() {
        tableModel.setRowCount(0);

        List<Flight> deletedFlights = fbs.getFlights().stream().filter(Flight::isDeleted).toList();

        if (deletedFlights.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No deleted flights found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

        for (Flight flight : deletedFlights) {
            Object[] rowData = {flight.getId(), flight.getFlightNumber(), flight.getDestination(), flight.getOrigin(),
                    flight.getDepartureDate(), flight.getCapacity(), flight.getPrice(), flight.getDuration()};
            tableModel.addRow(rowData);
        }
    }

    /**
     * Handles action events triggered by button clicks in the {@code ShowDeletedFlightsWindow}.
     * <p>
     * If the "Restore Flight" button is clicked, it retrieves the selected flight from the table,
     * prompts the user for confirmation to restore the flight, and if confirmed, marks the flight as not deleted,
     * persists the changes using {@link FlightBookingSystemData#store(FlightBookingSystem)}, reloads the list of deleted flights,
     * and displays a success message. If no flight is selected or flight is not found, it displays appropriate
     * informational or error messages via {@link JOptionPane}.
     * </p>
     *
     * @param e The {@link ActionEvent} representing the action that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restoreFlightButton) {
            int selectedRow = deletedFlightTable.getSelectedRow();
            if (selectedRow >= 0) {
                int flightId = (int) tableModel.getValueAt(selectedRow, 0);
                Flight flight = fbs.getFlightById(flightId);
                if (flight != null) {
                    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to restore this flight?", "Restore Flight", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (confirm == JOptionPane.YES_OPTION) {
                        flight.setDeleted(false);
                        try {
                            data.store(fbs); // Save Flight to file
                            loadFlights(); // Reload new state of the list
                            JOptionPane.showMessageDialog(this, "Flight restored successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(this, "Error saving data.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Flight not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a flight to restore.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}