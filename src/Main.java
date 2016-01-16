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
	timeLimit = Integer.parseInt(ls1.get(4));
	board = new Board(rows, columns, connectN);
	if(goingFirst) {
	    board.dropADiscFromTop(columns/2, 1);
	}
	while(true) {
	    String s11 = null;
		try {
			s11 = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if(s11.length() >= 3) {
		processInput(Integer.parseInt(s11.substring(0,1)), Integer.parseInt(s11.substring(2,3)));
	    }
        }
    }

    public int alphaBeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {
	if(depth == 0 || node.isTerminal()) {
	    return node.heuristic();
	}
	ArrayList<Node> children = node.children();
	int bestSoFar;
	if(maximizingPlayer) {
	    bestSoFar = Integer.MIN_VALUE;
	    for(Node c : children) {
		bestSoFar = max(bestSoFar, alphaBeta(c, depth - 1, alpha, beta, false));
		alpha = max(alpha, bestSoFar);
		if(beta <= alpha) {
		    break;
		}
	    }
	} else {
	    bestSoFar = Integer.MAX_VALUE;
	    for(Node c : children) {
		bestSoFar = min(bestSoFar, alphaBeta(c, depth - 1, alpha, beta, true));
		beta = min(bestSoFar, beta);
		if(beta <= alpha) {
		    break;
		}
	    }
	}
	return bestSoFar;
    }
}
