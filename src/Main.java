import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static int rows, columns, connectN, timeLimit;
    public static boolean player1, goingFirst;
    public static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    public static String name = "RyanAndAdilet";
    public static Board board;
    public static long turnStartTime;
    public static int BRANCH_FACTOR_DROP = 4;
    public static int PROB_THRESHOLD = 5;
    public static int DEPTH = 10;
    public static Random r = new Random();
    private static void processInput(Integer column, Integer moveType) {
	if(moveType == 1) {
	    board.dropADiscFromTop(column, 2);
	} else {
	    board.removeADiscFromBottom(column);
	}
	ArrayList<Integer> move = Main.determineMove();
	System.out.println(move.get(0) + " " + move.get(1));
	if(move.get(1) == 1) {
		board.dropADiscFromTop(move.get(0), 1);
	} else {
		board.removeADiscFromBottom(move.get(0));
	}
	board.printBoard();
    }
    public static void main(String[] args) {
	// Announce ourselves
	System.out.println(name);
	// Read the line telling player1 and player2
	String s = null;
	try {
		s = input.readLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	List<String> ls=Arrays.asList(s.split(" "));
	player1 = ls.get(1).equals(name);
	// Read the line telling the parameters of the game	
	String s1 = null;
	try {
		s1 = input.readLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	List<String> ls1 = Arrays.asList(s1.split(" "));
	rows = Integer.parseInt(ls1.get(0));
	columns = Integer.parseInt(ls1.get(1));
	connectN = Integer.parseInt(ls1.get(2));
	goingFirst = (Integer.parseInt(ls1.get(3)) == 2) ^ player1;
	timeLimit = Integer.parseInt(ls1.get(4));
	board = new Board(rows, columns, connectN);
	if(goingFirst) {
	    board.dropADiscFromTop(columns/2, 1);
	    System.out.println(columns/2 + " 1");
	}
	while(true) {
	    String s11 = null;
		try {
			s11 = input.readLine();
			turnStartTime = System.nanoTime();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if(s11.length() >= 3) {
		processInput(Integer.parseInt(s11.substring(0,1)), Integer.parseInt(s11.substring(2,3)));
	    }
        }
    }

    // Because we can't prune anything directly below the top level, don't attempt to do so
    public static ArrayList<Integer> determineMove() {
	ArrayList<Integer> rtn = new ArrayList<Integer>();
	rtn.add(3);
	rtn.add(1);
	int bestValue = Integer.MIN_VALUE;
	for(int i = 0; i < Board.width; i++) {
	    if(board.canDropADiscFromTop(i, 1)) {
	    	Node tmp = new Node (new Board(board), 2);
	    	tmp.board.dropADiscFromTop(i, 1);
		int candidateValue = alphaBeta(tmp, DEPTH, bestValue, Integer.MAX_VALUE, false);
	        if(candidateValue > bestValue) {
		    bestValue = candidateValue;
		    rtn.set(0, i);
		    rtn.set(1, 1);
		    if(candidateValue == Integer.MAX_VALUE) break;
		}
	    }
	    if(board.canRemoveADiscFromBottom(i, 1)) {
	    	Node tmp = new Node(new Board(board),
	    			2);
	    	tmp.board.removeADiscFromBottom(i);
		int candidateValue = alphaBeta(tmp, DEPTH, bestValue, Integer.MAX_VALUE, false);
	        if(candidateValue > bestValue) {
		    bestValue = candidateValue;
		    rtn.set(0, i);
		    rtn.set(1, 0);
		    if(candidateValue == Integer.MAX_VALUE) break;
		}
	    }
	}
	return rtn;
    }
    
    public static int alphaBeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {
	int isConnectN = node.board.isConnectN();
	if(isConnectN == 0) {
	    return maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
	} else if(isConnectN == 1) {
	    return Integer.MAX_VALUE; 
	} else if(isConnectN == 2) {
	    return Integer.MIN_VALUE;
	} else if(depth == 0 || System.nanoTime() - turnStartTime > (timeLimit-1)*1000000000L) {
	    return node.heuristic();
	}
	ArrayList<Node> children = node.children();
	if(depth <= PROB_THRESHOLD) {
		while(children.size() > BRANCH_FACTOR_DROP) {
			children.remove(r.nextInt(children.size() - 1));
		}
	}
	int bestSoFar;
	if(maximizingPlayer) {
	    bestSoFar = Integer.MIN_VALUE;
	    for(Node c : children) {
		bestSoFar = Math.max(bestSoFar, alphaBeta(c, depth - 1, alpha, beta, false));
		alpha = Math.max(alpha, bestSoFar);
		if(beta <= alpha) {
		    break;
		}
	    }
	} else {
	    bestSoFar = Integer.MAX_VALUE;
	    for(Node c : children) {
		bestSoFar = Math.min(bestSoFar, alphaBeta(c, depth - 1, alpha, beta, true));
		beta = Math.min(bestSoFar, beta);
		if(beta <= alpha) {
		    break;
		}
	    }
	}
	return bestSoFar;
    }
}
