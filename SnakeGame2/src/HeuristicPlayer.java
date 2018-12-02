
// Sklavenitis Dimitrios
// AEM: 9455
// Phone Number: 6940064840
// email: skladimi@ece.auth.gr
// 
// Ourdas Antonios
// AEM: 9358
// Phone Number: 6980561699
// email: ourdasav@ece.auth.gr

import java.util.ArrayList;

public class HeuristicPlayer extends Player {
	ArrayList<Integer[]> path;

	public HeuristicPlayer() {
		super();
		path = new ArrayList<Integer[]>();
	}

	public HeuristicPlayer(int playerId, int score, String name, Board tempBoard) {
		super(playerId, score, name, tempBoard);
		path = new ArrayList<Integer[]>();
	}
	
	// Getter
	public ArrayList<Integer[]> getPath(){return path;}
	
	// Setter
	public void setPath(ArrayList<Integer[]> path){this.path = path;}

	public double evaluate(int currentPos, int dice) {

		// Value used for storing evaluation results
		double f = 0.0;

		// Points gained by this move
		int points = 0;

		// Temporary Board used for move evaluation
		Board tempBoard = new Board(board);

		// Total number of steps the player has made by this move
		// Initial steps is the dice number
		int totalSteps = dice;
		currentPos += dice;

		// Check if player meets a snake
		for (int i = 0; i < tempBoard.getSnakes().length; ++i) {
			if (tempBoard.getSnakes()[i].getHeadId() == currentPos) {
				// Subtract from total steps, because the player moves backwards (i.e. its id on
				// the board decreases)
				totalSteps -= (tempBoard.getSnakes()[i].getHeadId() - tempBoard.getSnakes()[i].getTailId());
				// Move player to snake tail
				currentPos = tempBoard.getSnakes()[i].getTailId();
			}
		}

		// Check if player meets a ladder
		for (int i = 0; i < tempBoard.getLadders().length; ++i) {
			if (tempBoard.getLadders()[i].getUpStepId() == currentPos && !tempBoard.getLadders()[i].getBroken()) {
				// Add to total steps, because the player moves upwards (i.e. its id on the
				// board decreases)
				totalSteps += (tempBoard.getLadders()[i].getDownStepId() - tempBoard.getLadders()[i].getUpStepId());
				// Move player to ladder top
				currentPos = tempBoard.getLadders()[i].getDownStepId();
			}
		}

		// Check if player meets an apple and add apple points to total points
		for (int i = 0; i < tempBoard.getApples().length; ++i) {
			if (tempBoard.getApples()[i].getAppleTileId() == (currentPos) && board.getApples()[i].getPoints() != 0) {
				points += tempBoard.getApples()[i].getPoints();
				tempBoard.getApples()[i].setPoints(0);
			}
		}

		// Evaluate move
		f = totalSteps * 0.6 + points * 0.4;

		// And return evaluation
		return f;
	}

	// getNextMove function
	public int getNextMove(int currentPos) {
		// List in which every possible move of the player is stored
		// as an array of doubles
		// The first element is the dice number
		// And the second element is the evaluation function value for that
		// specific dice number

		ArrayList<double[]> moves = new ArrayList<double[]>();

		// Temporary double array used in order to save the move to "moves" ArrayList
		double[] tempMove;

		// Fill in ArrayList with every possible move of the player
		// (i.e. call the function for dices from 1 to 6)
		for (int i = 1; i <= 6; ++i) {
			// Temporarily store move
			tempMove = new double[2]; // Allocate memory for the new move that will be stored in ArrayList
			tempMove[0] = i; // save dice number which is the same as i
			tempMove[1] = evaluate(currentPos, i); // save evaluation function value (f(i))

			// And save it to the list
			moves.add(tempMove);
		}

		// Find move with maximum evaluation
		//
		// Maximum evaluation value
		double maxEvaluation = moves.get(0)[1];
		// Index in the array List of the move with maximum evaluation
		int maxIndex = 0;

		// Check all elements of ArrayList to find the one with maximum evaluation
		// We assume the first element is the maximum
		for (int i = 1; i < 6; ++i) {
			if (moves.get(i)[1] > maxEvaluation) {
				maxEvaluation = moves.get(i)[1];
				maxIndex = i;
			}
		}

		// Temporary arrays used to save the player's movement information
		Integer[] nextMove = new Integer[8];
		int[] temp = new int[8];

		// Call move function to make the actual movement of the player
		// This function has been modified in order to return an array of ints with 8
		// elements
		// Then the ints are converted to Integer objects in order to be saved in the
		// path variable

		temp = move(currentPos, maxIndex + 1);

		// Conversion
		for (int i = 0; i < 8; ++i) {
			nextMove[i] = temp[i];
		}

		// Add array to path ArrayList
		path.add(nextMove);

		// Return next move position
		// (i.e. the player's position after he made the move)
		return nextMove[7];
	}

	// Statistics function
	public void statistics() {

		// Variables used to calculate total number of tiles with snake heads, ladder
		// bases, red and black apples the player has encountered

		int snakeHead = 0, ladderBase = 0, redApple = 0, blackApple = 0;

		// Print information of every movement the player has made
		for (int i = 0; i < path.size(); ++i) {
			System.out.println("Round " + (i + 1));
			System.out.println("Dice Number: " + path.get(i)[0]);
			System.out.println("Points: " + path.get(i)[1]);
			System.out.println("Total Steps: " + path.get(i)[2]);
			System.out.println("Red apples eaten: " + path.get(i)[3]);
			System.out.println("Black apples eaten: " + path.get(i)[4]);
			System.out.println("Snakes beaten by: " + path.get(i)[5]);
			System.out.println("Ladders climbed: " + path.get(i)[6]);
			System.out.println("New position: " + path.get(i)[7]);

			// Count total red apples, black apples, snake heads and ladders
			redApple += path.get(i)[3];
			blackApple += path.get(i)[4];
			snakeHead += path.get(i)[5];
			ladderBase += path.get(i)[6];
			System.out.println();
		}
		// Print total statistics

		System.out.println("==============================");

		System.out.println("Statistics:");

		System.out.println("The player came across " + snakeHead + " snake heads");
		System.out.println("The player came across " + ladderBase + " ladder bases");
		System.out.println("The player came across " + redApple + " red apples");
		System.out.println("The player came across " + blackApple + " black apples");
		System.out.println("==============================");
		System.out.println();
	}
}
