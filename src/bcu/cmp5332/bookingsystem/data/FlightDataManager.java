package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code FlightDataManager} class is responsible for loading and saving flight data
 * to and from persistent storage, specifically text files.
 * It implements the {@link DataManager} interface for handling flight-related data operations
 * within the flight booking system.
 * <p>
 * This class manages the persistence of {@link Flight} objects, differentiating between active
 * and deleted flights by storing them in separate files. It reads flight details from predefined files
 * upon loading and writes the current flight state back to these files when saving data.
 * </p>
 * <p>
 * Active flights are stored in {@value #FLIGHTS_FILE_PATH}, while deleted flights are stored in
 * {@value #DELETED_FLIGHTS_FILE_PATH}. The data in each file is formatted with fields delimited
 * by "::".
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see DataManager
 * @see FlightBookingSystem
 * @see Flight
 */
public class FlightDataManager implements DataManager {

    /**
     * The file path for storing active flight data.
     * It is a relative path to the "flights.txt" file within the "resources/data" directory.
     */
    private static final String FLIGHTS_FILE_PATH = "resources/data/flights.txt";
    /**
     * The file path for storing deleted flight data.
     * It is a relative path to the "deletedFlights.txt" file within the "resources/data" directory.
     */
    private static final String DELETED_FLIGHTS_FILE_PATH = "resources/data/deletedFlights.txt";

    /**
     * Loads flight data from both active and deleted flight files into the {@link FlightBookingSystem}.
     * <p>
     * This method loads active flights from {@value #FLIGHTS_FILE_PATH} and deleted flights from
     * {@value #DELETED_FLIGHTS_FILE_PATH}. It uses the helper method {@link #loadFlightsFromFile(FlightBookingSystem, String, boolean)}
     * to perform the actual loading from each file.
     * </p>
     *
     * @param fbs The {@link FlightBookingSystem} to which the loaded flights should be added.
     * @throws IOException If an I/O error occurs while reading from either of the flight data files.
     * @see #loadFlightsFromFile(FlightBookingSystem, String, boolean)
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException {
        loadFlightsFromFile(fbs, FLIGHTS_FILE_PATH, false);
        loadFlightsFromFile(fbs, DELETED_FLIGHTS_FILE_PATH, true);
    }

    /**
     * Helper method to load flight data from a specified file into the {@link FlightBookingSystem}.
     * <p>
     * This method reads each line from the given file path, parses the flight details,
     * and creates {@link Flight} objects. It then adds these flights to the provided
     * {@link FlightBookingSystem}. The method handles potential {@link IOException} during file reading,
     * {@link NumberFormatException} during numeric data parsing, and {@link DateTimeParseException}
     * during date parsing. Malformed lines are skipped, and errors are logged to the standard error stream.
     * If the file is not found ({@link FileNotFoundException}), it is assumed that there is no existing data
     * for that category of flights (active or deleted), and the system continues without loading data from that file.
     * </p>
     *
     * @param fbs      The {@link FlightBookingSystem} to which the loaded flights should be added.
     * @param filePath The path to the file from which to load flight data.
     * @param isDeleted A boolean flag indicating whether the flights being loaded are deleted (true) or active (false).
     * @throws IOException If an I/O error occurs while reading from the specified flight data file.
     */
    private void loadFlightsFromFile(FlightBookingSystem fbs, String filePath, boolean isDeleted) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("::");
                if (values.length != 10) {
                    continue; // Skip malformed lines
                }
                try {
                    int id = Integer.parseInt(values[0]);
                    String flightNumber = values[1];
                    String destination = values[2];
                    String origin = values[3];
                    String departureDate = values[4];
                    int capacity = Integer.parseInt(values[5]);
                    double price = Double.parseDouble(values[6]);
                    String duration = values[7];
                    double vegMealCost = Double.parseDouble(values[8]);
                    double nonVegMealCost = Double.parseDouble(values[9]);

                    Flight flight = new Flight(id, flightNumber, destination, origin, departureDate, capacity, price, duration, vegMealCost, nonVegMealCost);
                    flight.setDeleted(isDeleted);
                    fbs.addFlight(flight);

                    if (id >= fbs.getNextFlightId()) {
                        fbs.setNextFlightId(id + 1);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing flight data: " + line);
                } catch (DateTimeParseException e) {
                    System.err.println("Error parsing date: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            // If file is not found, do nothing
            // This is to allow the system to start with no data
             if (!filePath.equals(FLIGHTS_FILE_PATH)) {
                System.err.println("Deleted flights file not found. Starting with empty list.");
            }
        }
    }

    /**
     * Saves flight data to both active and deleted flight files from the {@link FlightBookingSystem}.
     * <p>
     * This method saves active flights to {@value #FLIGHTS_FILE_PATH} and deleted flights to
     * {@value #DELETED_FLIGHTS_FILE_PATH}. It uses the helper method {@link #saveFlightsToFile(FlightBookingSystem, String, boolean)}
     * to perform the actual saving to each file.
     * </p>
     *
     * @param fbs The {@link FlightBookingSystem} from which to save the flight data.
     * @throws IOException If an I/O error occurs while writing to either of the flight data files.
     * @see #saveFlightsToFile(FlightBookingSystem, String, boolean)
     */
    @Override
    public void saveData(FlightBookingSystem fbs) throws IOException {
        saveFlightsToFile(fbs, FLIGHTS_FILE_PATH, false);
        saveFlightsToFile(fbs, DELETED_FLIGHTS_FILE_PATH, true);
    }

    /**
     * Helper method to save flight data to a specified file.
     * <p>
     * This method iterates through all flights in the provided {@link FlightBookingSystem} and writes
     * the details of flights that match the {@code isDeleted} criteria to the specified file path.
     * The flight attributes are formatted as a string with fields separated by "::".
     * The method uses a {@link BufferedWriter} for efficient writing and handles potential {@link IOException}
     * during file writing.
     * </p>
     *
     * @param fbs       The {@link FlightBookingSystem} from which to save the flight data.
     * @param filePath  The path to the file where flight data should be saved.
     * @param isDeleted A boolean flag indicating whether to save deleted flights (true) or active flights (false).
     * @throws IOException If an I/O error occurs while writing to the specified flight data file.
     */
    private void saveFlightsToFile(FlightBookingSystem fbs, String filePath, boolean isDeleted) throws IOException {
        List<Flight> flightsToSave = new ArrayList<>();
        for (Flight flight : fbs.getFlights()) {
            if (flight.isDeleted() == isDeleted) {
                flightsToSave.add(flight);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Flight flight : flightsToSave) {
                String line = flight.getId() + "::" +
                        flight.getFlightNumber() + "::" +
                        flight.getDestination() + "::" +
                        flight.getOrigin() + "::" +
                        flight.getDepartureDate() + "::" +
                        flight.getCapacity() + "::" +
                        flight.getBasePrice() + "::" +
                        flight.getDuration() + "::" +
                        flight.getVegMealCost() + "::" +
                        flight.getNonVegMealCost();
                bw.write(line);
                bw.newLine();
            }
        }
    }
}