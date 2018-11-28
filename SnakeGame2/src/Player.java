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
	public int getPlayerId() {
		return playerId;
	}

	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}

	public Board getBoard() {
		return board;
	}

	// Setters
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int[] move(int id, int die) {
		int stats[] = new int[8];

		for (int i = 0; i < stats.length; i++) {
			stats[i] = 0;
		}

		// stats[0] = die
		// stats[1] = total points gained by this movement
		// stats[2] = total steps made by the player
		// stats[3] = number of red apples eaten by the player
		// stats[4] = number of black apples eaten by the player
		// stats[5] = number of snakes the player way bitten by
		// stats[6] = number of ladders the player climbed
		// stats[7] = new position

		stats[0] = die;

		stats[7] = id + die;

		// Snake check
		for (int i = 0; i < board.getSnakes().length; ++i) {
			if (stats[7] == board.getSnakes()[i].getHeadId()) {
				stats[7] = board.getSnakes()[i].getTailId();
				System.out.println(name + " has been bitten by a snake!! oh noooooo!");
				System.out.println();
				++stats[5];
			}
		} // Snake check done

		// Ladder check
		for (int i = 0; i < board.getLadders().length; ++i) {
			if (stats[7] == board.getLadders()[i].getUpStepId() && !board.getLadders()[i].getBroken()) {
				stats[7] = board.getLadders()[i].getDownStepId();
				System.out.println(name + " has climbed a ladder...crack(that ladder is broken now)");
				System.out.println();
				++stats[6];
				board.getLadders()[i].setBroken(true);
			}
		} // Ladder check done

		// Apple check
		for (int i = 0; i < board.getApples().length; ++i) {
			if (stats[7] == board.getApples()[i].getAppleTileId() && board.getApples()[i].getPoints() != 0) {
				System.out.println(name + " has just eaten a " + board.getApples()[i].getColor() + " apple");
				System.out.println();
				score += board.getApples()[i].getPoints();
				board.getApples()[i].setPoints(0);
				if (board.getApples()[i].getColor() == "red") {
					++stats[3];
				} else {
					++stats[4];
				}
			}
		} // Apple check done

		return stats;
	}
}
