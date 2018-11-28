import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;



// Sklavenitis Dimitrios
// AEM: 9455
// Phone Number: 6940064840
// email: skladimi@ece.auth.gr
// 
// Ourdas Antonios
// AEM: 9358
// Phone Number: 6980561699
// email: ourdasav@ece.auth.gr



public class Game {
	
	private int round;
	
	// Constructors
	public Game() {round = 0;}
	
	public Game(int round) {this.round = round;}
	
	// Getter
	public int getRound() {return round;}
	
	// Setter
	public void setRound(int round) {this.round = round;}
	
	// Dice roll function
	public static int roll()
	{ 
		return (int)(1+Math.random()*6) ;
	}
	
	public static double wf(int tiles,int score) {
		return tiles*0.6+score*0.4;
	}
	public static Map<Integer,Integer> setTurns(ArrayList<Object> players){
		int rollNo=0;
		
		Map<Integer,Integer> map=new TreeMap<Integer,Integer>();
		
		int[]diceArray =new int[players.size()];
		
		for(int i=0;i<diceArray.length;i++) {
			diceArray[i]=0;
		}
		
		for(int i=0;i<players.size();i++) {
		
			rollNo=roll();  
			
			map.put(rollNo,((Player)players.get(i)).getPlayerId());
			
			/// Restarts the process if two players roll the same number
			for(int j=0;j<diceArray.length;j++) {          
				if(rollNo==diceArray[j]) { 
					map.clear();
					i=0;
					for(int k=0;k<diceArray.length;k++) {
						diceArray[i]=0;    ///gg
					}
					rollNo=0;
				}
			} 
			  
			diceArray[i]=rollNo;
			
		}
		return map;
	}
	


	
	public static void main(String[] args) 
	{
		
		Game g = new Game(-1);
		
		Board board = new Board(20, 10, 3, 3, 6);
		
		board.createBoard();
		
		board.createElementBoard();
		
		// Array used for players
		ArrayList<Object>players = new ArrayList<Object>();
		
		players.add(new Player(0, 0, "Henry", board));
		
		players.add(new HeuristicPlayer(1, 0, "Francois", board));  // id=1 for the Heuristic Player 
		
		Map<Integer,Integer> turnMap = new TreeMap<Integer,Integer>();
		
		turnMap=setTurns(players);
		
		// Array with each player's Tile Id (position on board)
		int playerTileIds[] = new int[players.size()];
		
		// Initialize all players
		// Put them in position 1
		for(int i = 0; i < playerTileIds.length; i++)
		{
			playerTileIds[i] = 1;
		}
		
		
		
		boolean completed = false;
		
		//Game start
		for(int i=0;i<100;i++) {
			g.setRound(g.getRound()+1);
			//Round start
			for(Map.Entry<Integer,Integer> entry:turnMap.entrySet())
			{
				// Move player
				//Check if it's the Heuristic player
				if(((Player)players.get(entry.getValue())).getPlayerId()==1) {       
					
					playerTileIds[entry.getValue()] = ((HeuristicPlayer)players.get(playerTileIds[entry.getValue()])).getNextMove(playerTileIds[entry.getValue()]);
				
				} else {
					
					playerTileIds[entry.getValue()] = ((Player)players.get(playerTileIds[entry.getValue()])).move(playerTileIds[entry.getValue()], roll())[0];
				
				}
				if(playerTileIds[entry.getValue()]>=board.getM()*board.getN()) {
					
					playerTileIds[entry.getValue()] = board.getM() * board.getN();
					completed=true;
					break;
				}
			}
			
			if(completed) {
				break;
			}
		
		}
		double wfMax=0;
		int winnerId=0;
		double number=0;
		for(int i =0;i<players.size();i++) {
			number=wf(playerTileIds[((Player)players.get(i)).getPlayerId()],((Player)players.get(i)).getScore());
			if(number>wfMax) {
				wfMax=number;
				winnerId=((Player)players.get(i)).getPlayerId();
			}else if(number==wfMax && playerTileIds[((Player)players.get(i)).getPlayerId()]>playerTileIds[winnerId]) {
				winnerId=((Player)players.get(i)).getPlayerId();
			}
		
			
		}
		((HeuristicPlayer)players.get(1)).statistics();
		
		System.out.println("Total number of rounds played:"+g.getRound());
		for(int i=0;i<players.size();i++) {
			System.out.println(((Player)players.get(i)).getName()+"'s score:"+((Player)players.get(i)).getScore());
			System.out.println();
		}
		
		System.out.print("The winner is "+((Player)players.get(winnerId)).getName());
		
		if(((Player)players.get(winnerId)).getPlayerId()==1) {
			System.out.println(" as expected");
		}
		
		
		
}
	





}
