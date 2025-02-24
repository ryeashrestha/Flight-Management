package test.java;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

/**
 * {@code AddBookingTest} class is a JUnit test class designed to test the functionality
 * of the {@link AddBooking} command within the flight booking system.
 * It focuses on verifying the successful execution of adding bookings, handling scenarios
 * with multiple bookings, and correctly managing exceptions when customers or flights are not found.
 * <p>
 * This test class sets up a controlled environment before each test using the {@link BeforeEach} annotation,
 * ensuring that each test starts with a fresh {@link FlightBookingSystem} instance and predefined test data.
 * </p>
 */
public class AddBookingTest {

    private FlightBookingSystem fbs;

    /**
     * {@code setUp} method is executed before each test case. It initializes a new instance of
     * {@link FlightBookingSystem} and sets up a default {@link Customer} and {@link Flight}
     * within the system to be used for testing booking operations.
     *
     * @throws Exception if any exception occurs during setup.
     */
    @BeforeEach
    public void setUp() {
        fbs = new FlightBookingSystem();
        // Setting up a customer and flight for testing
        Customer customer = new Customer(1, "John Doe", "johndoe@example.com", null, null);

        // Update: Set available seats to a positive number
        Flight flight = new Flight(1, "AA123", "New York", "London", "2025-12-01 10:00", 10, 0, null, 0, 0);

        fbs.addCustomer(customer);
        fbs.addFlight(flight);
    }

    /**
     * {@code testExecuteSuccess} test method verifies the successful execution of the {@link AddBooking} command.
     * It creates a booking for an existing customer and flight and asserts that the booking is successfully
     * added to the flight booking system. It checks the returned success message and verifies the details
     * of the created booking, such as customer and flight IDs, seat class, and meal preference.
     *
     * @throws FlightBookingSystemException if the execution of the command throws an exception, which is not expected in this success test case.
     */
    @Test
    public void testExecuteSuccess() throws FlightBookingSystemException {
        AddBooking addBooking = new AddBooking(1, 1, "Economy", "Veg", 2);
        String result = addBooking.execute(fbs);

        // Debug print
        System.out.println("In test, after execute, bookings size: " + fbs.getBookings().size());

        // Check the exact message returned
        assertTrue(result.contains("Booking created for customer 1 on flight 1."));

        // Verify the booking was added
        assertEquals(1, fbs.getBookings().size());

        // Optional: Check the contents of the booking
        Booking booking = fbs.getBookings().get(0);
        assertEquals(1, booking.getCustomer().getId());
        assertEquals(1, booking.getFlight().getId());
        assertEquals("Economy", booking.getSeatClass());
        assertEquals("Veg", booking.getMealPreference());
    }

    /**
     * {@code testExecuteMultipleBookings} test method checks the system's ability to handle multiple booking additions.
     * It adds two bookings for the same customer and flight but with different seat classes and meal preferences.
     * The test asserts that both bookings are added successfully and verifies the specific details of each booking
     * to ensure correct handling of concurrent booking operations.
     *
     * @throws FlightBookingSystemException if the execution of the command throws an exception, which is not expected in this success test case.
     */
    @Test
    public void testExecuteMultipleBookings() throws FlightBookingSystemException {
        AddBooking addBooking1 = new AddBooking(1, 1, "Economy", "Veg", 1);
        AddBooking addBooking2 = new AddBooking(1, 1, "Business", "Non-Veg", 2);

        addBooking1.execute(fbs);
        addBooking2.execute(fbs);

        // Verify multiple bookings were added
        assertEquals(2, fbs.getBookings().size());

        // Optional: Check the contents of each booking
        Booking booking1 = fbs.getBookings().get(0);
        Booking booking2 = fbs.getBookings().get(1);

        assertEquals("Economy", booking1.getSeatClass());
        assertEquals("Veg", booking1.getMealPreference());
        assertEquals("Business", booking2.getSeatClass());
        assertEquals("Non-Veg", booking2.getMealPreference());
    }

    /**
     * {@code testExecuteCustomerNotFound} test method validates the exception handling when attempting to add a booking
     * for a non-existent customer. It tries to execute {@link AddBooking} with a customer ID that does not exist in the system
     * and asserts that a {@link FlightBookingSystemException} is thrown with the correct error message indicating customer not found.
     */
    @Test
    public void testExecuteCustomerNotFound() {
        AddBooking addBooking = new AddBooking(99, 1, "Economy", "Veg", 2);

        Exception exception = assertThrows(FlightBookingSystemException.class, () -> {
            addBooking.execute(fbs);
        });

        String expectedMessage = "Customer with ID 99 not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * {@code testExecuteFlightNotFound} test method validates the exception handling when attempting to add a booking
     * for a non-existent flight. It tries to execute {@link AddBooking} with a flight ID that does not exist in the system
     * and asserts that a {@link FlightBookingSystemException} is thrown with the correct error message indicating flight not found.
     */
    @Test
    public void testExecuteFlightNotFound() {
        AddBooking addBooking = new AddBooking(1, 99, "Economy", "Veg", 2);

        Exception exception = assertThrows(FlightBookingSystemException.class, () -> {
            addBooking.execute(fbs);
        });

        String expectedMessage = "Flight with ID 99 not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}