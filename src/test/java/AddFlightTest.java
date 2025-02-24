package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * {@code AddFlightTest} class contains JUnit tests to verify the functionality
 * of the {@link AddFlight} command within the flight booking system.
 * <p>
 * This test class focuses on ensuring that flights can be successfully added to the system
 * and that the system correctly handles attempts to add flights with invalid data.
 * </p>
 */
public class AddFlightTest {

    private FlightBookingSystem flightBookingSystem;

    /**
     * {@code setUp} method is executed before each test case. It initializes a new instance of
     * {@link FlightBookingSystem} to ensure each test starts with a clean state.
     *
     * @throws Exception if any exception occurs during setup.
     */
    @BeforeEach
    public void setUp() {
        flightBookingSystem = new FlightBookingSystem();
    }

    /**
     * {@code testAddFlightSuccess} test method verifies the successful addition of a flight
     * to the flight booking system using the {@link AddFlight} command.
     * <p>
     * It creates an {@link AddFlight} command with valid flight details, executes it, and then asserts:
     * <ul>
     *     <li>The command execution returns the expected success message.</li>
     *     <li>The number of flights in the system increases to 1.</li>
     *     <li>The details of the added flight in the system match the details provided in the command.</li>
     * </ul>
     *
     * @throws FlightBookingSystemException if the execution of the command throws an exception, which is not expected in this success test case.
     */
    @Test
    public void testAddFlightSuccess() throws FlightBookingSystemException {
        AddFlight addFlight = new AddFlight(
                "AA123",
                "New York",
                "Los Angeles",
                "2025-12-25",
                150,
                500.00,
                "5h 30m",
                20.0,
                25.0
        );

        String result = addFlight.execute(flightBookingSystem);

        assertEquals("Flight AA123 added with ID 1", result);
        assertEquals(1, flightBookingSystem.getFlights().size());

        Flight flight = flightBookingSystem.getFlights().get(0);
        assertEquals("AA123", flight.getFlightNumber());
        assertEquals("New York", flight.getDestination());
        assertEquals("Los Angeles", flight.getOrigin());
        assertEquals("2025-12-25", flight.getDepartureDate());
        assertEquals(150, flight.getCapacity());
        assertEquals(500.00, flight.getBasePrice());
        assertEquals("5h 30m", flight.getDuration());
        assertEquals(20.0, flight.getVegMealCost());
        assertEquals(25.0, flight.getNonVegMealCost());
    }

    /**
     * {@code testAddFlightWithInvalidData} test method is intended to verify the system's behavior
     * when attempting to add a flight with invalid data. In its current implementation, it is set up
     * to attempt adding a flight with an empty flight number, which should be considered invalid.
     * <p>
     * However, the current implementation of this test method is empty and does not include any assertions
     * to validate the expected behavior (e.g., throwing a {@link FlightBookingSystemException}).
     * To make this test effective, it should be updated to include assertions that check for
     * exception throwing or any other expected error handling behavior when invalid data is provided.
     * </p>
     * <p>
     * In a complete test case, you would typically assert that executing the {@code AddFlight} command
     * with invalid data throws a {@link FlightBookingSystemException} and that the exception message
     * is as expected.
     * </p>
     *
     * <p><strong>Note: This test is currently incomplete and should be revised to include proper assertions.</strong></p>
     */
    @Test
    public void testAddFlightWithInvalidData() {
        AddFlight addFlight = new AddFlight(
                "",  // Invalid flight number
                "New York",
                "Los Angeles",
                "2025-12-25",
                150,
                500.00,
                "5h 30m",
                20.0,
                25.0
        );
        // Incomplete test: Add assertions to check for expected exception or error handling
    }
}