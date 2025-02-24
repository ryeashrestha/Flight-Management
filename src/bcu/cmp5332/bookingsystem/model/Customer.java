package bcu.cmp5332.bookingsystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Customer} class represents a customer in the flight booking system.
 * It stores personal details such as name, phone number, email, and special requests.
 * Each customer is uniquely identified by an ID and can have multiple bookings associated with them.
 * <p>
 * Customers can be marked as deleted in the system, which is a soft deletion mechanism,
 * and the class also maintains a list of bookings made by the customer.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 */
public class Customer {

    /**
     * Unique identifier for the customer.
     */
    private int id;
    /**
     * The name of the customer.
     */
    private String name;
    /**
     * The phone number of the customer.
     */
    private String phone;
    /**
     * The email address of the customer.
     */
    private String email;
    /**
     * Special requests or notes associated with the customer.
     */
    private String specialRequests;
    /**
     * Flag indicating if the customer is marked as deleted (soft deletion).
     */
    private boolean isDeleted;
    /**
     * List of {@link Booking} objects associated with this customer.
     */
    private List<Booking> bookings;

    /**
     * Constructs a {@code Customer} object with the specified details.
     *
     * @param id              The unique ID of the customer.
     * @param name            The name of the customer.
     * @param phone           The phone number of the customer.
     * @param email           The email address of the customer.
     * @param specialRequests Special requests or notes for the customer.
     */
    public Customer(int id, String name, String phone, String email, String specialRequests) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.specialRequests = specialRequests;
        this.isDeleted = false;
        this.bookings = new ArrayList<>();
    }

    /**
     * Gets the unique identifier of the customer.
     *
     * @return The customer ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the customer.
     *
     * @return The customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the phone number of the customer.
     *
     * @return The customer's phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets the email address of the customer.
     *
     * @return The customer's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets any special requests associated with the customer.
     *
     * @return The customer's special requests.
     */
    public String getSpecialRequests() {
        return specialRequests;
    }

    /**
     * Checks if the customer is marked as deleted.
     *
     * @return {@code true} if the customer is deleted, {@code false} otherwise.
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Sets the deletion status of the customer.
     *
     * @param deleted {@code true} to mark the customer as deleted, {@code false} to mark as active.
     */
    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    /**
     * Gets the list of bookings associated with this customer.
     *
     * @return A list of {@link Booking} objects made by this customer.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Adds a booking to the list of bookings for this customer.
     *
     * @param booking The {@link Booking} object to add.
     */
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}