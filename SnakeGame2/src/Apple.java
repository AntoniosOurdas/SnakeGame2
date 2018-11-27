// Sklavenitis Dimitrios
// AEM: 9455
// Phone Number: 6940064840
// email: skladimi@ece.auth.gr
// 
// Ourdas Antonios
// AEM: 9358
// Phone Number: 6980561699
// email: ourdasav@ece.auth.gr

public class Apple {
	private int appleId;
	private int appleTileId;
	private String color;
	private int points;
	
	// Constructors
	public Apple() {
		appleId = 0;
		appleTileId = 0;
		color = "";
		points = 0;
	}
	
	public Apple(int appleId, int appleTileId, String color, int points) {
		this();
		this.appleId = appleId;
		this.appleTileId = appleTileId;
		this.color = color;
		this.points = points;
	}
	
	public Apple(Apple a) {
		this(a.appleId, a.appleTileId, a.color, a.points);
	}
	
	// Getters
	public int getAppleId() {return appleId;}
	public int getAppleTileId() {return appleTileId;}
	public String getColor() {return color;}
	public int getPoints() {return points;}
	
	// Setters
	public void setAppleId(int appleId) {this.appleId = appleId;}
	public void setAppleTileId(int appleTileId) {this.appleTileId = appleTileId;}
	public void setColor(String color) {this.color = color;}
	public void setPoints(int points) {this.points = points;}
}
