import java.util.ArrayList;

public class Node {
    public Board board;
    public int turn = 1;
    public Node(Board board, int turn) {
    	this.board = board;
    	this.turn = turn;
    }
    public ArrayList<Node> children() {
	ArrayList<Node> rtn = new ArrayList<Node>();
	for(int i = 0; i < board.width; i++) {
	    if(board.canRemoveADiscFromBottom(i, turn)) {
	    	Node tmp = new Node(new Board(board),
	    			(turn * 2) % 3);
	    	tmp.board.removeADiscFromBottom(i);
		rtn.add(tmp);
		
	    }
	    if(board.canDropADiscFromTop(i, turn)) {
	    	Node tmp = new Node (new Board(board), turn);
	    	tmp.board.dropADiscFromTop(i, turn);
	    	rtn.add(tmp);
	    }
	}
	return rtn;
    }
    
    public boolean isTerminal() {
	return board.isConnectN() != -1;
    }

    public int heuristic() {
	return 2*(board.isConnectN() & turn) - 1;
    }
}
