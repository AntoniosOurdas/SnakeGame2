// Sklavenitis Dimitrios
// AEM: 9455
// Phone Number: 6940064840
// email: skladimi@ece.auth.gr
// 
// Ourdas Antonios
// AEM: 9358
// Phone Number: 6980561699
// email: ourdasav@ece.auth.gr

public class Ladder
{
	private int ladderId;
	private int upStepId;
	private int downStepId;
	private boolean broken;
	
	// Constructors
	public Ladder() {
		ladderId = 0;
		upStepId = 0;
		downStepId = 0;
		broken = false;
	}
	
	public Ladder(int ladderId, int upStepId, int downStepId, boolean broken) {
		this();
		this.ladderId = ladderId;
		this.upStepId = upStepId;
		this.downStepId = downStepId;
		this.broken = broken;
	}
	
	public Ladder(Ladder l) {
		this(l.ladderId, l.upStepId, l.downStepId, l.broken);
	}
	
	// Getters
	public int getLadderId() {return ladderId;}
	public int getUpStepId() {return upStepId;}
	public int getDownStepId() {return downStepId;}
	public boolean getBroken() {return broken;}
	
	// Setters
	public void setLadderId(int ladderId) {this.ladderId = ladderId;}
	public void setUpStepId(int upStepId) {this.upStepId = upStepId;}
	public void setDownStepId(int downStepId) {this.downStepId = downStepId;}
	public void setBroken(boolean broken) {this.broken = broken;}
}
