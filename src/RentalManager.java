import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentalManager {
	
	private static RentalManager rentalManager = null;
	
	private Map<Customer, List<Rental>> rentals = null;
	
	private RentalManager()
	{
		rentals = new HashMap<Customer, List<Rental>>();
	}
	
	public static RentalManager getInstance()
	{
		if(rentalManager == null)
		{
			rentalManager = new RentalManager();
		}
		return rentalManager;
	}

	public void rentVideo(Customer customer, Video video) {
		Rental rental = new Rental(video) ;
		
		video.setRented(true);
		
		List<Rental> customerRentals = rentals.get(customer);
		if(customerRentals == null)
		{
			customerRentals = new ArrayList<Rental>();
			rentals.put(customer, customerRentals);
		}
		
		customerRentals.add(rental);
		
		rentals.put(customer, customerRentals);
	}

	public void returnVideo(Customer customer, Video video) {
		List<Rental> customerRentals = rentals.get(customer);
		if(customerRentals == null)
		{
			return;
		}
		
		for ( Rental rental: customerRentals ) {
			if ( isRentedVideo(video, rental) ) {
				rental.returnVideo();
				rental.getVideo().setRented(false);
				break ;
			}
		}
	}

	private boolean isRentedVideo(Video video, Rental rental) {
		return rental.getVideo().getTitle().equals(video.getTitle()) && rental.getVideo().isRented();
	}

	public void clearRentals(Customer customer) {		
		rentals.put(customer, new ArrayList<Rental>());
	}

	public void printRental(Customer customer) {
		List<Rental> customerRentals = getCustomerRentals(customer);
		System.out.println("Name: " + customer.getName() +
				"\tRentals: " + customerRentals.size()) ;
		for ( Rental rental: customerRentals ) {
			System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
			System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
		}
	}

	private List<Rental> getCustomerRentals(Customer customer) {
		List<Rental> customerRentals = rentals.get(customer);
		if(customerRentals == null)
		{
			customerRentals = new ArrayList<Rental>();
		}
		return customerRentals;
	}
	
	public String getCustomerReport(Customer customer)
	{
		StringBuffer result = new StringBuffer("");

		writeReportTitleTo(result, customer.getName());

		List<Rental> customerRentals = getCustomerRentals(customer);

		double totalCharge = 0;
		int totalPoint = 0;

		for (Rental eachRental : customerRentals) {
			double eachCharge = 0;
			int daysRented = 0;
			int eachPoint = 0;
			
			daysRented = eachRental.getRentedDays();
			eachPoint= eachRental.getPoint(daysRented);
			eachCharge = eachRental.getCharge(daysRented);	

			writeReportInfoTo(result, eachRental, eachCharge, eachPoint, daysRented);

			totalCharge += eachCharge;
			totalPoint += eachPoint;
		}
		
		writeReportSummaryTo(result, totalCharge, totalPoint);
		
		issueCoupon(totalPoint);
		return result.toString();
	}
	
	private void issueCoupon(int point) {
		if (point >= 10) {
			System.out.println("Congrat! You earned one free coupon");
		}
		
		if (point >= 30) {
			System.out.println("Congrat! You earned two free coupon");
		}
	}

	private void writeReportSummaryTo(StringBuffer result, double totalCharge, int totalPoint) {
		result.append("Total charge: " + totalCharge 
				    + "\tTotal Point:" + totalPoint + "\n");
	}

	private void writeReportInfoTo(StringBuffer result, Rental each,
			double eachCharge, int eachPoint, int daysRented) {
		result.append("\t" + each.getVideo().getTitle() 
				+ "\tDays rented: "
				+ daysRented + "\tCharge: " 
				+ eachCharge + "\tPoint: "
				+ eachPoint + "\n");
	}

	private void writeReportTitleTo(StringBuffer result, String customerName) {
		result.append("Customer Report for " + customerName + "\n");
	}
}
