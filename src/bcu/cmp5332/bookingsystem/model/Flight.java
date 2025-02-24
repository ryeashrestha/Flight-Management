package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * {@code Flight} class represents an airline flight in the booking system.
 * It encapsulates all the details of a flight, including flight number, destination, origin,
 * departure date, capacity, price, duration, meal costs, and associated bookings.
 * <p>
 * Each flight is uniquely identified by an ID and can have multiple bookings. Flights can also be
 * marked as deleted, which is a soft deletion mechanism. The class includes dynamic pricing logic
 * based on the time to departure and seat availability.
 * </p>
 * <p>
 * It also manages a waiting list for customers when the flight is fully booked and provides methods
 * to calculate available seats and check if a flight has departed.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see Booking
 * @see Customer
 */
public class Flight {
    /**
     * Unique identifier for the flight.
     */
    private int id;
    /**
     * The flight number (e.g., "BA249").
     */
    private String flightNumber;
    /**
     * The destination airport or city of the flight.
     */
    private String destination;
    /**
     * The origin airport or city of the flight.
     */
    private String origin;
    /**
     * The departure date of the flight in "yyyy-MM-dd" format.
     */
    private String departureDate;
    /**
     * The total passenger capacity of the flight.
     */
    private int capacity;
    /**
     * The base price of a flight ticket before dynamic pricing adjustments.
     */
    private double basePrice;
    /**
     * The duration of the flight (e.g., "2h 30m").
     */
    private String duration;
    /**
     * Flag indicating if the flight is marked as deleted (soft deletion).
     */
    private boolean isDeleted;
    /**
     * The cost of a vegetarian meal on this flight.
     */
    private double vegMealCost;
    /**
     * The cost of a non-vegetarian meal on this flight.
     */
    private double nonVegMealCost;

    /**
     * List of {@link Booking} objects associated with this flight.
     */
    private List<Booking> bookings;
    /**
     * List of {@link Customer} objects on the waiting list for this flight.
     */
    private List<Customer> waitingList; // New

