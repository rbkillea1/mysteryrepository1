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
		rtn.add(new Node(new Board(board).removeADiscFromBottom(i)),
			(turn * 2) % 3);
	    }
	    if(board.canDropADiscFromTop(i, turn)) {
		rtn.add(new Board(board).removeADiscFromTop(i, turn),
			(turn * 2) % 3);
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
