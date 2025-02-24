package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * {@code AddCustomerWindow} class represents a window for adding a new customer to the booking system.
 * It provides a graphical user interface for inputting customer details such as name, phone number,
 * email, and special requests. Upon submission, it uses the {@link AddCustomer} command to add
 * the customer to the system and updates the data store.
 * <p>
 * This window is part of the graphical user interface for the flight booking system and interacts
 * with the underlying business logic and data management components.
 * </p>
 * <p>
 * It extends {@link JFrame} for window functionalities and implements {@link ActionListener} to handle
 * button click events.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see MainWindow
 * @see FlightBookingSystem
 * @see FlightBookingSystemData
 * @see AddCustomer
 */
public class AddCustomerWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final FlightBookingSystemData data;
    private final MainWindow mainWindow;
    private JTextField nameField = new JTextField(20);
    private JTextField phoneField = new JTextField(20);
    private JTextField emailField = new JTextField(20);
    private JTextArea specialRequestsArea = new JTextArea(5, 20); // Added Special Request Area
    private JButton addButton = new JButton("Add");
    private JButton cancelButton = new JButton("Cancel");

    /**
     * Constructs an {@code AddCustomerWindow} object.
     *
     * @param fbs        The {@link FlightBookingSystem} instance to interact with.
     * @param data       The {@link FlightBookingSystemData} instance for data persistence.
     * @param mainWindow The main application window, used for potential updates or interactions.
     */
    public AddCustomerWindow(FlightBookingSystem fbs, FlightBookingSystemData data, MainWindow mainWindow) {
        this.fbs = fbs;
        this.data = data;
        this.mainWindow = mainWindow;
        initialize();
    }

    /**
     * Initializes the graphical components of the {@code AddCustomerWindow}.
     * Sets up the layout, labels, text fields, text area, and buttons for user interaction.
     * This method is called internally during the construction of the window.
     */
    private void initialize() {
        setTitle("Add New Customer");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the components horizontally

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(emailField, gbc);

        // Special Request
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Special Requests:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        JScrollPane scrollPane = new JScrollPane(specialRequestsArea); //Add Scrollable
        panel.add(scrollPane, gbc);
        specialRequestsArea.setPreferredSize(new Dimension(200, 75));

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(addButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(cancelButton, gbc);

        // Set custom button colors
        addButton = createStyledButton(addButton, new Color(40, 167, 69)); // Green Button
        cancelButton = createStyledButton(cancelButton, new Color(220, 53, 69)); // Red Button

        addButton.addActionListener(this);
        cancelButton.addActionListener(this);

        add(panel);
    }

    /**
     * Helper method to create a styled button with custom font, size, background color,
     * foreground color, focus painted, and tooltip text.
     *
     * @param button  The {@link JButton} to style.
     * @param bgColor The background {@link Color} for the button.
     * @return The styled {@link JButton}.
     */
    private JButton createStyledButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Remove the focus border
        button.setToolTipText("Click to " + button.getText().toLowerCase());
        return button;
    }

    /**
     * Handles action events triggered by button clicks in the {@code AddCustomerWindow}.
     * <p>
     * If the "Add" button is clicked, it retrieves the customer details from the input fields,
     * validates the input, creates an {@link AddCustomer} command, executes it, and updates the data store.
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
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String specialRequests = specialRequestsArea.getText();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AddCustomer command = new AddCustomer(name, phone, email, specialRequests);
                String result = command.execute(fbs);

                data.store(fbs);

                JOptionPane.showMessageDialog(this, "Customer added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();

            } catch (FlightBookingSystemException ex) { //ADD exception
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}