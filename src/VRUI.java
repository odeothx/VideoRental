import java.util.Date;
import java.util.Scanner;

public class VRUI {

	private static Scanner scanner = new Scanner(System.in) ;
	private CustomerManager customerManager = CustomerManager.getInstance();
	private VideoManager videoManager = VideoManager.getInstance();
	private RentalManager rentalManager = RentalManager.getInstance();

	public static void main(String[] args) {
		VRUI ui = new VRUI() ;
		
		boolean quit = false ;
		while ( ! quit ) {
			int command = ui.showCommand() ;
			switch ( command ) {
				case 0: quit = true ; break ;
				case 1: ui.customerManager.printCustomerList() ; break ;
				case 2: ui.videoManager.printVideoList() ; break ;
				case 3: ui.registerCustomer() ; break ;
				case 4: ui.registerVideo() ; break ;
				case 5: ui.rentVideo() ; break ;
				case 6: ui.returnVideo() ; break ;
				case 7: ui.getCustomerReport() ; break; 
				case 8: ui.clearRentals() ; break ;
				case -1: ui.init() ; break ;
				default: break ;
			}
		}
		System.out.println("Bye");
	}
	
	public void clearRentals() {
		String customerName = getCustomerName();
		
		Customer foundCustomer = customerManager.findCustomer(customerName);
		// Decompose conditional
		if ( foundCustomer == null ) {
			System.out.println("No customer found") ;
		}
		else {	
			rentalManager.printRental(foundCustomer);
			rentalManager.clearRentals(foundCustomer);
		}
	}

	public void returnVideo() {
		String customerName = getCustomerName();
		
		Customer foundCustomer = customerManager.findCustomer(customerName);
		if ( foundCustomer == null ) 
			return ;
		
		String returnVideoTitle = getReturnVideoTitle();		
		Video foundVideo = videoManager.findVideo(returnVideoTitle);
		
		if ( foundVideo == null ) 
			return ;
			
		rentalManager.returnVideo(foundCustomer, foundVideo);		
	}

	private String getReturnVideoTitle() {
		System.out.println("Enter video title to return: ") ;
		String videoTitle = scanner.next() ;
		return videoTitle;
	}

	private String getCustomerName() {
		System.out.println("Enter customer name: ") ;
		return scanner.next();
	}
	
	public void rentVideo() {
		String customerName = getCustomerName();
		
		Customer foundCustomer = customerManager.findCustomer(customerName);
		if ( foundCustomer == null ) 
			return ;
		
		String videoTitle = getRentVideoTitle();
		Video foundVideo = videoManager.findVideo(videoTitle);
		
		if ( foundVideo == null ) 
			return ;
		
		rentalManager.rentVideo(foundCustomer, foundVideo);	
	}

	private String getRentVideoTitle() {
		System.out.println("Enter video title to rent: ") ;
		String videoTitle = scanner.next() ;
		return videoTitle;
	}

	private void init() {
		customerManager.registerCustomer("James");
		customerManager.registerCustomer("Brown");
		
		videoManager.registerVideo("v1", Video.CD, Video.REGULAR, new Date()) ;
		videoManager.registerVideo("v2", Video.DVD, Video.NEW_RELEASE, new Date()) ;
		
		rentalManager.rentVideo(customerManager.findCustomer("James"), videoManager.findVideo("v1")) ;
		rentalManager.rentVideo(customerManager.findCustomer("James"), videoManager.findVideo("v2")) ;
	}

	public void getCustomerReport() {
		String customerName = getCustomerName();
		
		Customer foundCustomer = customerManager.findCustomer(customerName);
		if ( foundCustomer == null ) {
			System.out.println("No customer found") ;
		}
		else {
			String result = rentalManager.getCustomerReport(foundCustomer) ;
			System.out.println(result);
		}
	}

	private void registerVideo() {
		String title = getRegisterVideoTitle();		
		int videoType = getVideoType();		
		int priceCode = getPriceCode();
		Date registeredDate = new Date();
		
		videoManager.registerVideo(title, videoType, priceCode, registeredDate);
	}

	private void registerCustomer() { 
		customerManager.registerCustomer(getCustomerName());
	}

	private int getPriceCode() {
		System.out.println("Enter price code( 1 for Regular, 2 for New Release ):") ;
		int priceCode = scanner.nextInt();
		return priceCode;
	}

	private int getVideoType() {
		System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):") ;
		int videoType = scanner.nextInt();
		return videoType;
	}

	private String getRegisterVideoTitle() {
		System.out.println("Enter video title to register: ") ;
		String title = scanner.next() ;
		return title;
	}

	public int showCommand() {
		System.out.println("\nSelect a command !");
		System.out.println("\t 0. Quit");
		System.out.println("\t 1. List customers");
		System.out.println("\t 2. List videos");
		System.out.println("\t 3. Register customer");
		System.out.println("\t 4. Register video");
		System.out.println("\t 5. Rent video");
		System.out.println("\t 6. Return video");
		System.out.println("\t 7. Show customer report");
		System.out.println("\t 8. Show customer and clear rentals");
		
		int command = scanner.nextInt() ;
		
		return command ;
	}
}
