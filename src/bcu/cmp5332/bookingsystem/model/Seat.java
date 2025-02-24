package bcu.cmp5332.bookingsystem.model;

/**
 * {@code Seat} class represents a seat on a flight.
 * Each seat has a number, a class (e.g., economy, business), and a booking status.
 * <p>
 * This class is used to model individual seats on a {@link Flight} and track their availability.
 * A seat can be either booked or not booked.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see Flight
 * @see Booking
 */
public class Seat {
    /**
     * The unique number of the seat.
     */
    private int seatNumber;
    /**
     * The class of the seat (e.g., "economy", "business").
     */
    private String seatClass;
    /**
     * Indicates whether the seat is currently booked.
     */
    private boolean isBooked;

    /**
     * Constructs a {@code Seat} object with a seat number and seat class.
     * Initially, a seat is not booked.
     *
     * @param seatNumber The number of the seat.
     * @param seatClass  The class of the seat (e.g., "economy", "business").
     */
    public Seat(int seatNumber, String seatClass) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.isBooked = false;
    }

    /**
     * Gets the seat number.
     *
     * @return The seat number.
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Gets the seat class.
     *
     * @return The seat class (e.g., "economy", "business").
     */
    public String getSeatClass() {
        return seatClass;
    }

    /**
     * Checks if the seat is booked.
     *
     * @return {@code true} if the seat is booked, {@code false} otherwise.
     */
    public boolean isBooked() {
        return isBooked;
    }

    /**
     * Sets the booking status of the seat.
     *
     * @param booked {@code true} to mark the seat as booked, {@code false} to mark as not booked.
     */
    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}