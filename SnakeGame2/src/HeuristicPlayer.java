import java.util.ArrayList;

public class HeuristicPlayer extends Player {
	ArrayList<int[]> path;

	public HeuristicPlayer() {
		super();
		path = new ArrayList<int[]>();
	}

	public HeuristicPlayer(int playerId, int score, String name, Board tempBoard) {
		super(playerId, score, name, tempBoard);
		path = new ArrayList<int[]>();
	}

	public double evaluate(int currentPos, int dice) {

		// Value used for storing evaluation results
		double f = 0.0;

		// Points gained by this move, snakes, ladders and apples the player met
		int points = 0;

		// Temporary tempBoard used for move evaluation
		Board tempBoard = new Board(board);

		// Initial steps is the dice number
		int totalSteps = dice;
		currentPos += dice;

		// Check if player meets a snake
		for (int i = 0; i < tempBoard.getSnakes().length; ++i) {
			if (tempBoard.getSnakes()[i].getHeadId() == currentPos) {
				// Subtract from total steps
				totalSteps -= (tempBoard.getSnakes()[i].getHeadId() - tempBoard.getSnakes()[i].getTailId());
				// Move player to snake tail
				currentPos = tempBoard.getSnakes()[i].getTailId();
			}
		}

		// Check if player meets a ladder
		for (int i = 0; i < tempBoard.getLadders().length; ++i) {
			if (tempBoard.getLadders()[i].getUpStepId() == currentPos) {
				// Add to total steps
				totalSteps += (tempBoard.getLadders()[i].getDownStepId() - tempBoard.getLadders()[i].getUpStepId());
				// Move player to ladder top
				currentPos = tempBoard.getLadders()[i].getDownStepId();
			}
		}

		// Check if player meets an apple and add points to evaluation
		for (int i = 0; i < tempBoard.getApples().length; ++i) {
			if (tempBoard.getApples()[i].getAppleTileId() == (currentPos) && board.getApples()[i].getPoints() != 0) {
				points += tempBoard.getApples()[i].getPoints();
				tempBoard.getApples()[i].setPoints(0);
			}
		}

		f = totalSteps * 0.6 + points * 0.4;
		return f;
	}

	public int getNextMove(int currentPos) {
		// List in which every possible move of the player is stored as an array of
		// doubles
		ArrayList<double[]> moves = new ArrayList<double[]>();

		double[] tempMove = new double[2];

		for (int i = 0; i < 6; ++i) {
			tempMove[0] = i + 1;
			tempMove[1] = evaluate(currentPos, i + 1);
			moves.add(tempMove);
		}

		// Find next move with maximum evaluation
		double maxEvaluation = moves.get(0)[1];
		int maxIndex = 0;

		for (int i = 1; i < 6; ++i) {
			if (moves.get(maxIndex)[1] > maxEvaluation) {
				maxEvaluation = moves.get(i)[1];
				maxIndex = i;
			}
		}
		
		int[] nextMove = new int[7];
		nextMove = move(currentPos, maxIndex + 1);

		path.add(nextMove);
		
		// Return next move
		return nextMove[7];
	}

	public void statistics() {

		int snakeHead = 0, ladderBase = 0, redApple = 0, blackApple = 0;

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
