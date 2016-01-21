import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static int rows, columns, connectN, timeLimit;
    public static boolean player1, goingFirst;
    public static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    public static String name = "RyanAndAdilet";
    public static Board board;
    public static long turnStartTime;
    public static int BRANCH_FACTOR_DROP = 4;
    private static void processInput(Integer column, Integer moveType) {
	if(moveType == 1) {
	    board.dropADiscFromTop(column, 2);
	} else {
	    board.removeADiscFromBottom(column);
	}
	
	// print output at the end of this function
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
	goingFirst = (Integer.parseInt(ls1.get(3)) == 1) ^ player1;
	turn = goingFirst? 1 : 2;
	timeLimit = Integer.parseInt(ls1.get(4));
	board = new Board(rows, columns, connectN);
	if(goingFirst) {
	    board.dropADiscFromTop(columns/2, 1);
	    System.out.println(
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
    public ArrayList<Integer> determineMove() {
	ArrayList<Integer> rtn = new ArrayList<Integer>();
	rtn.add(3);
	rtn.add(1);
	int bestValue = Integer.MIN_VALUE;
	for(int i = 0; i < Board.width; i++) {
	    if(board.canDropADiscFromTop(i, turn)) {
	    	Node tmp = new Node (new Board(board), turn);
	    	tmp.board.dropADiscFromTop(i, turn);
		int candidateValue = alphaBeta(tmp, DEPTH, bestValue, Integer.MIN_VALUE, false);
	        if(candidateValue > bestValue) {
		    bestValue = candidateValue;
		    rtn.set(0, i);
		    rtn.set(1, 1);
		    if(candidateValue = Integer.MAX_VALUE) break;
		}
	    }
	    if(board.canRemoveADiscFromBottom(i, turn)) {
	    	Node tmp = new Node(new Board(board),
	    			(turn * 2) % 3);
	    	tmp.board.removeADiscFromBottom(i);
		int candidateValue = alphaBeta(tmp, DEPTH, bestValue, Integer.MIN_VALUE, false);
	        if(candidateValue > bestValue) {
		    bestValue = candidateValue;
		    rtn.set(0, i);
		    rtn.set(1, 2);
		    if(candidateValue = Integer.MAX_VALUE) break;
		}
	    }
	}
	return rtn;
    }
    
    public int alphaBeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {
	int isConnectN = node.board.isConnectN()
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
