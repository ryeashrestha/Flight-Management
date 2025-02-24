package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@code LoginWindow} class represents the login window for the flight booking system.
 * It provides a graphical user interface for users to enter their username and password
 * to access the main application functionalities.
 * <p>
 * This window authenticates users against hardcoded credentials for demonstration purposes.
 * Upon successful login, it closes the login window and displays the main application window ({@link MainWindow}).
 * </p>
 * <p>
 * It extends {@link JFrame} for window functionalities and implements {@link ActionListener} to handle
 * button click events.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see MainWindow
 */
public class LoginWindow extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private String username = "admin";
    private String password = "123";
    private MainWindow mainWindow;

    /**
     * Constructs a {@code LoginWindow} object.
     * Initializes the login window's components and sets up the user interface.
     */
    public LoginWindow() {
        initialize();
    }

    /**
     * Initializes the graphical components of the {@code LoginWindow}.
     * Sets up the layout, labels, text fields, password field, and login button for user interaction.
     * This method is called internally during the construction of the window.
     */
    private void initialize() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Set background color
        getContentPane().setBackground(new Color(34, 45, 50));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(34, 45, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create Labels
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(65, 105, 225));  // Royal blue button
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(65, 105, 225), 2));
        loginButton.addActionListener(this);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect for the login button
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(100, 149, 237)); // Lighter blue on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(65, 105, 225)); // Original color
            }
        });

        // Positioning components on the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        add(panel);
    }

    /**
     * Handles action events triggered by button clicks in the {@code LoginWindow}.
     * <p>
     * If the "Login" button is clicked, it retrieves the entered username and password,
     * validates them against hardcoded credentials, and upon successful validation,
     * displays a success message, closes the login window, and opens the main application window.
     * If login fails or fields are empty, it displays appropriate error messages.
     * </p>
     *
     * @param e The {@link ActionEvent} representing the action that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String enteredUsername = usernameField.getText();
            String enteredPassword = new String(passwordField.getPassword());

            if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (enteredUsername.equals(username) && enteredPassword.equals(password)) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Close the login window and open the main application window
                this.dispose();
                if (getMainWindow() != null) {
                    getMainWindow().setVisible(true);
                } else {
                    System.out.println("Error on login window.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Gets the {@link MainWindow} associated with this login window.
     *
     * @return The {@link MainWindow} instance.
     */
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    /**
     * Sets the {@link MainWindow} instance for this login window.
     *
     * @param mainWindow The {@link MainWindow} instance to set.
     */
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Main method to launch the {@code LoginWindow} application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }
}