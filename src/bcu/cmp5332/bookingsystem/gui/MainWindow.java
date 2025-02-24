package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * {@code MainWindow} class is the main window of the Flight Booking System application.
 * It serves as the primary graphical interface for users to interact with the system,
 * providing access to various functionalities such as adding customers and flights,
 * listing and deleting customers and flights, and issuing bookings.
 * <p>
 * The window is structured with a sidebar navigation menu on the left and a main content area
 * on the right. It utilizes {@link FlightBookingSystem} and {@link FlightBookingSystemData}
 * to manage the application's business logic and data persistence.
 * </p>
 * <p>
 * It extends {@link JFrame} for window functionalities and implements {@link ActionListener} to handle
 * button click events from the sidebar menu.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see LoginWindow
 * @see FlightBookingSystem
 * @see FlightBookingSystemData
 * @see AddCustomerWindow
 * @see AddFlightWindow
 * @see ListCustomersWindow
 * @see ListFlightsWindow
 * @see IssueBookingWindow
 * @see DeleteFlightAction
 * @see DeleteCustomerAction
 */
public class MainWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private FlightBookingSystemData data;

    // Buttons
    private JButton addCustomerButton;
    private JButton addFlightButton;
    private JButton listCustomersButton;
    private JButton listFlightsButton;
    private JButton issueBookingButton;
    private JButton deleteFlightButton;
    private JButton deleteCustomerButton;
    private JPanel sideBar; // Declare sidebar as a class member

    /**
     * Constructs a {@code MainWindow} object.
     *
     * @param fbs  The {@link FlightBookingSystem} instance to be used by this window.
     * @param data The {@link FlightBookingSystemData} instance for data management.
     */
    public MainWindow(FlightBookingSystem fbs, FlightBookingSystemData data) {
        this.fbs = fbs;
        this.data = data;
        initialize();
    }

    /**
     * Initializes the graphical components of the {@code MainWindow}.
     * Sets up the layout, sidebar with navigation buttons, main body panel, and adds action listeners to the buttons.
     * This method is called internally during the construction of the window.
     */
    private void initialize() {
        setTitle("Flight Booking System");
        setSize(1000, 500); // Adjusted size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Light Gray Background

        // SideBar (Left)
        sideBar = new JPanel(); // Initialize sidebar here
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(new Color(51, 51, 51)); // Dark gray sidebar
        sideBar.setPreferredSize(new Dimension(250, 700));
        sideBar.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add some padding

        // Title Label
        JLabel titleLabel = new JLabel("Bookingsystem");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 204, 0)); // Gold color
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title
        sideBar.add(titleLabel);
        sideBar.add(Box.createRigidArea(new Dimension(0, 30))); // Space after title

        // Create buttons with style and color
        addCustomerButton = createStyledButton("Add Customer", new Color(66, 134, 244), new Color(255, 255, 255));  // Blue
        addFlightButton = createStyledButton("Add Flight", new Color(76, 175, 80), new Color(255, 255, 255));  // Green
        listCustomersButton = createStyledButton("List Customers", new Color(255, 235, 59), new Color(0, 0, 0));  // Yellow
        listFlightsButton = createStyledButton("List Flights", new Color(255, 112, 67), new Color(255, 255, 255));  // Orange
        issueBookingButton = createStyledButton("Issue Booking", new Color(3, 169, 244), new Color(255, 255, 255));  // Cyan
        deleteFlightButton = createStyledButton("Delete Flight", new Color(244, 67, 54), new Color(255, 255, 255));  // Red
        deleteCustomerButton = createStyledButton("Delete Customer", new Color(158, 158, 158), new Color(255, 255, 255));  // Grey

        // Add buttons to the sidebar
        sideBar.add(addCustomerButton);
        sideBar.add(addFlightButton);
        sideBar.add(listCustomersButton);
        sideBar.add(listFlightsButton);
        sideBar.add(issueBookingButton);
        sideBar.add(deleteFlightButton);
        sideBar.add(deleteCustomerButton);

        mainPanel.add(sideBar, BorderLayout.WEST);

        // Main Body (Center)
        JPanel bodyPanel = new JPanel(new BorderLayout()); // Use BorderLayout
        bodyPanel.setBackground(new Color(255, 255, 255));

        // Body Image - Ensure the path is correct and image exists!
        ImageIcon bodyImageIcon = new ImageIcon("resources/Images/body.png"); // Assumes image is in a 'resources' folder
        if (bodyImageIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image bodyImage = bodyImageIcon.getImage().getScaledInstance(800, 500, Image.SCALE_AREA_AVERAGING); // Fixed size
            JLabel bodyImageLabel = new JLabel(new ImageIcon(bodyImage));
            bodyImageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center image
            bodyPanel.add(bodyImageLabel, BorderLayout.CENTER);
        } else {
            bodyPanel.add(new JLabel("Image not found. Place 'body.png' in the resources/Images folder.", SwingConstants.CENTER), BorderLayout.CENTER);
        }

        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        // Add main panel to the frame
        add(mainPanel);

        // Add listeners to buttons
        addCustomerButton.addActionListener(this);
        addFlightButton.addActionListener(this);
        listCustomersButton.addActionListener(this);
        listFlightsButton.addActionListener(this);
        issueBookingButton.addActionListener(this);
        deleteFlightButton.addActionListener(this);
        deleteCustomerButton.addActionListener(this);
    }

    /**
     * Helper method to create and style a {@link JButton} with custom font, colors, borders, and hover effects.
     *
     * @param text      The text to be displayed on the button.
     * @param bgColor   The background {@link Color} of the button.
     * @param textColor The text {@link Color} of the button.
     * @return The styled {@link JButton}.
     */
    private JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(textColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(bgColor, 2)); // Add a border for visual separation
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(220, 50)); // Adjusted size
        button.setMaximumSize(new Dimension(220, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        // Hover Effect
        button.addMouseListener(new MouseAdapter() {
            private Color originalColor = bgColor; // Store the original color

            @Override
            public void mouseEntered(MouseEvent evt) {
                originalColor = button.getBackground();
                button.setBackground(originalColor.darker());
                button.setBorder(new LineBorder(originalColor.darker(), 2));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(originalColor);
                button.setBorder(new LineBorder(originalColor, 2));
            }
        });

        // Add space below the button
        if (sideBar != null) {
            sideBar.add(button);
            sideBar.add(Box.createRigidArea(new Dimension(0, 15)));  //Adding space between buttons
        }
        return button;
    }

    /**
     * Handles action events triggered by button clicks in the {@code MainWindow}.
     * <p>
     * This method is responsible for opening different windows based on the button clicked in the sidebar menu.
     * It handles actions for adding a flight, listing flights, adding a customer, listing customers,
     * issuing a booking, deleting a flight, and deleting a customer.
     * </p>
     *
     * @param e The {@link ActionEvent} representing the action that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addFlightButton) {
            new AddFlightWindow(fbs, data, this).setVisible(true);
        } else if (e.getSource() == listFlightsButton) {
            data = new FlightBookingSystemData(fbs);
            new ListFlightsWindow(fbs, data).setVisible(true);
        } else if (e.getSource() == addCustomerButton) {
            new AddCustomerWindow(fbs, data, null).setVisible(true);
        } else if (e.getSource() == listCustomersButton) {
            data = new FlightBookingSystemData(fbs);
            new ListCustomersWindow(fbs, data).setVisible(true);
        } else if (e.getSource() == issueBookingButton) {
            new IssueBookingWindow(fbs, data).setVisible(true);
        } else if (e.getSource() == deleteFlightButton) {
            DeleteFlightAction.deleteFlight(fbs, data, this);
        } else if (e.getSource() == deleteCustomerButton) {
            DeleteCustomerAction.deleteCustomer(fbs, data, this);
        }
    }
}