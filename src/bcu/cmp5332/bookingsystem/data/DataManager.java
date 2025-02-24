package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;

/**
 * {@code DataManager} interface defines the contract for classes that handle data persistence
 * for the flight booking system. Implementations of this interface are responsible for loading
 * data into and saving data from a {@link FlightBookingSystem}.
 * <p>
 * This interface provides a level of abstraction for data storage, allowing different
 * persistence mechanisms (e.g., file system, database, network storage) to be used without
 * changing the core logic of the booking system.
 * </p>
 * <p>
 * Implementations are expected to handle {@link IOException} if there are issues during data
 * loading or saving, and to manage the serialization and deserialization of data as needed.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see FlightBookingSystem
 * @see IOException
 */
public interface DataManager {
    /**
     * Loads data into the provided {@link FlightBookingSystem}.
     * This method is responsible for retrieving persistent data from a storage medium
     * and populating the {@code FlightBookingSystem} with this data.
     *
     * @param fbs The {@link FlightBookingSystem} instance to load data into.
     * @throws IOException If an I/O error occurs during data loading, such as issues accessing
     *                     the storage medium, reading data, or parsing data.
     */
    void loadData(FlightBookingSystem fbs) throws IOException;

    /**
     * Saves data from the provided {@link FlightBookingSystem} to persistent storage.
     * This method is responsible for taking the current state of the {@code FlightBookingSystem}
     * and writing it to a persistent storage medium, ensuring that data is preserved across
     * application sessions.
     *
     * @param fbs The {@link FlightBookingSystem} instance to save data from.
     * @throws IOException If an I/O error occurs during data saving, such as issues accessing
     *                     the storage medium, writing data, or serializing data.
     */
    void saveData(FlightBookingSystem fbs) throws IOException;
}