package bcu.cmp5332.bookingsystem.model;

/**
 * {@code Booking} class represents a flight booking made by a customer.
 * It encapsulates all the details related to a single booking, including the customer,
 * the flight booked, booking status (cancelled or not), seat class, meal preference,
 * seat number, and number of bags.
 * <p>
 * Each booking is uniquely identified by an ID and is associated with a specific {@link Customer}
 * and {@link Flight}. It also maintains information about the booking preferences and status.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see Customer
 * @see Flight
 */
public class Booking {
    /**
     * Unique identifier for the booking.
     */
    private int id;
    /**
     * The {@link Customer} who made the booking.
     */
    private Customer customer;
    /**
     * The {@link Flight} that is booked.
     */
    private Flight flight;
    /**
     * Status of the booking, indicating if it has been cancelled.
     */
    private boolean isCancelled;

    /**
     * The class of seat booked (e.g., "economy", "business").
     */
    private String seatClass;
    /**
     * The meal preference for the booking (e.g., "veg", "non-veg", "none").
     */
    private String mealPreference;
    /**
     * The assigned seat number for the booking.
     */
    private int seatNumber;
    /**
     * The number of bags associated with this booking.
     */
    private int numberOfBags;

    /**
     * Constructs a {@code Booking} object with specified details.
     *
     * @param id             The unique ID of the booking.
     * @param customer       The {@link Customer} who made the booking.
     * @param flight         The {@link Flight} that is booked.
     * @param seatClass      The class of seat for the booking.
     * @param mealPreference The meal preference for the booking.
     * @param seatNumber     The assigned seat number.
     * @param numberOfBags   The number of bags for the booking.
     */
    public Booking(int id, Customer customer, Flight flight, String seatClass, String mealPreference, int seatNumber, int numberOfBags) {
        this.id = id;
        this.customer = customer;
        this.flight = flight;
        this.isCancelled = false;
        this.seatClass = seatClass;
        this.mealPreference = mealPreference;
        this.seatNumber = seatNumber;
        this.numberOfBags = numberOfBags;
    }

    /**
     * Gets the unique identifier of the booking.
     *
     * @return The booking ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the {@link Customer} associated with this booking.
     *
     * @return The customer object.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets the {@link Flight} booked in this booking.
     *
     * @return The flight object.
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Checks if the booking is cancelled.
     *
     * @return {@code true} if the booking is cancelled, {@code false} otherwise.
     */
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Sets the cancellation status of the booking.
     *
     * @param cancelled {@code true} to cancel the booking, {@code false} to un-cancel.
     */
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    /**
     * Gets the seat class for this booking.
     *
     * @return The seat class (e.g., "economy", "business").
     */
    public String getSeatClass() {
        return seatClass;
    }

    /**
     * Sets the seat class for this booking.
     *
     * @param seatClass The seat class to set.
     */
    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    /**
     * Gets the meal preference for this booking.
     *
     * @return The meal preference (e.g., "veg", "non-veg", "none").
     */
    public String getMealPreference() {
        return mealPreference;
    }

    /**
     * Sets the meal preference for this booking.
     *
     * @param mealPreference The meal preference to set.
     */
    public void setMealPreference(String mealPreference) {
        this.mealPreference = mealPreference;
    }

     /**
      * Gets the seat number for this booking.
      *
      * @return The seat number.
      */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Sets the seat number for this booking.
     *
     * @param seatNumber The seat number to set.
     */
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * Gets the number of bags for this booking.
     *
     * @return The number of bags.
     */
    public int getNumberOfBags() {
        return numberOfBags;
    }

    /**
     * Sets the number of bags for this booking.
     *
     * @param numberOfBags The number of bags to set.
     */
    public void setNumberOfBags(int numberOfBags) {
        this.numberOfBags = numberOfBags;
    }

    /**
     * Returns a string representation of the {@code Booking} object.
     * Includes the booking ID, customer name, flight number, and cancellation status.
     *
     * @return A string representation of the booking.
     */
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", customer=" + customer.getName() +
                ", flight=" + flight.getFlightNumber() +
                ", isCancelled=" + isCancelled +
                '}';
    }
}