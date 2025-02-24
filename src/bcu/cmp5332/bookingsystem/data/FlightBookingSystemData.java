package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Customer;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code FlightBookingSystemData} class serves as a central data management component
 * for the Flight Booking System. It orchestrates the loading and saving of various data types
 * including flights, customers, and bookings using specialized {@link DataManager} implementations.
 * <p>
 * This class encapsulates the data persistence logic, providing a unified interface to load all system data
 * at startup and store all data upon application shutdown or when data changes need to be persisted.
 * It utilizes {@link FlightDataManager}, {@link CustomerDataManager}, and {@link BookingDataManager}
 * to handle the specifics of each data type's persistence.
 * </p>
 * <p>
 * Additionally, it provides methods to retrieve specific subsets of data, such as active and deleted customers,
 * by filtering the data managed by the {@link FlightBookingSystem}.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see FlightDataManager
 * @see CustomerDataManager
 * @see BookingDataManager
 * @see DataManager
 * @see FlightBookingSystem
 */
public class FlightBookingSystemData {
    /**
     * The directory where data files are stored.
     * Currently set to "resources/data/".
     */
    private final String dataDir = "resources/data/";
    /**
     * {@link FlightDataManager} instance responsible for managing flight data persistence.
     */
    private final FlightDataManager flightDataManager = new FlightDataManager();
    /**
     * {@link CustomerDataManager} instance responsible for managing customer data persistence.
     */
    private final CustomerDataManager customerDataManager = new CustomerDataManager();
    /**
     * {@link BookingDataManager} instance responsible for managing booking data persistence.
     */
    private final BookingDataManager bookingDataManager = new BookingDataManager();
    /**
     * A list to hold {@link Flight} objects.
     * It's populated after loading data from persistence.
     */
    private List<Flight> flights;
    /**
     * Instance of {@link FlightBookingSystem} to manage the booking system data.
     */
    public FlightBookingSystem fbs; //Add FlightBookingSystem fbs into the constructor

    /**
     * Constructs a {@code FlightBookingSystemData} object, associating it with a specific
     * {@link FlightBookingSystem} instance.
     *
     * @param fbs The {@link FlightBookingSystem} instance that this data manager will handle data for.
     */
    public FlightBookingSystemData(FlightBookingSystem fbs) { //Create FlightBookingSystemData Object
        this.fbs = fbs; //Set fbs into FlightBookingSystemData
    }

    /**
     * Loads all system data (flights, customers, bookings) into the provided {@link FlightBookingSystem}.
     * <p>
     * This method delegates the data loading process to the respective {@link DataManager} instances
     * for flights, customers, and bookings. After loading, it updates the local {@code flights} list
     * to reflect the current state in the {@link FlightBookingSystem}.
     * </p>
     *
     * @param fbs The {@link FlightBookingSystem} instance to load data into.
     * @throws IOException If an I/O error occurs during data loading by any of the data managers.
     * @see FlightDataManager#loadData(FlightBookingSystem)
     * @see CustomerDataManager#loadData(FlightBookingSystem)
     * @see BookingDataManager#loadData(FlightBookingSystem)
     */
    public void load(FlightBookingSystem fbs) throws IOException {
        flightDataManager.loadData(fbs);
        customerDataManager.loadData(fbs);
        bookingDataManager.loadData(fbs);
        this.flights = fbs.getFlights();
    }

    /**
     * Stores all system data (flights, customers, bookings) from the provided {@link FlightBookingSystem}
     * to persistent storage.
     * <p>
     * This method delegates the data saving process to the respective {@link DataManager} instances
     * for flights, customers, and bookings. After saving, it updates the local {@code flights} list
     * to reflect the current state in the {@link FlightBookingSystem}.
     * </p>
     *
     * @param fbs The {@link FlightBookingSystem} instance to save data from.
     * @throws IOException If an I/O error occurs during data saving by any of the data managers.
     * @see FlightDataManager#saveData(FlightBookingSystem)
     * @see CustomerDataManager#saveData(FlightBookingSystem)
     * @see BookingDataManager#saveData(FlightBookingSystem)
     */
    public void store(FlightBookingSystem fbs) throws IOException {
        flightDataManager.saveData(fbs);
        customerDataManager.saveData(fbs);
        bookingDataManager.saveData(fbs);
        this.flights = fbs.getFlights();
    }

    /**
     * Retrieves a list of active customers from the {@link FlightBookingSystem}.
     * <p>
     * Active customers are defined as those who are not marked as deleted. This method filters
     * the list of all customers in the {@link FlightBookingSystem} to include only those who are not deleted.
     * </p>
     *
     * @return A list of {@link Customer} objects that are currently active in the system.
     */
    public List<Customer> getActiveCustomers() {
        return fbs.getCustomers().stream()
                .filter(customer -> !customer.isDeleted())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of deleted customers from the {@link FlightBookingSystem}.
     * <p>
     * Deleted customers are those who have been marked as deleted in the system. This method filters
     * the list of all customers in the {@link FlightBookingSystem} to include only those who are deleted.
     * </p>
     *
     * @return A list of {@link Customer} objects that are marked as deleted in the system.
     */
    public List<Customer> getDeletedCustomers() {
        return fbs.getCustomers().stream()
                .filter(customer -> customer.isDeleted())
                .collect(Collectors.toList());
    }
}