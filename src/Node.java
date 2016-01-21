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
	for(int i = 0; i < Board.width; i++) {
	    if(board.canDropADiscFromTop(i, turn)) {
	    	Node tmp = new Node (new Board(board), turn);
	    	tmp.board.dropADiscFromTop(i, turn);
	    	rtn.add(tmp);
	    }
	    if(board.canRemoveADiscFromBottom(i, turn)) {
	    	Node tmp = new Node(new Board(board),
	    			(turn * 2) % 3);
	    	tmp.board.removeADiscFromBottom(i);
		rtn.add(tmp);
		
	    }
	}
	return rtn;
    }

    public int heuristic() {
	int rtn = 0;
	for(int i=0;i< Board.height;i++){
	    max1=0;
	    max2=0;
	    for(int j=0;j< Board.width;j++){
		if(board[i][j]==1){
		    max1++;
		    max2=0;
		    rtn += max1;
		}
		else if(board[i][j]==2){
		    max1=0;
		    max2++;
		    rtn -= max2;
		}
		else{
		    max1=0;
		    max2=0;
		}
	    }
	}
	for(int j=0;j<this.width;j++){
	    max1=0;
	    max2=0;
	    for(int i=0;i<this.height;i++){
		if(board[i][j]==PLAYER1){
		    max1++;
		    max2=0;
		    rtn += max1;
		}
		else if(board[i][j]==PLAYER2){
		    max1=0;
		    max2++;
		    rtn -= max2;
		}
		else{
		    max1=0;
		    max2=0;
		}
	    }
	}
	int upper_bound=Board.height-1+Board.width-1-(Board.N-1);
	for(int k=Board.N-1;k<=upper_bound;k++){			
	    max1=0;
	    max2=0;
	    int x,y;
	    if(k<Board.width) 
		x=k;
	    else
		x=width-1;
	    y=-x+k;
			 
	    while(x>=0  && y < Board.height){
		if(board[Board.height-1-y][x] == 1){
		    max1++;
		    max2=0;
		    rtn += max1/2;
		}
		else if(board[Board.height-1-y][x] == 2){
		    max1=0;
		    max2++;
		    rtn -= max2/2;
		}
		else{
		    max1=0;
		    max2=0;
		}
		x--;
		y++;
	    }
	}
	upper_bound=Board.width-1-(N-1);
	int lower_bound=-(Board.height-1-(N-1));
	for(int k=lower_bound;k<=upper_bound;k++){
	    max1=0;
	    max2=0;
	    int x,y;
	    if(k>=0) 
		x=k;
	    else
		x=0;
	    y=x-k;
	    while(x>=0 && x<Board.width && y<Board.height){
		if(board[Board.height-1-y][x] == 1){
		    max1++;
		    max2=0;
		    rtn += max1/2;
		}
		else if(board[Board.height-1-y][x] == 2){
		    max1=0;
		    max2++;
		    rtn -= max2/2;
		}
		else{
		    max1=0;
		    max2=0;
		}
		x++;
		y++;
	    }	 
			 
	}
	return rtn;
    }
}
