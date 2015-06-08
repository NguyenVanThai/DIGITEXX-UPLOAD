package digi.mobile.building;


/**
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class ImageItem {
	private String imagePath;
	private String title;

	public ImageItem(String imagePath, String title) {
		super();
		this.imagePath = imagePath;
		this.title = title;
	}

	public String getImage() {
		return imagePath;
	}

	public void setImage(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
