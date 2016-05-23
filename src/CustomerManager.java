import java.util.ArrayList;
import java.util.List;


public class CustomerManager {
	private List<Customer> customers;
	private static CustomerManager customerManager = null;

	private CustomerManager() {
		this.customers = new ArrayList<Customer>();
	}
	
	public static CustomerManager getInstance()
	{
		if(customerManager == null)
		{
			customerManager = new CustomerManager();
		}
		return customerManager;
	}

	public Customer findCustomer(String name) {
		Customer foundCustomer = null ;
		for ( Customer customer: customers ) {
			if ( customer.getName().equals(name)) {
				foundCustomer = customer ;
				break ;
			}
		}
		return foundCustomer;
	}

	public void printCustomerList() {
		System.out.println("List of customers");
		
		for ( Customer customer: customers ) 
		{
			RentalManager.getInstance().printRental(customer);
		}
		System.out.println("End of list");
	}

	public void registerCustomer(String name) {
		Customer customer = new Customer(name) ;
		customers.add(customer) ;
	}
}