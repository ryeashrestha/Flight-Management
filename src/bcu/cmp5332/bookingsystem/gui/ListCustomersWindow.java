package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * {@code ListCustomersWindow} class represents a window that displays a list of customers
 * in the flight booking system. It uses a {@link JTable} to present customer information
 * such as ID, Name, Phone, and Email.
 * <p>
 * This window provides functionalities to view customer details, sort customers by name,
 * and navigate to a window displaying deleted customers. It's part of the graphical user
 * interface for managing customer data within the booking system.
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
 * @see Customer
 * @see ShowCustomerWindow
 * @see ViewDeletedCustomersWindow
 */
public class ListCustomersWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final FlightBookingSystemData data;
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JButton showCustomerButton;
    private JButton sortButton;
    private JButton viewDeletedCustomersButton;

    /**
     * Constructs a {@code ListCustomersWindow} object.
     *
     * @param fbs  The {@link FlightBookingSystem} instance to interact with.
     * @param data The {@link FlightBookingSystemData} instance for data access.
     */
    public ListCustomersWindow(FlightBookingSystem fbs, FlightBookingSystemData data) {
        this.fbs = fbs;
        this.data = data;
        initialize();
    }

    /**
     * Initializes the graphical components of the {@code ListCustomersWindow}.
     * Sets up the layout, table model, {@link JTable}, buttons, and loads customer data into the table.
     * This method is called internally during the construction of the window.
     */
    private void initialize() {
        setTitle("Customer List");
        setSize(700, 500); // Increase window size for better visibility
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Set background color of the main window
        getContentPane().setBackground(Color.WHITE);

        // Table Model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Email");

        customerTable = new JTable(tableModel);
        customerTable.setBackground(Color.WHITE); // Light background for the table
        customerTable.setForeground(Color.BLACK); // Black text for the table
        customerTable.setSelectionBackground(new Color(0, 123, 255)); // Blue highlight for selected row
        JScrollPane scrollPane = new JScrollPane(customerTable);

        // Load Customers into Table
        loadCustomers(data.getActiveCustomers());

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Better spacing for buttons
        buttonPanel.setBackground(Color.WHITE); // Set background of the button panel to white

        showCustomerButton = createStyledButton("Show Customer Details", new Color(0, 123, 255));
        sortButton = createStyledButton("Sort by Name", new Color(40, 167, 69)); // Green button
        viewDeletedCustomersButton = createStyledButton("View Deleted Customers", new Color(220, 53, 69)); // Red button

        showCustomerButton.addActionListener(this);
        sortButton.addActionListener(this);
        viewDeletedCustomersButton.addActionListener(this);

        buttonPanel.add(showCustomerButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(viewDeletedCustomersButton);

        // Layout configuration
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Loads customer data into the {@link JTable}.
     * Clears existing table data and populates the table with information from the provided list of customers.
     *
     * @param customers A list of {@link Customer} objects to be displayed in the table.
     */
    private void loadCustomers(List<Customer> customers) {
        tableModel.setRowCount(0); // Clear existing data

        for (Customer customer : customers) {
            Object[] rowData = {customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail()};
            tableModel.addRow(rowData);
        }
    }

    /**
     * Helper method to create a styled {@link JButton} with custom font, size, background color,
     * foreground color, focus painted, and tooltip text.
     *
     * @param text    The text to display on the button.
     * @param bgColor The background {@link Color} for the button.
     * @return The styled {@link JButton}.
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(200, 40));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Remove the focus border
        button.setToolTipText("Click to " + text.toLowerCase());
        return button;
    }

    /**
     * Handles action events triggered by button clicks in the {@code ListCustomersWindow}.
     * <p>
     * If the "Show Customer Details" button is clicked, it opens a {@link ShowCustomerWindow} for the selected customer.
     * If the "Sort by Name" button is clicked, it sorts the customers by name and reloads the table.
     * If the "View Deleted Customers" button is clicked, it opens a {@link ViewDeletedCustomersWindow}.
     * </p>
     *
     * @param e The {@link ActionEvent} representing the action that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showCustomerButton) {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow >= 0) {
                int customerId = (int) tableModel.getValueAt(selectedRow, 0);
                Customer customer = fbs.getCustomerById(customerId);
                if (customer != null) {
                    new ShowCustomerWindow(fbs, customer).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a customer.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (e.getSource() == sortButton) {
            List<Customer> sortedCustomers = fbs.sortCustomersByName();
            loadCustomers(sortedCustomers); // Load sorted customers
        } else if (e.getSource() == viewDeletedCustomersButton) {
            new ViewDeletedCustomersWindow(data).setVisible(true);
        }
    }
}