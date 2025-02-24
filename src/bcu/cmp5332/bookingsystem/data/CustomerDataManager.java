package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * {@code CustomerDataManager} class is responsible for loading and saving customer data
 * to and from persistent storage, specifically a text file.
 * It implements the {@link DataManager} interface for handling customer-related data operations
 * within the flight booking system.
 * <p>
 * This class manages the persistence of {@link Customer} objects, ensuring that customer information
 * is preserved across application sessions. It reads customer details from a predefined file
 * upon loading and writes the current customer state back to the same file when saving data.
 * </p>
 * <p>
 * The data is stored in a text file format where each line represents a customer, and fields are
 * delimited by a specific delimiter ("::").
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see DataManager
 * @see FlightBookingSystem
 * @see Customer
 */
public class CustomerDataManager implements DataManager {

    /**
     * The file path where customer data is stored.
     * It is a relative path to the "customers.txt" file within the "resources/data" directory.
     */
    private static final String CUSTOMERS_FILE_PATH = "resources/data/customers.txt";

    /**
     * Loads customer data from the {@value #CUSTOMERS_FILE_PATH} file into the {@link FlightBookingSystem}.
     * <p>
     * This method reads each line from the customer data file, parses the customer details,
     * and creates {@link Customer} objects. It then adds these customers to the provided
     * {@link FlightBookingSystem}. The method handles potential {@link IOException} during file reading
     * and {@link NumberFormatException} during data parsing. Malformed lines in the data file are skipped,
     * and errors are logged to the standard error stream. If the file is not found ({@link FileNotFoundException}),
     * it is assumed that there is no existing data, and the system starts with an empty customer list.
     * </p>
     *
     * @param fbs The {@link FlightBookingSystem} to which the loaded customers should be added.
     * @throws IOException If an I/O error occurs while reading from the customer data file.
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMERS_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("::");
                if (values.length != 6) { //Changed length to 6 because you added isDeleted.
                    continue; // Skip malformed lines
                }
                try {
                    int id = Integer.parseInt(values[0]);
                    String name = values[1];
                    String phone = values[2];
                    String email = values[3];
                    String specialRequests = values[4];
                    boolean isDeleted = Boolean.parseBoolean(values[5]); //Getting if the customer is deleted.

                    Customer customer = new Customer(id, name, phone, email, specialRequests);
                    customer.setDeleted(isDeleted); //Setting if customer is deleted or not.

                    fbs.addCustomer(customer);
                    if (id >= fbs.getNextCustomerId()) {
                        fbs.setNextCustomerId(id + 1);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing customer data: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            //If file is not found, do nothing
            //This is to allow the system to start with no data
        }
    }

    /**
     * Saves customer data from the {@link FlightBookingSystem} to the {@value #CUSTOMERS_FILE_PATH} file.
     * <p>
     * This method iterates through all customers in the provided {@link FlightBookingSystem} and writes
     * each customer's details to a new line in the customer data file. The customer attributes are formatted
     * as a string with fields separated by the delimiter ("::"). The method uses a {@link BufferedWriter}
     * for efficient writing and handles potential {@link IOException} during file writing.
     * </p>
     *
     * @param fbs The {@link FlightBookingSystem} from which to save the customer data.
     * @throws IOException If an I/O error occurs while writing to the customer data file.
     */
    @Override
    public void saveData(FlightBookingSystem fbs) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMERS_FILE_PATH))) {
            for (Customer customer : fbs.getCustomers()) {
                String line = customer.getId() + "::" +
                        customer.getName() + "::" +
                        customer.getPhone() + "::" +
                        customer.getEmail() + "::" +
                        customer.getSpecialRequests() + "::" +
                        customer.isDeleted(); // added isDeleted to the storage
                bw.write(line);
                bw.newLine();
            }
        }
    }
}