    /**
     * Constructs a {@code Flight} object with the specified details.
     *
     * @param id              The unique ID of the flight.
     * @param flightNumber    The flight number.
     * @param destination     The destination airport or city.
     * @param origin          The origin airport or city.
     * @param departureDate   The departure date in "yyyy-MM-dd" format.
     * @param capacity        The passenger capacity of the flight.
     * @param basePrice       The base price of a ticket for this flight.
     * @param duration        The duration of the flight.
     * @param vegMealCost     The cost of a vegetarian meal.
     * @param nonVegMealCost  The cost of a non-vegetarian meal.
     */
    public Flight(int id, String flightNumber, String destination, String origin, String departureDate, int capacity, double basePrice, String duration, double vegMealCost, double nonVegMealCost) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.origin = origin;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.basePrice = basePrice;
        this.duration = duration;
        this.vegMealCost = vegMealCost;
        this.nonVegMealCost = nonVegMealCost;
        this.bookings = new ArrayList<>();
        this.waitingList = new ArrayList<>(); //New
        this.isDeleted = false;
    }

    /**
     * Gets the unique identifier of the flight.
     *
     * @return The flight ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the flight number.
     *
     * @return The flight number.
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Sets the flight number.
     *
     * @param flightNumber The flight number to set.
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * Gets the destination of the flight.
     *
     * @return The destination airport or city.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the destination of the flight.
     *
     * @param destination The destination airport or city to set.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets the origin of the flight.
     *
     * @return The origin airport or city.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the origin of the flight.
     *
     * @param origin The origin airport or city to set.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Gets the departure date of the flight as a string in "yyyy-MM-dd" format.
     *
     * @return The departure date string.
     */
    public String getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the departure date of the flight.
     *
     * @param departureDate The departure date to set, in "yyyy-MM-dd" format.
     */
    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Gets the capacity of the flight.
     *
     * @return The passenger capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the flight.
     *
     * @param capacity The passenger capacity to set.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the base price of the flight ticket.
     *
     * @return The base price.
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Sets the base price of the flight ticket.
     *
     * @param basePrice The base price to set.
     */
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * Gets the duration of the flight.
     *
     * @return The flight duration string.
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the flight.
     *
     * @param duration The flight duration to set.
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Gets the list of bookings associated with this flight.
     *
     * @return A list of {@link Booking} objects.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Adds a booking to the list of bookings for this flight.
     *
     * @param booking The {@link Booking} object to add.
     */
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    /**
     * Removes a booking from the list of bookings for this flight.
     *
     * @param booking The {@link Booking} object to remove.
     */
    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }

    /**
     * Calculates and returns the number of available seats on the flight.
     *
     * @return The number of available seats.
     */
    public int getAvailableSeats() {
        return capacity - bookings.size();
    }

     /**
      * Checks if the flight is marked as deleted.
      *
      * @return {@code true} if the flight is deleted, {@code false} otherwise.
      */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Sets the deletion status of the flight.
     *
     * @param deleted {@code true} to mark the flight as deleted, {@code false} to mark as active.
     */
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    /**
     * Gets the dynamically calculated price of the flight ticket.
     * Price is adjusted based on days to departure and seat availability.
     *
     * @return The dynamic price of the flight ticket.
     */
    public double getPrice() {
         try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate departure = LocalDate.parse(this.departureDate, formatter);
            LocalDate now = LocalDate.now();
            long daysToDeparture = ChronoUnit.DAYS.between(now, departure);

            double price = basePrice;

            // Price increases as departure date approaches
            if (daysToDeparture < 7) {
                price *= 1.5; // 50% increase if less than 7 days
            } else if (daysToDeparture < 30) {
                price *= 1.2; // 20% increase if less than 30 days
            }

            // Price increases as fewer seats are available
            double seatRatio = (double) getAvailableSeats() / capacity;
            if (seatRatio < 0.2) {
                price *= 1.8; // 80% increase if less than 20% seats
            } else if (seatRatio < 0.5) {
                price *= 1.3; // 30% increase if less than 50% seats
            }

            return price;

        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + this.departureDate);
            return basePrice; // Return base price in case of error
        }
    }

    /**
     * Returns a string representation of the {@code Flight} object.
     * Includes flight ID, flight number, destination, origin, departure date, capacity, price, and duration.
     *
     * @return A string representation of the flight.
     */
    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", destination='" + destination + '\'' +
                ", origin='" + origin + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", capacity=" + capacity +
                ", price=" + basePrice +
                ", duration='" + duration + '\'' +
                '}';
    }

    /**
     * Gets the cost of a vegetarian meal on this flight.
     *
     * @return The vegetarian meal cost.
     */
    public double getVegMealCost() {
        return vegMealCost;
    }

    /**
     * Sets the cost of a vegetarian meal on this flight.
     *
     * @param vegMealCost The vegetarian meal cost to set.
     */
    public void setVegMealCost(double vegMealCost) {
        this.vegMealCost = vegMealCost;
    }

    /**
     * Gets the cost of a non-vegetarian meal on this flight.
     *
     * @return The non-vegetarian meal cost.
     */
    public double getNonVegMealCost() {
        return nonVegMealCost;
    }

    /**
     * Sets the cost of a non-vegetarian meal on this flight.
     *
     * @param nonVegMealCost The non-vegetarian meal cost to set.
     */
    public void setNonVegMealCost(double nonVegMealCost) {
        this.nonVegMealCost = nonVegMealCost;
    }

    /**
     * Gets the waiting list of customers for this flight.
     *
     * @return A list of {@link Customer} objects on the waiting list.
     */
    public List<Customer> getWaitingList() {
        return waitingList;
    }

    /**
     * Adds a customer to the waiting list for this flight.
     *
     * @param customer The {@link Customer} object to add to the waiting list.
     */
    public void addWaitingList(Customer customer) {
         this.waitingList.add(customer);
    }

    /**
     * Removes a customer from the waiting list for this flight.
     *
     * @param customer The {@link Customer} object to remove from the waiting list.
     */
    public void removeWaitingList(Customer customer) {
         this.waitingList.remove(customer);
    }

    /**
     * Checks if the flight has departed based on its departure date.
     *
     * @return {@code true} if the departure date is in the past, {@code false} otherwise.
     */
    public boolean isDeparted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate departureDate = LocalDate.parse(this.departureDate, formatter);
            return departureDate.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + this.departureDate);
            return false; // Assume not departed if date is invalid
        }
    }
}