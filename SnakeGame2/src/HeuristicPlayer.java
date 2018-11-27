import java.util.ArrayList;

public class HeuristicPlayer extends Player {
	ArrayList<int[]> path;
	
	public HeuristicPlayer() {
		super();
	}
	
	public HeuristicPlayer(int playerId, int score, String name, Board board) {
		super(playerId, score, name, board);
	}
	
	public double[] evaluate(int currentPos, int dice) {
		
		// Create a copy of the original board in order to examine the player's possible move
		// This way the original board isn't affected
		Board tempBoard = new Board(board);
		
		// This array stores information about the player's possible move
		// It's contents are:
		// moveEvaluation[0]: dice number
		// moveEvaluation[1]: total points gained by this move
		// moveEvaluation[2]: total steps made by the player in this move
		// moveEvaluation[3]: evaluation function result
		// The evaluation function is: f = 0.6 * steps + 0.4 * points
		double[] moveEvaluation = new double[4];
		
		// Value used for storing evaluation function result
		double f = 0.0;
		
		// Points gained by this move
		int points = 0;
		
		// Total steps made by player in this move
		// Initial steps are the dice number
		int totalSteps = dice;
		
		// Move player by the dice number
		currentPos += dice;
		
		// Check if player meets a snake
		for(int i = 0; i < tempBoard.getSnakes().length; ++i) {
			if(tempBoard.getSnakes()[i].getHeadId() == currentPos) {
				// Subtract from total steps, since the player is moving backwards
				totalSteps -= (tempBoard.getSnakes()[i].getHeadId() - tempBoard.getSnakes()[i].getTailId());
				// Move player to snake tail
				currentPos = tempBoard.getSnakes()[i].getTailId();
			}
		}
		
		// Check if player meets a ladder
		for(int i = 0; i < tempBoard.getLadders().length; ++i) {
			if(tempBoard.getLadders()[i].getUpStepId() == currentPos && !tempBoard.getLadders()[i].getBroken()) {
				// Add to total steps
				totalSteps += (tempBoard.getLadders()[i].getDownStepId() - tempBoard.getLadders()[i].getUpStepId());
				// Move player to ladder top
				currentPos = tempBoard.getLadders()[i].getDownStepId();
				tempBoard.getLadders()[i].setBroken(true);
			}
		}
		
		// Check if player meets an apple and add points to evaluation
		for(int i = 0; i < tempBoard.getApples().length; ++i) {
			if(tempBoard.getApples()[i].getAppleTileId() == (currentPos)) {
				points += tempBoard.getApples()[i].getPoints();
				tempBoard.getApples()[i].setPoints(0);
			}
		}
		
		f = totalSteps * 0.6 + points * 0.4;
		// Store move results
		moveEvaluation[0] = dice;
		moveEvaluation[1] = points;
		moveEvaluation[2] = totalSteps;
		moveEvaluation[3] = f;
		
		return moveEvaluation;
	}
	
	
	public int getNextMove(int currentPos) {
		// List in which every possible move is stored as an array of doubles
		// This array matches the array described int the above evaluate() function
		ArrayList<double[]> moves = new ArrayList<double[]>();
		
		for(int i = 0; i < 6; ++i) {
			moves.add(evaluate(currentPos, i + 1));
		}
		
		// Find next move with maximum evaluation
		double maxEvaluation = moves.get(0)[3];
		int maxIndex = 0;
		
		for(int i = 1; i < 6; ++i) {
			if(moves.get(i)[3] > maxEvaluation) {
				maxEvaluation = moves.get(i)[3];
				maxIndex = i;
			}
		}
		
		// A Double array is used in order to convert doubles to int
		// This array matches the array chosen from moves ArrayList
		Double[] temp = new Double[4];
		
		for(int i = 0; i < 4; ++i) {
			temp[i] = moves.get(maxIndex)[i];
		}
		
		// This array is used to store the nextMove information
		// (Final position of the player and number of snakes, ladders, red apples, black apples
		int[] tempMove = new int[5];
		
		// Make the actual move of the player and save results in tempMove array
		tempMove = move(currentPos, temp[0].intValue());
		
		// This array will be added to path ArrayList
		// We fill in the array with information about the next move, from the temp and tempMove and temp
		int[] nextMove = new int[7];
		
		nextMove[0] = temp[0].intValue(); // Dice number
		nextMove[1] = temp[1].intValue(); // Points gained by player
		nextMove[2] = temp[2].intValue(); // Total steps of player
		nextMove[2] = tempMove[3]; // Number of red apples the player ate
		nextMove[3] = tempMove[4]; // Number of black apples the player ate
		nextMove[4] = tempMove[1]; // Number of snakes that bit the player
		nextMove[5] = tempMove[2]; // Number of ladders that bit the player
		
		// Add array to path variable
		path.add(nextMove);
		
		// Return the player's new position
		return tempMove[0];
	}
	
	
	public void statistics() {
		
		int snakeHead = 0, ladderBase = 0, redApple = 0, blackApple = 0;
		
		for(int i = 0; i < path.size(); ++i) {
			System.out.println("Round " + (i + 1));
			System.out.println("Dice Number: " + path.get(i)[0]);
			System.out.println("Points: " + path.get(i)[1]);
			System.out.println("Total Steps: " + path.get(i)[2]);
			System.out.println("Red apples eaten: " + path.get(i)[3]);
			System.out.println("Black apples eaten: " + path.get(i)[4]);
			System.out.println("Snakes beaten by: " + path.get(i)[5]);
			System.out.println("Ladders climbed: " + path.get(i)[6]);
			
			redApple += path.get(i)[3];
			blackApple += path.get(i)[4];
			snakeHead += path.get(i)[5];
			ladderBase += path.get(i)[6];
			System.out.println();
		}
		
		System.out.println("==============================");
		
		System.out.println("Statistics:");
		
		System.out.println("The player came across " + snakeHead + " snake heads");
		System.out.println("The player came across " + ladderBase + " ladder bases");
		System.out.println("The player came across " + redApple + " red apples");
		System.out.println("The player came across " + blackApple + " black apples");
	}
}
