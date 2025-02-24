package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.util.List;

/**
 * {@code AddBooking} command is responsible for adding a new booking to the flight booking system.
 * It implements the {@link Command} interface and encapsulates the action of creating a booking
 * for a specific customer on a specific flight with given preferences like seat class, meal, and baggage.
 * <p>
 * The command first validates the existence of the customer and flight IDs provided.
 * If either the customer or flight is not found, it throws a {@link FlightBookingSystemException}.
 * Upon successful validation, it proceeds to add the booking to the {@link FlightBookingSystem}.
 * </p>
 * <p>
 * After adding the booking, it increments the next booking ID in the system to ensure unique IDs for subsequent bookings.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see Command
 * @see FlightBookingSystem
 * @see Booking
 * @see Customer
 * @see Flight
 * @see FlightBookingSystemException
 */
public class AddBooking implements Command {

    private int customerId;
    private int flightId;
    private String seatClass;
    private String mealPreference;
    private int numberOfBags;

    /**
     * Constructs an {@code AddBooking} command.
     *
     * @param customerId     The ID of the customer making the booking.
     * @param flightId       The ID of the flight being booked.
     * @param seatClass      The class of seat for the booking (e.g., "economy", "business").
     * @param mealPreference The preferred meal for the booking (e.g., "veg", "non-veg", "none").
     * @param numberOfBags   The number of bags for the booking.
     */
    public AddBooking(int customerId, int flightId, String seatClass, String mealPreference, int numberOfBags) {
        this.customerId = customerId;
        this.flightId = flightId;
        this.seatClass = seatClass;
        this.mealPreference = mealPreference;
        this.numberOfBags = numberOfBags;
    }

    /**
     * Executes the command to add a new booking to the flight booking system.
     * <p>
     * It retrieves the {@link Customer} and {@link Flight} objects using the provided IDs from the {@link FlightBookingSystem}.
     * If either the customer or flight is not found, it throws a {@link FlightBookingSystemException}.
     * Otherwise, it adds the booking to the system and increments the next booking ID.
     * </p>
     *
     * @param fbs The {@link FlightBookingSystem} to which the booking is to be added.
     * @return A success message indicating the booking creation.
     * @throws FlightBookingSystemException If the customer or flight with the given IDs is not found, or if any other error occurs during booking creation.
     */
    @Override
    public String execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Customer customer = fbs.getCustomerById(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        Flight flight = fbs.getFlightById(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }

        fbs.addBooking(customer,flight,seatClass,mealPreference,numberOfBags); //Implements here

        fbs.setNextBookingId(fbs.getNextBookingId() + 1);

        return "Booking created for customer " + customerId + " on flight " + flightId + ".\n";
    }
}