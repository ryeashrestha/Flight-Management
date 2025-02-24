package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@code PromocodeDialog} class represents a modal dialog window that prompts the user to enter a promocode.
 * This dialog is designed to be used within the flight booking system to apply discounts or special offers
 * based on valid promocodes.
 * <p>
 * The dialog contains a text field for promocode input, and "Apply" and "Cancel" buttons.
 * It validates the entered promocode against predefined valid codes and provides feedback to the user.
 * </p>
 * <p>
 * It extends {@link JDialog} for dialog window functionalities and implements {@link ActionListener} to handle
 * button click events.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see IssueBookingWindow
 */
public class PromocodeDialog extends JDialog implements ActionListener {

    private JTextField promocodeField = new JTextField(10);
    private JButton applyButton = new JButton("Apply");
    private JButton cancelButton = new JButton("Cancel");
    private String promocode = null;

    /**
     * Constructs a {@code PromocodeDialog} object.
     *
     * @param parent The parent {@link JFrame} for this dialog, used for positioning and modality.
     */
    public PromocodeDialog(JFrame parent) {
        super(parent, "Apply Promocode", true);
        setLayout(new BorderLayout(10, 10));

        // Create a panel for the input fields and buttons
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(new Color(240, 240, 240)); // Set background color for input panel

        JLabel label = new JLabel("Enter Promocode:");
        label.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for label

        inputPanel.add(label);
        inputPanel.add(promocodeField);

        // Set button colors and styles
        applyButton.setBackground(new Color(51, 153, 255)); // Blue background for apply button
        applyButton.setForeground(Color.WHITE); // White text
        applyButton.setFocusPainted(false); // Remove focus border
        applyButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font for apply button

        cancelButton.setBackground(new Color(255, 51, 51)); // Red background for cancel button
        cancelButton.setForeground(Color.WHITE); // White text
        cancelButton.setFocusPainted(false); // Remove focus border
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font for cancel button

        // Button panel with some spacing
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240)); // Set background color for button panel
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);

        applyButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // Set background color for the dialog
        getContentPane().setBackground(new Color(240, 240, 240));

        // Add the input panel and button panel to the dialog
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(300, 150); // Set size of the dialog
        setLocationRelativeTo(parent); // Center the dialog relative to parent window
    }

    /**
     * Handles action events triggered by button clicks in the {@code PromocodeDialog}.
     * <p>
     * If the "Apply" button is clicked, it retrieves the entered promocode, validates it against
     * predefined valid codes ("123456789" and "122222222"), and closes the dialog if valid.
     * If the promocode is invalid, it displays an error message.
     * </p>
     * <p>
     * If the "Cancel" button is clicked, it sets the promocode to null and disposes of the dialog,
     * effectively cancelling the promocode application.
     * </p>
     *
     * @param e The {@link ActionEvent} representing the action that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == applyButton) {
            promocode = promocodeField.getText();
            if (!promocode.equals("123456789") && !promocode.equals("122222222")) {
                // Show an error message if the promocode is invalid
                JOptionPane.showMessageDialog(this, "Wrong promocode", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose(); // Close the dialog if the promocode is valid
            }
        } else if (e.getSource() == cancelButton) {
            promocode = null;
            dispose(); // Close the dialog without applying a promocode
        }
    }

    /**
     * Gets the promocode entered by the user.
     *
     * @return The promocode string entered by the user, or {@code null} if no promocode was applied or if cancelled.
     */
    public String getPromocode() {
        return promocode;
    }
}