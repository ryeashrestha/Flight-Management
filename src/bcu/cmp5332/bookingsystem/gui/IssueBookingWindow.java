package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * {@code IssueBookingWindow} class represents a window for issuing a new booking in the flight booking system.
 * It provides a graphical user interface for selecting a customer, a flight, seat class, meal preference,
 * and number of bags. It also calculates and displays the price, including potential discounts from promocodes.
 * Upon submission, it uses the {@link AddBooking} command to create the booking in the system and persists the data.
 * <p>
 * This window is part of the graphical user interface for the flight booking system and interacts
 * with the underlying business logic and data management components.
 * </p>
 * <p>
 * It extends {@link JFrame} for window functionalities and implements {@link ActionListener} to handle
 * user interactions such as button clicks and combo box selections.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see MainWindow
 * @see FlightBookingSystem
 * @see FlightBookingSystemData
 * @see AddBooking
 * @see Customer
 * @see Flight
 */
public class IssueBookingWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final FlightBookingSystemData data;
    private JComboBox<String> customerCombo;
    private JComboBox<String> flightCombo;
    private JComboBox<String> seatClassCombo = new JComboBox<>(new String[]{"Economy", "Business", "FirstClass"});
    private JComboBox<String> mealPreferenceCombo = new JComboBox<>(new String[]{"Veg", "Non-Veg"});
    private JLabel priceLabel = new JLabel("Price: ");
    private JButton issueButton = new JButton("Issue Booking");
    private JButton cancelButton = new JButton("Cancel");
    private JTextField numberOfBagsField = new JTextField("0", 5); // Bags
    private JButton applyPromoButton = new JButton("Apply Promocode"); // AddPromo
    private Flight selectedFlight;
    private Customer selectedCustomer;
    private String promocode;

    /**
     * Constructs an {@code IssueBookingWindow} object.
     *
     * @param fbs  The {@link FlightBookingSystem} instance to interact with.
     * @param data The {@link FlightBookingSystemData} instance for data persistence.
     */
    public IssueBookingWindow(FlightBookingSystem fbs, FlightBookingSystemData data) {
        this.fbs = fbs;
        this.data = data;
        initialize();
    }

    /**
     * Loads active customers into the customer combo box.
     * Clears existing items and populates the combo box with customer display strings obtained from {@link FlightBookingSystem#getCustomerDisplayString(Customer)}.
     * Includes a blank option at the beginning of the list.
     */
    private void loadCustomers() {
        customerCombo.removeAllItems();  // Clear existing items
        customerCombo.addItem("");  // Add blank option

        List<Customer> customers = fbs.getActiveCustomers();
        for (Customer customer : customers) {
            customerCombo.addItem(fbs.getCustomerDisplayString(customer));  // Add customers to combo box
        }
    }

    /**
     * Loads active flights into the flight combo box.
     * Clears existing items and populates the combo box with flight display strings obtained from {@link FlightBookingSystem#getFlightDisplayString(Flight)}.
     * Includes a blank option at the beginning of the list.
     */
    private void loadFlights() {
        flightCombo.removeAllItems();  // Clear existing items
        flightCombo.addItem("");  // Add blank option

        List<Flight> flights = fbs.getActiveFlights();
        for (Flight flight : flights) {
            flightCombo.addItem(fbs.getFlightDisplayString(flight));  // Add flights to combo box
        }
    }

    /**
     * Initializes the graphical components of the {@code IssueBookingWindow}.
     * Sets up the layout, combo boxes for customer and flight selection, combo boxes for seat class and meal preference,
     * text field for number of bags, price label, and buttons for issuing booking, cancelling, and applying promocodes.
     * This method is called internally during the construction of the window.
     */
    private void initialize() {
        setTitle("Issue Booking");
        setSize(500, 600); // Adjusted size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for flexibility
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components

        // Initialize Customer Combo
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Customer:"), gbc);
        customerCombo = new JComboBox<>();
        customerCombo.addItem("");  // Add blank option
        loadCustomers();  // Populate customer list after adding blank option
        gbc.gridx = 1;
        panel.add(customerCombo, gbc);

        // Initialize Flight Combo
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Flight:"), gbc);
        flightCombo = new JComboBox<>();
        flightCombo.addItem("");  // Add blank option
        loadFlights();  // Populate flight list after adding blank option
        gbc.gridx = 1;
        panel.add(flightCombo, gbc);


        // Seat Class Combo
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Seat Class:"), gbc);
        seatClassCombo = new JComboBox<>(new String[]{"", "Economy", "Business", "FirstClass"});
        gbc.gridx = 1;
        panel.add(seatClassCombo, gbc);

        // Meal Preference Combo
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Meal Preference:"), gbc);
        mealPreferenceCombo = new JComboBox<>(new String[]{"", "Veg", "Non-Veg"});
        gbc.gridx = 1;
        panel.add(mealPreferenceCombo, gbc);

        // Number of Bags Field
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Number of Bags:"), gbc);
        gbc.gridx = 1;
        panel.add(numberOfBagsField, gbc);

        // Price Label
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        panel.add(priceLabel, gbc);

        // Buttons (Issue, Cancel, Apply Promo)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Align buttons properly
        buttonPanel.add(issueButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(applyPromoButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Take up 2 columns
        panel.add(buttonPanel, gbc);
     // Change button colors
        issueButton.setBackground(new Color(0, 123, 255)); // Blue color for issueButton
        issueButton.setForeground(Color.WHITE); // White text color for issueButton
        cancelButton.setBackground(new Color(220, 53, 69)); // Red color for cancelButton
        cancelButton.setForeground(Color.WHITE); // White text color for cancelButton
        applyPromoButton.setBackground(new Color(28, 200, 138)); // Green color for applyPromoButton
        applyPromoButton.setForeground(Color.WHITE); // White text color for applyPromoButton

        issueButton.addActionListener(this);
        cancelButton.addActionListener(this);
        seatClassCombo.addActionListener(this);
        mealPreferenceCombo.addActionListener(this);
        customerCombo.addActionListener(this);
        flightCombo.addActionListener(this);
        numberOfBagsField.addActionListener(this);
        applyPromoButton.addActionListener(this);

        add(panel);

        // Initial price update
        updatePrice();
    }

    /**
     * Handles action events triggered by button clicks and combo box selections in the {@code IssueBookingWindow}.
     * <p>
     * If the "Issue Booking" button is clicked, it attempts to issue a booking using the selected customer, flight,
     * seat class, meal preference, and number of bags. It validates the selections and available seats before
     * executing the {@link AddBooking} command.
     * </p>
     * <p>
     * If the "Cancel" button is clicked, it disposes of the window.
     * </p>
     * <p>
     * If the "Apply Promocode" button is clicked, it opens a {@link PromocodeDialog} to get a promocode from the user.
     * </p>
     * <p>
     * It also handles selection changes in customer and flight combo boxes to update the selected customer and flight objects,
     * and updates the displayed price whenever any relevant component's state changes.
     * </p>
     *
     * @param e The {@link ActionEvent} representing the action that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueButton) {
            try {
                if (selectedCustomer == null || selectedFlight == null) {
                    JOptionPane.showMessageDialog(this, "Please select a customer and a flight.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String seatClass = (String) seatClassCombo.getSelectedItem();
                String mealPreference = (String) mealPreferenceCombo.getSelectedItem();
                int numberOfBags = Integer.parseInt(numberOfBagsField.getText()); // New code

                if (selectedFlight.getAvailableSeats() <= 0) {
                    JOptionPane.showMessageDialog(this, "Flight is full. Cannot book.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AddBooking command = new AddBooking(selectedCustomer.getId(), selectedFlight.getId(), seatClass, mealPreference, numberOfBags);
                String result = command.execute(fbs);
                data.store(fbs);

                JOptionPane.showMessageDialog(this, "Booking issued successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException | FlightBookingSystemException ex) {
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        } else if (e.getSource() == applyPromoButton) {
            PromocodeDialog promocodeDialog = new PromocodeDialog(this);
            promocodeDialog.setVisible(true);

            promocode = promocodeDialog.getPromocode();
            updatePrice();
        } else if (e.getSource() == customerCombo) {
             List<Customer> activeCustomers = fbs.getActiveCustomers();
                 //The if statement code
                if (customerCombo.getSelectedItem() != null && !customerCombo.getSelectedItem().toString().isEmpty()) {
                  int selectedIndex = customerCombo.getSelectedIndex() - 1;
                    if (selectedIndex >= 0 && selectedIndex < activeCustomers.size()) {
                        selectedCustomer = activeCustomers.get(selectedIndex);
                        updatePrice();
                    }
                }
        } else if (e.getSource() == flightCombo) {
            List<Flight> activeFlights = fbs.getActiveFlights();
                if (flightCombo.getSelectedItem() != null && !flightCombo.getSelectedItem().toString().isEmpty()) {
                    int selectedIndex = flightCombo.getSelectedIndex() - 1;
                    if (selectedIndex >= 0 && selectedIndex < activeFlights.size()) {
                        selectedFlight = activeFlights.get(selectedIndex);
                        updatePrice();
                    }
                }
        } else {
            updatePrice(); // Update price based on selection
        }
    }

    /**
     * Updates the price label based on the current selections in the window.
     * Calculates the price dynamically based on the selected flight, seat class, meal preference, number of bags, and applied promocode.
     * Displays a detailed price breakdown in the {@code priceLabel}.
     * If no flight is selected, it sets the price label to indicate that a flight needs to be selected.
     */
    private void updatePrice() {
        try {
             if (selectedFlight == null) {
                priceLabel.setText("Price: Select a flight");
                return;
            }

            String seatClass = (String) seatClassCombo.getSelectedItem();
            String mealPreference = (String) mealPreferenceCombo.getSelectedItem();
            int numberOfBags = Integer.parseInt(numberOfBagsField.getText()); // Get number of bags

            double basePrice = selectedFlight.getPrice();
            double mealCost = 0.0;

             if ("Veg".equals(mealPreference)) {
                    mealCost = selectedFlight.getVegMealCost();
                } else if ("Non-Veg".equals(mealPreference)) {
                    mealCost = selectedFlight.getNonVegMealCost();
                }

            double seatClassMultiplier = 1.0;

            switch (seatClass) {
                case "Business":
                    seatClassMultiplier = 2.0;
                    mealCost *= 2;
                    break;
                case "FirstClass":
                    seatClassMultiplier = 4.0;
                    mealCost *= 4;
                    break;
                default:
                    seatClassMultiplier = 1.0;
                    break;
            }

             double baggageFee = 0.0;

            if (numberOfBags > 2) {
                baggageFee = (numberOfBags - 2) * 20.0;
            }

            // Promocode logic
            double discount = 0.0;

            if (promocode != null) {
                if (promocode.equals("123456789")) {
                    discount = 0.5; // 50% discount
                } else if (promocode.equals("122222222")) {
                    discount = 1.0; // 100% discount
                }
            }

            double totalPrice = (basePrice * seatClassMultiplier + mealCost + baggageFee) * (1 - discount);

            String priceDetails = String.format(
                "<html>Price Breakdown:<br>" +
                "Original Price: %.2f<br>" +
                "Seat Class (Multiplier: %.1f): +%.2f<br>" +
                "Meal: +%.2f<br>" +
                "Baggage Fee: +%.2f<br>" +
                "Discount: %.2f%% <br>" +
                "Total Price: %.2f</html>",
                basePrice,
                seatClassMultiplier, basePrice * (seatClassMultiplier - 1),
                mealCost,
                baggageFee,
                discount * 100,
                totalPrice
            );

            priceLabel.setText(priceDetails);

        } catch (NumberFormatException ex) {
            priceLabel.setText("Price: Invalid Flight ID");
        }
    }

    /**
     * Gets the currently selected customer in the window.
     *
     * @return The selected {@link Customer} object, or {@code null} if no customer is selected.
     */
    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    /**
     * Gets the currently selected flight in the window.
     *
     * @return The selected {@link Flight} object, or {@code null} if no flight is selected.
     */
    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    /**
     * Gets the selected seat class from the seat class combo box.
     *
     * @return The selected seat class as a String.
     */
    public String getSeatClass() {
        return (String) seatClassCombo.getSelectedItem();
    }

    /**
     * Gets the selected meal preference from the meal preference combo box.
     *
     * @return The selected meal preference as a String.
     */
    public String getMealPreference() {
        return (String) mealPreferenceCombo.getSelectedItem();
    }

    /**
     * Gets the number of bags entered in the number of bags text field.
     *
     * @return The number of bags as an integer. Returns 0 if the input is not a valid number.
     */
    public int getNumberOfBags() {
        try {
            return Integer.parseInt(numberOfBagsField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}