package bcu.cmp5332.bookingsystem.data;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * {@code BookingDataManager} class is responsible for loading and saving booking data
 * to and from a persistent storage, specifically a text file.
 * It implements the {@link DataManager} interface for handling booking-related data operations
 * within the flight booking system.
 * <p>
 * This class manages the persistence of {@link Booking} objects, ensuring that booking information
 * is preserved across application sessions. It reads booking details from a predefined file
 * upon loading and writes the current booking state back to the same file when saving data.
 * </p>
 * <p>
 * The data is stored in a text file format where each line represents a booking, and fields are
 * delimited by a specific delimiter ({@value #DELIMITER}).
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see DataManager
 * @see FlightBookingSystem
 * @see Booking
 */
public class BookingDataManager implements DataManager {
    /**
     * The file path where booking data is stored.
     * It is a relative path to the "bookings.txt" file within the "resources/data" directory.
     */
    private static final String BOOKINGS_FILE_PATH = "resources/data/bookings.txt";
    /**
     * The delimiter used to separate fields in the booking data file.
     * It is defined as "::" to ensure clear separation between different booking attributes.
     */
    private static final String DELIMITER = "::"; // Define the new delimiter

    /**
     * Loads booking data from the {@value #BOOKINGS_FILE_PATH} file into the {@link FlightBookingSystem}.
     * <p>
     * This method reads each line from the booking data file, parses the booking details,
     * and creates {@link Booking} objects. It then adds these bookings to the provided
     * {@link FlightBookingSystem}. The method handles potential {@link IOException} during file reading
     * and {@link NumberFormatException} during data parsing. Malformed lines in the data file are skipped,
     * and errors are logged to the standard error stream. If the file is not found ({@link FileNotFoundException}),
     * it is assumed that there is no existing data, and the system starts with an empty booking list.
     * </p>
     *
     * @param fbs The {@link FlightBookingSystem} to which the loaded bookings should be added.
     * @throws IOException If an I/O error occurs while reading from the booking data file.
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKINGS_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMITER); // Use "::" delimiter
                if (values.length != 7) { // Expecting 7 values
                    continue; // Skip malformed lines
                }
                try {
                    int id = Integer.parseInt(values[0]);
                    int customerId = Integer.parseInt(values[1]);
                    int flightId = Integer.parseInt(values[2]);
                    boolean isCancelled = Boolean.parseBoolean(values[3]);
                    String seatClass = values[4];
                    String mealPreference = values[5];
                    int numberOfBags = Integer.parseInt(values[6]); // New
                    Customer customer = fbs.getCustomerById(customerId);
                    Flight flight = fbs.getFlightById(flightId);

                    if (customer == null || flight == null) {
                        System.err.println("Invalid customer or flight ID in booking data: " + line);
                        continue;
                    }

                    Booking booking = new Booking(id, customer, flight, seatClass, mealPreference, 1, numberOfBags); // Update Booking
                    booking.setCancelled(isCancelled);

                    fbs.addBooking(customer, flight, seatClass, mealPreference, numberOfBags); // ADD THIS CODE

                    if (id >= fbs.getNextBookingId()) {
                        fbs.setNextBookingId(id + 1);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing booking data: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            // If file is not found, do nothing
            // This is to allow the system to start with no data
        }
    }

    /**
     * Saves booking data from the {@link FlightBookingSystem} to the {@value #BOOKINGS_FILE_PATH} file.
     * <p>
     * This method iterates through all bookings in the provided {@link FlightBookingSystem} and writes
     * each booking's details to a new line in the booking data file. The booking attributes are formatted
     * as a string with fields separated by the {@value #DELIMITER}. The method uses a {@link BufferedWriter}
     * for efficient writing and handles potential {@link IOException} during file writing.
     * </p>
     *
     * @param fbs The {@link FlightBookingSystem} from which to save the booking data.
     * @throws IOException If an I/O error occurs while writing to the booking data file.
     */
    @Override
    public void saveData(FlightBookingSystem fbs) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKINGS_FILE_PATH))) {
            for (Booking booking : fbs.getBookings()) {
                String line = booking.getId() + DELIMITER +
                        booking.getCustomer().getId() + DELIMITER +
                        booking.getFlight().getId() + DELIMITER +
                        booking.isCancelled() + DELIMITER +
                        booking.getSeatClass() + DELIMITER +
                        booking.getMealPreference() + DELIMITER +
                        booking.getNumberOfBags(); // Update bags
                bw.write(line);
                bw.newLine();
            }
        }
    }
}