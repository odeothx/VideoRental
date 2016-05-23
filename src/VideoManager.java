import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class VideoManager {
	private List<Video> videos;

	private static VideoManager videoManager = null;

	private VideoManager() {
		this.videos = new ArrayList<Video>();
	}
	
	public static VideoManager getInstance()
	{
		if(videoManager == null)
		{
			videoManager = new VideoManager();
		}
		return videoManager;
	}

	public Video findVideo(String title) {
		Video foundVideo = null ;
		for ( Video video: videos ) {
			if ( video.getTitle().equals(title) && video.isRented() == false ) {
				foundVideo = video ;
				break ;
			}
		}
		return foundVideo;
	}

	public void printVideoList() {
		System.out.println("List of videos");
		
		for ( Video video: videos ) {
			System.out.println("Price code: " + video.getPriceCode() +"\tTitle: " + video.getTitle()) ;
		}
		System.out.println("End of list");
	}

	void registerVideo(String title, int videoType, int priceCode, Date registeredDate) {
		Video video = new Video(title, videoType, priceCode, registeredDate) ;
		videos.add(video) ;
	}
}