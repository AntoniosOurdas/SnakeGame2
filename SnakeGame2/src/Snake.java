// Sklavenitis Dimitrios
// AEM: 9455
// Phone Number: 6940064840
// email: skladimi@ece.auth.gr
// 
// Ourdas Antonios
// AEM: 9358
// Phone Number: 6980561699
// email: ourdasav@ece.auth.gr

public class Snake 
{
	private int snakeId;
	private int headId;
	private int tailId;
	
	// Constructors
	public Snake() {
		snakeId = 0;
		headId = 0;
		tailId = 0;
	}
	
	public Snake(int snakeId, int headId, int tailId) {
		this();
		this.snakeId = snakeId;
		this.headId = headId;
		this.tailId = tailId;
	}
	
	public Snake(Snake s) {
		this(s.snakeId, s.headId, s.tailId);
	}
	
	// Getters
	public int getSnakeId() {return snakeId;}
	public int getHeadId() {return headId;}
	public int getTailId() {return tailId;}
	
	// Setters
	public void setSnakeId(int snakeId) {this.snakeId = snakeId;}
	public void setHeadId(int headId) {this.headId = headId;}
	public void setTailId(int tailId) {this.tailId = tailId;}
}
