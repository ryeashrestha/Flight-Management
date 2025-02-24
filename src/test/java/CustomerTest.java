package test.java;

import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@code CustomerTest} class is a JUnit test class designed to test the functionalities
 * related to {@link Customer} entities within the {@link FlightBookingSystem}.
 * <p>
 * This test class verifies the successful addition of customers to the system and the soft deletion
 * mechanism applied to customers. It uses the {@link AddCustomer} command to add new customers
 * and checks if the customer details are correctly stored and retrievable. Additionally, it tests
 * the functionality to mark a customer as deleted and verifies the deletion status.
 * </p>
 * <p>
 * The tests are designed to ensure the robustness and correctness of customer management operations
 * within the flight booking system.
 * </p>
 *
 * @author Sanket Shrestha, Riya Shrestha
 * @version 1.0
 * @see Customer
 * @see FlightBookingSystem
 * @see AddCustomer
 */
public class CustomerTest {
    private FlightBookingSystem fbs;
    private FlightBookingSystemData data;

    /**
     * {@code setUp} method is executed before each test case. It initializes a new instance of
     * {@link FlightBookingSystem} to ensure each test starts with a clean state.
     *
     * @throws Exception if any exception occurs during setup.
     */
    @BeforeEach
    public void setUp() {
        fbs = new FlightBookingSystem();
    }

    /**
     * {@code testAddCustomerSuccess} test method verifies the successful addition of a customer
     * to the flight booking system using the {@link AddCustomer} command.
     * <p>
     * It creates an {@link AddCustomer} command with valid customer details, executes it, and then asserts:
     * <ul>
     *     <li>The command execution returns the expected success message.</li>
     *     <li>The customer is successfully added to the {@link FlightBookingSystem}.</li>
     *     <li>The details of the added customer in the system match the details provided in the command.</li>
     * </ul>
     *
     * @throws FlightBookingSystemException if the execution of the command throws an exception, which is not expected in this success test case.
     */
    @Test
    public void testAddCustomerSuccess() throws FlightBookingSystemException {
        AddCustomer addCustomer = new AddCustomer("John Doe", "1234567890", "john@example.com", "Vegan meal");
        String result = addCustomer.execute(fbs);
        assertEquals("Customer John Doe added with ID 1", result);

        Customer customer = fbs.getCustomerById(1);
        assertNotNull(customer);
        assertEquals("John Doe", customer.getName());
        assertEquals("1234567890", customer.getPhone());
        assertEquals("john@example.com", customer.getEmail());
        assertEquals("Vegan meal", customer.getSpecialRequests());
    }

    /**
     * {@code testDeleteCustomerSuccess} test method verifies the successful soft deletion of a customer.
     * <p>
     * It first adds a customer to the system using {@link AddCustomer}, then retrieves the customer,
     * and simulates deletion by calling {@link Customer#setDeleted(boolean)} with {@code true}.
     * Finally, it asserts that the customer's {@link Customer#isDeleted()} method returns {@code true},
     * confirming that the customer has been successfully marked as deleted.
     * </p>
     *
     * @throws FlightBookingSystemException if the execution of the {@link AddCustomer} command throws an exception, which is not expected in this success test case.
     */
    @Test
    public void testDeleteCustomerSuccess() throws FlightBookingSystemException {
        AddCustomer addCustomer = new AddCustomer("Jane Doe", "0987654321", "jane@example.com", "Window seat");
        addCustomer.execute(fbs);

        Customer customer = fbs.getCustomerById(1);
        assertNotNull(customer);

        customer.setDeleted(true);
        assertTrue(customer.isDeleted());
    }
}