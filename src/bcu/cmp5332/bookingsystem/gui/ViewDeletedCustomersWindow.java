package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * {@code ViewDeletedCustomersWindow} class represents a window that displays a list of customers
 * who have been marked as deleted (soft deleted) in the flight booking system.
 * It uses a {@link JTable} to present the information of deleted customers, including their ID,
 * Name, Phone, and Email.
 * <p>
 * This window is designed for administrative purposes, allowing staff to review customers who have
 * been removed from the active customer list but are still retained in the system for record-keeping.
 * </p>
 * <p>
 * It extends {@link JFrame} for window functionalities and is intended to be used in conjunction with
 * {@link MainWindow} to provide a complete user interface for the flight booking system.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see MainWindow
 * @see FlightBookingSystemData
 * @see Customer
 */
public class ViewDeletedCustomersWindow extends JFrame {

    private JTable deletedCustomersTable;
    private DefaultTableModel tableModel;
    private final FlightBookingSystemData data;

    /**
     * Constructs a {@code ViewDeletedCustomersWindow} object.
     *
     * @param data The {@link FlightBookingSystemData} instance to retrieve deleted customer data.
     */
    public ViewDeletedCustomersWindow(FlightBookingSystemData data) {
        this.data = data;
        initialize();
    }

    /**
     * Initializes the graphical components of the {@code ViewDeletedCustomersWindow}.
     * Sets up the layout, table model, {@link JTable} for displaying deleted customers,
     * and loads the initial list of deleted customers into the table.
     * This method is called internally during the construction of the window.
     */
    private void initialize() {
        setTitle("Deleted Customers");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(new Color(240, 248, 255));  // Light blue background

        // Table Model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Email");

        deletedCustomersTable = new JTable(tableModel);

        // Customize table appearance
        deletedCustomersTable.setFont(new Font("Arial", Font.PLAIN, 14));  // Change font
        deletedCustomersTable.setRowHeight(25);  // Set row height for better readability
        deletedCustomersTable.setSelectionBackground(new Color(173, 216, 230));  // Light blue selection
        deletedCustomersTable.setSelectionForeground(Color.BLACK);  // Black text on selection

        // Customize the table header
        JTableHeader tableHeader = deletedCustomersTable.getTableHeader();
        tableHeader.setBackground(new Color(70, 130, 180));  // SteelBlue background for header
        tableHeader.setForeground(Color.WHITE);  // White text for header
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));  // Bold header font

        // Scroll Pane customization
        JScrollPane scrollPane = new JScrollPane(deletedCustomersTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Add padding around the table

        // Load Customers into Table
        loadCustomers(data.getDeletedCustomers());

        // Add components to the window
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Loads deleted customer data into the {@link JTable}.
     * Clears existing table data and populates the table with information from the provided list of customers.
     *
     * @param customers A list of {@link Customer} objects that are marked as deleted and to be displayed in the table.
     */
    private void loadCustomers(List<Customer> customers) {
        tableModel.setRowCount(0); // Clear existing data

        for (Customer customer : customers) {
            Object[] rowData = {customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail()};
            tableModel.addRow(rowData);
        }
    }
}