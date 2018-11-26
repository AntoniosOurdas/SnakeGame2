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
		
		double[] moveEvaluation = new double[8];
		
		// Value used for storing evaluation results
		double f = 0.0;
		
		// Points gained by this move, snakes, ladders and apples the player met
		int points = 0, snakesNo = 0, laddersNo = 0, redApplesNo = 0, blackApplesNo = 0;
		
		// Initial steps is the dice number
		int totalSteps = dice;
		currentPos += dice;
		
		// Check if player meets a snake
		for(int i = 0; i < board.getSnakes().length; ++i) {
			if(board.getSnakes()[i].getHeadId() == currentPos) {
				// Add to total steps
				totalSteps += (board.getSnakes()[i].getHeadId() - board.getSnakes()[i].getTailId());
				// Move player to snake tail
				currentPos = board.getSnakes()[i].getTailId();
				++snakesNo;
			}
		}
		
		// Check if player meets a ladder
		for(int i = 0; i < board.getLadders().length; ++i) {
			if(board.getLadders()[i].getUpStepId() == currentPos) {
				// Add to total steps
				totalSteps += (board.getLadders()[i].getDownStepId() - board.getLadders()[i].getUpStepId());
				// Move player to ladder top
				currentPos = board.getLadders()[i].getDownStepId();
				++laddersNo;
			}
		}

		f += 0.6 * totalSteps;
		
		// Check if player meets an apple and add points to evaluation
		for(int i = 0; i < board.getApples().length; ++i) {
			if(board.getApples()[i].getAppleTileId() == (currentPos)) {
				f += 0.4 * board.getApples()[i].getPoints();
				points += board.getApples()[i].getPoints();
				if(board.getApples()[i].getColor() == "red") {
					++redApplesNo;
				} else if (board.getApples()[i].getColor() == "black"){
					++blackApplesNo;
				}
			}
		}
	
		// Store move results
		moveEvaluation[0] = dice;
		moveEvaluation[1] = points;
		moveEvaluation[2] = totalSteps;
		moveEvaluation[3] = redApplesNo;
		moveEvaluation[4] = blackApplesNo;
		moveEvaluation[5] = snakesNo;
		moveEvaluation[6] = laddersNo;
		moveEvaluation[7] = f;
		
		return moveEvaluation;
	}
	
	
	public int getNextMove(int currentPos) {
		// List in which every possible move of the player is stored as an array of doubles
		ArrayList<double[]> moves = new ArrayList<double[]>();
		
		for(int i = 0; i < 6; ++i) {
			moves.add(evaluate(currentPos, i + 1));
		}
		
		// Find next move with maximum evaluation
		double maxEvaluation = moves.get(0)[6];
		int maxIndex = 0;
		
		for(int i = 1; i < 6; ++i) {
			if(moves.get(i)[6] > maxEvaluation) {
				maxEvaluation = moves.get(i)[6];
				maxIndex = i;
			}
		}
		
		// Convert double to int in order to update path variable
		Double[] d = new Double[7];
		
		for(int i = 0; i < 7; ++i) {
			d[i] = moves.get(maxIndex)[i];
		}
		
		int[] nextMove = new int[7];
		for(int i = 0; i < 7; ++i) {
			nextMove[i] = d[i].intValue();
		}
		
		path.add(nextMove);
		
		// Return next move
		return (currentPos + nextMove[2]);
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
