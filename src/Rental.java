import java.util.Date;

public class Rental {
	private Video video ;
	private int status ; // 0 for Rented, 1 for Returned
	private Date rentDate ;
	private Date returnDate ;
	
	private final static int RENTED = 0;		// 추가. Rental status를 명시적으로 표기
	private final static int RETURNED = 1;
	
	public static final int DAY_MS = 1000 * 60 * 60 * 24;
	
	public Rental(Video video) {
		this.video = video ;
		status = RENTED ;
		rentDate = new Date() ;
	}
	
	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public int getStatus() {
		return status;
	}

	public void returnVideo() {
		boolean isRented = status == RENTED;
		if ( isRented ) {					// status == 1 수정, Rented 된 상태일 경우 
			this.status = RETURNED;
			returnDate = new Date() ;
		}
	}
	
	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}
	
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getDaysRentedLimit() {
		int limit = 0 ;
		int daysRented = getRentedDays();
		if ( daysRented <= 2) return limit ;
		
		switch ( video.getVideoType() ) {
			case Video.VHS: limit = 5 ; break ;
			case Video.CD: limit = 3 ; break ;
			case Video.DVD: limit = 2 ; break ;
		}
		return limit ;	
	}

	public int getRentedDays() {
		long timeGap = getTimeGap();
		return getDaysRented(timeGap);
	}

	private int getDaysRented(long timeGap) {
		int daysRented;
		daysRented = (int) (timeGap / DAY_MS) + 1;
		return daysRented;
	}

	private long getTimeGap() {
		if (getStatus() == RETURNED) { // returned Video
			return returnDate.getTime() - rentDate.getTime();
			
		} else { // not yet returned
			return new Date().getTime() - rentDate.getTime();
		}
	}

	double getCharge(int daysRented) {
		double charge = 0;
		
		switch (getVideo().getPriceCode()) {
		case Video.REGULAR:
			charge += 2;
			if (daysRented > 2)
				charge += (daysRented - 2) * 1.5;
			break;
		case Video.NEW_RELEASE:
			charge = daysRented * 3;
			break;
		}
		return charge;
	}

	int getPoint(int daysRented) {
		int eachPoint = 1;
		boolean isRentNewRelease = getVideo().getPriceCode() == Video.NEW_RELEASE;
		if (isRentNewRelease)
			eachPoint++;
	
		boolean isLateReturn = daysRented > getDaysRentedLimit();
		if (isLateReturn)
			eachPoint -= Math.min(eachPoint, getVideo().getLateReturnPointPenalty());
		return eachPoint;
	}
}