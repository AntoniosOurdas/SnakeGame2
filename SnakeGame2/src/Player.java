// Sklavenitis Dimitrios
// AEM: 9455
// Phone Number: 6940064840
// email: skladimi@ece.auth.gr
// 
// Ourdas Antonios
// AEM: 9358
// Phone Number: 6980561699
// email: ourdasav@ece.auth.gr

public class Player {
	
	int playerId;
	int score;
	String name;
	Board board;
	
	/// Constructors
	
	public Player() {
		playerId = 0;
		score = 0;
		name = "";
		board = new Board();
	}
	
	
	public Player(int playerId, int score, String name, Board board) {
		this.playerId = playerId;
		this.score = score;
		this.name = name;
		this.board = board;
	}
	
	// Getters
	public int getPlayerId() {return playerId;}
	
	public int getScore() {return score;}
	
	public String getName() {return name;}
	
	public Board getBoard() {return board;}
	
	// Setters	
	public void setPlayerId(int playerId) {this.playerId = playerId;}
	
	public void setScore(int score) {this.score = score;}
	
	public void setName(String name) {this.name = name;}
	
	public void setBoard(Board board) {this.board = board;}
	
	
	public int[] move(int id, int  die) {
		int stats[] = new int[5];                           
		
		for(int i = 0; i < stats.length; i++) {
			stats[i] = 0;
		}
		
		//stats[0] = newposid
		//stats[1] = snakesno.
		//stats[2] = laddersnno.         
		//stats[3] = redapples
		//stats[4] = blackapples
		
		stats[0] = id + die;
		 
		// Snake check
		for(int i = 0; i < board.getSnakes().length; ++i) {
			if(stats[0] == board.getSnakes()[i].getHeadId()) { 
				stats[0] = board.getSnakes()[i].getTailId();
				System.out.println(name + " has been bitten by a snake!! oh noooooo!");
				System.out.println();
				++stats[1];
			}
		} // Snake check done
		
		// Ladder check
		for(int i = 0; i < board.getLadders().length; ++i) {
			if(stats[0] == board.getLadders()[i].getUpStepId() && !board.getLadders()[i].getBroken()) {
				stats[0] = board.getLadders()[i].getDownStepId();
				System.out.println(name + " has climbed a ladder...crack(that ladder is broken now)");
				System.out.println();
				++stats[2];
				board.getLadders()[i].setBroken(true);
			}
		} // Ladder check done
		 
		// Apple check
		for(int i = 0; i < board.getApples().length; ++i) {
			if(stats[0] == board.getApples()[i].getAppleTileId()) {
				System.out.println(name + " has just eaten a " + board.getApples()[i].getColor() + " apple");
				System.out.println();
				score += board.getApples()[i].getPoints();
				board.getApples()[i].setPoints(0);
			    if(board.getApples()[i].getColor() == "red") {
			    	++stats[3];
			    }
			    else
			    {
			    	++stats[4];
			    }
			}	 
		 } // Apple check done
       
		return stats;
	}
}
