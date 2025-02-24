package bcu.cmp5332.bookingsystem.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * {@code FlightBookingSystem} class is the core component of the flight booking system.
 * It manages collections of {@link Flight}s, {@link Customer}s, and {@link Booking}s,
 * providing functionalities to add, retrieve, modify, and manage these entities.
 * <p>
 * This class serves as a central point of interaction for the business logic of the booking system,
 * handling operations such as adding new flights and customers, creating bookings, cancelling bookings,
 * deleting flights and customers (soft delete), and providing various filtering and sorting options.
 * </p>
 * <p>
 * It also manages unique IDs for flights, customers, and bookings to ensure data integrity.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see Flight
 * @see Customer
 * @see Booking
 */
public class FlightBookingSystem {
    /**
     * List of all flights in the system.
     */
    private List<Flight> flights;
    /**
     * List of all customers in the system.
     */
    private List<Customer> customers;
    /**
     * List of all bookings in the system.
     */
    private List<Booking> bookings;
    /**
     * Counter for generating the next unique flight ID.
     */
    private int nextFlightId = 1;
    /**
     * Counter for generating the next unique customer ID.
     */
    private int nextCustomerId = 1;
    /**
     * Counter for generating the next unique booking ID.
     */
    private int nextBookingId = 1;

    /**
     * Constructs a new {@code FlightBookingSystem} instance.
     * Initializes empty lists for flights, customers, and bookings.
     * Sets initial values for nextFlightId, nextCustomerId, and nextBookingId to 1.
     */
    public FlightBookingSystem() {
        this.flights = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    /**
     * Gets the list of all flights in the system.
     *
     * @return A list of {@link Flight} objects.
     */
    public List<Flight> getFlights() {
        return flights;
    }

    /**
     * Gets the list of all customers in the system.
     *
     * @return A list of {@link Customer} objects.
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Gets the list of all bookings in the system.
     *
     * @return A list of {@link Booking} objects.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Adds a new flight to the system.
     *
     * @param flight The {@link Flight} object to add.
     */
    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    /**
     * Adds a new customer to the system.
     *
     * @param customer The {@link Customer} object to add.
     */
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    /**
     * Adds a new booking to the system.
     * <p>
     * This method creates a new {@link Booking} object, associates it with the given customer and flight,
     * and adds it to the system's bookings list, as well as to the customer's and flight's booking lists.
     * It also checks for flight availability before creating the booking. If the flight is full, it prints a message and returns null.
     * </p>
     *
     * @param customer       The {@link Customer} making the booking.
     * @param flight         The {@link Flight} to be booked.
     * @param seatClass      The seat class for the booking.
     * @param mealPreference The meal preference for the booking.
     * @param numberOfBags   The number of bags for the booking.
     * @return The newly created {@link Booking} object, or {@code null} if the flight is full.
     */
    public Booking addBooking(Customer customer, Flight flight, String seatClass, String mealPreference, int numberOfBags) {
         if (flight.getAvailableSeats() <= 0) {
            System.out.println("Flight is full. Cannot book.");
            return null;
        }

        Booking booking = new Booking(nextBookingId++, customer, flight, seatClass, mealPreference, numberOfBags, 1);
        this.bookings.add(booking);
        customer.addBooking(booking);
        flight.addBooking(booking);
        return booking;
    }

    /**
     * Retrieves a flight by its unique ID.
     *
     * @param id The ID of the flight to retrieve.
     * @return The {@link Flight} object with the given ID, or {@code null} if not found.
     */
    public Flight getFlightById(int id) {
        for (Flight flight : flights) {
            if (flight.getId() == id) { //&& !flight.isDeleted()) removed code for efficiency, if i add code again I have to read from database
                return flight;
            }
        }
        return null;
    }

    /**
     * Retrieves a customer by their unique ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The {@link Customer} object with the given ID, or {@code null} if not found.
     */
    public Customer getCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) { //&& !customer.isDeleted()) Remove code, there were too many bugs.
                return customer;
            }
        }
        return null;
    }

    /**
     * Retrieves a booking by its unique ID.
     *
     * @param id The ID of the booking to retrieve.
     * @return The {@link Booking} object with the given ID, or {@code null} if not found.
     */
    public Booking getBookingById(int id) {
        for (Booking booking : bookings) {
            if (booking.getId() == id) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Cancels a booking given its ID.
     * <p>
     * This method marks a booking as cancelled. Further logic for cancellation fees and updating flight passenger count may be added here.
     * </p>
     *
     * @param bookingId The ID of the booking to cancel.
     */
    public void cancelBooking(int bookingId) {
        Booking booking = getBookingById(bookingId);
        if (booking != null) {
            booking.setCancelled(true);
            // Update Flight passenger count (you might need to add a method for this)
            // Add cancellation fees logic here (for 80%+)
        }
    }

    /**
     * Deletes a flight (soft delete) given its ID.
     * <p>
     * This method marks a flight as deleted without removing it from the system.
     * </p>
     *
     * @param flightId The ID of the flight to delete.
     */
    public void deleteFlight(int flightId) {
        Flight flight = getFlightById(flightId);
        if (flight != null) {
            flight.setDeleted(true);
        }
    }

    /**
     * Deletes a customer (soft delete) given their ID.
     * <p>
     * This method marks a customer as deleted without removing them from the system.
     * </p>
     *
     * @param customerId The ID of the customer to delete.
     */
    public void deleteCustomer(int customerId) {
        Customer customer = getCustomerById(customerId);
        if (customer != null) {
            customer.setDeleted(true);
        }
    }

    /**
     * Gets a list of active flights (non-deleted).
     *
     * @return A list of {@link Flight} objects that are not marked as deleted.
     */
    public List<Flight> getActiveFlights() {
        return flights.stream().filter(f -> !f.isDeleted()).collect(Collectors.toList());
    }

    /**
     * Gets a list of active customers (non-deleted).
     *
     * @return A list of {@link Customer} objects that are not marked as deleted.
     */
    public List<Customer> getActiveCustomers() {
       return customers.stream().filter(c -> !c.isDeleted()).collect(Collectors.toList());
    }

     /**
      * Gets a list of departed flights.
      *
      * @return A list of {@link Flight} objects that have departed (departure date is in the past).
      */
    public List<Flight> getDepartedFlights() {
        return flights.stream().filter(f -> f.isDeparted()).collect(Collectors.toList());
    }

    /**
     * Gets a list of future flights (not departed and not deleted).
     *
     * @return A list of {@link Flight} objects that are scheduled for the future and are not deleted.
     */
    public List<Flight> getFutureFlights() {
        return flights.stream().filter(f -> !f.isDeparted() && !f.isDeleted()).collect(Collectors.toList());
    }

    /**
     * Filters active flights by destination.
     *
     * @param destination The destination to filter by (case-insensitive).
     * @return A list of {@link Flight} objects that match the given destination.
     */
    public List<Flight> filterFlightsByDestination(String destination) {
        return getActiveFlights().stream()
                .filter(flight -> flight.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }

    /**
     * Filters active flights by price range.
     *
     * @param minPrice The minimum price of the range (inclusive).
     * @param maxPrice The maximum price of the range (inclusive).
     * @return A list of {@link Flight} objects within the specified price range.
     */
    public List<Flight> filterFlightsByPrice(double minPrice, double maxPrice) {
        return getActiveFlights().stream()
                .filter(flight -> flight.getPrice() >= minPrice && flight.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    /**
     * Filters active flights by airline code (flight number prefix).
     *
     * @param airline The airline code to filter by (e.g., "BA" for British Airways).
     * @return A list of {@link Flight} objects operated by the specified airline.
     */
    public List<Flight> filterFlightsByAirline(String airline) {
        return getActiveFlights().stream()
                .filter(flight -> flight.getFlightNumber().startsWith(airline))
                .collect(Collectors.toList());
    }

    /**
     * Sorts active flights by price in ascending order.
     *
     * @return A list of {@link Flight} objects sorted by price.
     */
    public List<Flight> sortFlightsByPrice() {
        return getActiveFlights().stream()
                .sorted(Comparator.comparing(Flight::getPrice))
                .collect(Collectors.toList());
    }

    /**
     * Sorts active customers by name in alphabetical order.
     *
     * @return A list of {@link Customer} objects sorted by name.
     */
    public List<Customer> sortCustomersByName() {
        return getActiveCustomers().stream()
                .sorted(Comparator.comparing(Customer::getName))
                .collect(Collectors.toList());
    }

    /**
     * Gets the next available flight ID.
     *
     * @return The next flight ID.
     */
    public int getNextFlightId() {
        return nextFlightId;
    }

    /**
     * Sets the next flight ID.
     * <p>
     * This method is primarily used when loading data from persistent storage to ensure correct ID sequence.
     * </p>
     *
     * @param nextFlightId The value to set as the next flight ID.
     */
    public void setNextFlightId(int nextFlightId) {
        this.nextFlightId = nextFlightId;
    }

    /**
     * Gets the next available customer ID.
     *
     * @return The next customer ID.
     */
    public int getNextCustomerId() {
        return nextCustomerId;
    }

    /**
     * Sets the next customer ID.
     * <p>
     * This method is primarily used when loading data from persistent storage to ensure correct ID sequence.
     * </p>
     *
     * @param nextCustomerId The value to set as the next customer ID.
     */
    public void setNextCustomerId(int nextCustomerId) {
        this.nextCustomerId = nextCustomerId;
    }

    /**
     * Gets the next available booking ID.
     *
     * @return The next booking ID.
     */
    public int getNextBookingId() {
        return nextBookingId;
    }

    /**
     * Sets the next booking ID.
     * <p>
     * This method is primarily used when loading data from persistent storage to ensure correct ID sequence.
     * </p>
     *
     * @param nextBookingId The value to set as the next booking ID.
     */
    public void setNextBookingId(int nextBookingId) {
        this.nextBookingId = nextBookingId;
    }

    /**
     * Helper method to get a string representation of a flight for display purposes.
     *
     * @param flight The {@link Flight} object.
     * @return A string representation of the flight including flight number, destination, and departure date.
     */
    public String getFlightDisplayString(Flight flight) {
        return flight.getFlightNumber() + " - " + flight.getDestination() + " (" + flight.getDepartureDate() + ")";
    }

    /**
     * Helper method to get a string representation of a customer for display purposes.
     *
     * @param customer The {@link Customer} object.
     * @return A string representation of the customer including name and ID.
     */
    public String getCustomerDisplayString(Customer customer) {
        return customer.getName() + " (ID: " + customer.getId() + ")";
    }
}