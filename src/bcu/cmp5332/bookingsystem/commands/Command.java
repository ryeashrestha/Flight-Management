package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * {@code Command} interface defines the contract for executable commands within the flight booking system.
 * Implementing classes represent specific actions or operations that can be performed on a
 * {@link FlightBookingSystem}. This pattern is used to encapsulate actions as objects, allowing for
 * parameterization, queuing, logging, and undoable operations.
 * <p>
 * Each command implementation should focus on a single specific action, such as adding a flight,
 * adding a customer, making a booking, or any other operation within the booking system.
 * </p>
 * <p>
 * Implementations must provide an {@link #execute(FlightBookingSystem)} method that performs the
 * command's action within the context of a given {@link FlightBookingSystem} instance.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see FlightBookingSystem
 * @see FlightBookingSystemException
 */
public interface Command {
    /**
     * Executes the command within the context of the provided flight booking system.
     * This method is responsible for performing the action associated with the command,
     * potentially modifying the state of the {@link FlightBookingSystem}.
     *
     * @param flightBookingSystem The {@link FlightBookingSystem} on which the command is to be executed.
     * @return A string message indicating the result or outcome of the command execution.
     *         This could be a success message, a summary of changes, or any relevant information.
     * @throws FlightBookingSystemException If an error occurs during the execution of the command.
     *                                      This could be due to business logic violations, data inconsistencies,
     *                                      or any other exceptional condition encountered during command processing.
     */
    String execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException;
}