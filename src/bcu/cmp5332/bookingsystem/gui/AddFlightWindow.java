package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * {@code AddFlightWindow} class represents a window for adding a new flight to the booking system.
 * It allows users to input flight details such as flight number, destination, origin, departure date,
 * capacity, price, duration, and meal costs. Upon submission, it uses the {@link AddFlight} command
 * to add the flight to the system and updates the data store.
 * <p>
 * This window is part of the graphical user interface for the flight booking system and interacts
 * with the underlying business logic and data management components.
 * </p>
 * <p>
 * It extends {@link JFrame} for window functionalities and implements {@link ActionListener} to handle
 * button click events.
 * </p>
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see MainWindow
 * @see FlightBookingSystem
 * @see FlightBookingSystemData
 */
public class AddFlightWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final FlightBookingSystemData data;
    private final MainWindow mainWindow;

    private JTextField flightNumberField = new JTextField(20);
    private JTextField destinationField = new JTextField(20);
    private JTextField originField = new JTextField(20);
    private JTextField departureDateField = new JTextField(20);
    private JTextField capacityField = new JTextField(20);
    private JTextField priceField = new JTextField(20);
    private JTextField durationField = new JTextField(20);
    private JTextField vegMealCostField = new JTextField(20);
    private JTextField nonVegMealCostField = new JTextField(20);
    private JButton addButton = new JButton("Add Flight");
    private JButton cancelButton = new JButton("Cancel");

    /**
     * Constructs an {@code AddFlightWindow} object.
     *
     * @param fbs        The {@link FlightBookingSystem} instance to interact with.
     * @param data       The {@link FlightBookingSystemData} instance for data persistence.
     * @param mainWindow The main application window, used for potential updates or interactions.
     */
    public AddFlightWindow(FlightBookingSystem fbs, FlightBookingSystemData data, MainWindow mainWindow) {
        this.fbs = fbs;
        this.data = data;
        this.mainWindow = mainWindow;
        initialize();
    }

    /**
     * Initializes the graphical components of the {@code AddFlightWindow}.
     * Sets up the layout, labels, text fields, and buttons for user interaction.
     * This method is called internally during the construction of the window.
     */
    private void initialize() {
        setTitle("Add New Flight");
        setSize(450, 600); // Adjusted size for a more spacious layout
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for better control over component alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Add labels and fields with GridBagLayout constraints
        addLabelAndField(panel, "Flight Number:", flightNumberField, gbc, 0);
        addLabelAndField(panel, "Destination:", destinationField, gbc, 1);
        addLabelAndField(panel, "Origin:", originField, gbc, 2);
        addLabelAndField(panel, "Departure Date:", departureDateField, gbc, 3);
        addLabelAndField(panel, "Capacity:", capacityField, gbc, 4);
        addLabelAndField(panel, "Price:", priceField, gbc, 5);
        addLabelAndField(panel, "Duration:", durationField, gbc, 6);
        addLabelAndField(panel, "Veg Meal Cost:", vegMealCostField, gbc, 7);
        addLabelAndField(panel, "Non-Veg Meal Cost:", nonVegMealCostField, gbc, 8);

        // Add buttons with customized style
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2; // Buttons span two columns
        gbc.anchor = GridBagConstraints.CENTER;
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(150, 40));
        panel.add(addButton, gbc);

        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(150, 40));
        gbc.gridy = 10;
        panel.add(cancelButton, gbc);

        addButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // Add panel to frame
        add(new JScrollPane(panel)); // ScrollPane for better handling of larger forms
    }

    /**
     * Helper method to add a labeled text field to the given panel.
     *
     * @param panel     The {@link JPanel} to which the label and text field are added.
     * @param labelText The text for the label.
     * @param textField The {@link JTextField} to be added.
     * @param gbc       The {@link GridBagConstraints} object for layout management.
     * @param row       The row index in the grid layout.
     */
    private void addLabelAndField(JPanel panel, String labelText, JTextField textField, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        textField.setToolTipText("Enter " + labelText.toLowerCase());
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(textField, gbc);
    }

    /**
     * Handles action events triggered by button clicks in the {@code AddFlightWindow}.
     * <p>
     * If the "Add Flight" button is clicked, it retrieves the flight details from the text fields,
     * validates the input, creates an {@link AddFlight} command, executes it, and updates the data store.
     * If successful, it displays a success message and closes the window. If there are errors in input or
     * during execution, it displays appropriate error messages.
     * </p>
     * <p>
     * If the "Cancel" button is clicked, it simply disposes of the window.
     * </p>
     *
     * @param e The {@link ActionEvent} representing the action that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            try {
                String flightNumber = flightNumberField.getText();
                String destination = destinationField.getText();
                String origin = originField.getText();
                String departureDate = departureDateField.getText();
                int capacity = Integer.parseInt(capacityField.getText());
                double price = Double.parseDouble(priceField.getText());
                String duration = durationField.getText();
                double vegMealCost = Double.parseDouble(vegMealCostField.getText());
                double nonVegMealCost = Double.parseDouble(nonVegMealCostField.getText());

                if (flightNumber.trim().isEmpty() || destination.trim().isEmpty() ||
                        origin.trim().isEmpty() || departureDate.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AddFlight command = new AddFlight(flightNumber, destination, origin, departureDate, capacity, price, duration, vegMealCost, nonVegMealCost);
                String result = command.execute(fbs);

                data.store(fbs);

                JOptionPane.showMessageDialog(this, "Flight added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException | FlightBookingSystemException ex) {
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}