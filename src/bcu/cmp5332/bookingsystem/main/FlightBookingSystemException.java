package bcu.cmp5332.bookingsystem.main;

/**
 * {@code FlightBookingSystemException} is a custom exception class for the Flight Booking System.
 * It is used to indicate exceptional conditions that occur during the operation of the booking system.
 * This class extends {@link Exception} and provides a constructor to create exceptions with specific error messages.
 *
 * <p>
 * Instances of this exception are typically thrown when business logic rules are violated,
 * data integrity is compromised, or any other error condition specific to the flight booking system arises.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 */
public class FlightBookingSystemException extends Exception {

    /**
     * Constructs a new {@code FlightBookingSystemException} with the specified detail message.
     * The message is saved for later retrieval by the {@link #getMessage()} method.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public FlightBookingSystemException(String message) {
        super(message);
    }
}