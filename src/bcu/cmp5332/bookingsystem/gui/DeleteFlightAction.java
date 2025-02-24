package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.io.IOException;

/**
 * {@code DeleteFlightAction} class provides a static method to handle the deletion of a flight
 * from the flight booking system through a graphical user interface.
 * <p>
 * This class is designed as a utility, offering a single static method to encapsulate the action
 * of deleting a flight. The deletion process involves prompting the user for a flight ID,
 * retrieving the corresponding flight, marking it as deleted, and persisting these changes.
 * </p>
 * <p>
 * It's important to note that the deletion implemented in this class is a soft deletion.
 * Flights are not permanently removed from the system's data storage but are marked as deleted,
 * which typically means they are no longer considered active or available for booking.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see FlightBookingSystem
 * @see FlightBookingSystemData
 * @see bcu.cmp5332.bookingsystem.model.Flight
 */
public class DeleteFlightAction {

    /**
     * Initiates the process of deleting a flight from the flight booking system.
     * <p>
     * This method presents an input dialog to the user, prompting for the Flight ID of the flight to be deleted.
     * Upon receiving a valid flight ID, it calls the {@link FlightBookingSystem#deleteFlight(int)} method
     * to mark the flight as deleted within the {@link FlightBookingSystem}. Subsequently, it persists
     * the updated system state using {@link FlightBookingSystemData#store(FlightBookingSystem)}.
     * </p>
     * <p>
     * After attempting to delete the flight and store the data, the method provides feedback to the user
     * through {@link JOptionPane} messages, indicating either successful deletion or any errors encountered,
     * such as an invalid Flight ID format or issues during data saving.
     * </p>
     *
     * @param fbs    The {@link FlightBookingSystem} instance on which to perform the deletion.
     * @param data   The {@link FlightBookingSystemData} instance used for persisting data changes.
     * @param parent The parent {@link JFrame} for displaying dialogs, providing context and positioning for UI elements.
     */
    public static void deleteFlight(FlightBookingSystem fbs, FlightBookingSystemData data, JFrame parent) {
        String flightIdStr = JOptionPane.showInputDialog(parent, "Enter Flight ID to delete:");
        if (flightIdStr != null && !flightIdStr.isEmpty()) {
            try {
                int flightId = Integer.parseInt(flightIdStr);
                fbs.deleteFlight(flightId);
                data.store(fbs);
                JOptionPane.showMessageDialog(parent, "Flight deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, "Invalid Flight ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}