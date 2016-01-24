/* @authors: Ryan Killea and Adilet Issayev*/
public class Board {
	
    public static int width;
    public static int height;
    public int board[][];
    public int numOfDiscsInColumn[];
    public int emptyCell=9;
    public static int N;
    public int PLAYER1=1;
    public int PLAYER2=2;
    public int NOCONNECTION=-1;
    public int TIE=0;
    public boolean[] canRemoveDiscs = {true, true};
    public Board(int height1, int width1, int N1){
	width=width1;
	height=height1;
	board =new int[height][width];
	for(int i=0;i<height;i++)
	    for(int j=0;j<width;j++){
		board[i][j]=this.emptyCell;
	    }
	numOfDiscsInColumn=new int[Board.width];
	//		for(int j=0;j<width;j++)
	//			numOfDiscsInColumn[j]=0;
	N=N1;
    }

    public Board(Board toClone) {
    	this.board = new int[height][width];
    	for(int i = 0; i < toClone.board.length; i++) {
    		for(int j = 0; j < toClone.board[0].length; j++) {
    			board[i][j] = toClone.board[i][j];
    		}
    	}
    	this.numOfDiscsInColumn = new int[toClone.numOfDiscsInColumn.length];
    	for(int i = 0; i < toClone.numOfDiscsInColumn.length; i++) {
    		this.numOfDiscsInColumn[i] = toClone.numOfDiscsInColumn[i];
    	}
    	this.canRemoveDiscs = new boolean[2];
    	this.canRemoveDiscs[0] = toClone.canRemoveDiscs[0];
    	this.canRemoveDiscs[1] = toClone.canRemoveDiscs[1];
    }
    
    public void printBoard(){
		 System.out.println("Board: ");
		 for(int i=0;i<Board.height;i++){
				for(int j=0;j<Board.width;j++){
					System.out.print(board[i][j]+" ");
				}
				System.out.println();
		 }
	 }
	 
    public boolean canRemoveADiscFromBottom(int col, int currentPlayer){
	if(col >= width || board[height-1][col]!=currentPlayer || !canRemoveDiscs[currentPlayer-1]){
	    return false;
	} else {
	    return true;
	}
    }
	 
	 
	 
    public void removeADiscFromBottom(int col){
	int i;
    canRemoveDiscs[board[height-1][col] - 1] = false;
	for(i=height-1;i>height-this.numOfDiscsInColumn[col];i--){
	    board[i][col]=board[i-1][col];
	}
	board[i][col]=this.emptyCell;
	this.numOfDiscsInColumn[col]--;
    }
	 
	 
    public boolean canDropADiscFromTop(int col, int currentPlayer){
	if(col >= width || this.numOfDiscsInColumn[col] == height){
	    return false;
	}
	else
	    return true;
    }
	 
    public void dropADiscFromTop(int col, int currentplayer){
	int firstEmptyCellRow=height-this.numOfDiscsInColumn[col]-1;
	board[firstEmptyCellRow][col]=currentplayer;
	this.numOfDiscsInColumn[col]++;
    }
	 
    /**
     * Check if one of the players gets N checkers in a row (horizontally, vertically or diagonally) 
     *  @return the value of winner. If winner=-1, nobody win and game continues; If winner=0/TIE, it's a tie;
     * 			If winner=1, player1 wins; If winner=2, player2 wins. 
     */
	 
    public int isConnectN(){
	int tmp_winner=checkHorizontally();
		
	if(tmp_winner!=this.NOCONNECTION)
	    return tmp_winner;
		
	tmp_winner=checkVertically();
	if(tmp_winner!=this.NOCONNECTION)
	    return tmp_winner;
		 
	tmp_winner=checkDiagonally1();
	if(tmp_winner!=this.NOCONNECTION)
	    return tmp_winner; 
	tmp_winner=checkDiagonally2();
	if(tmp_winner!=this.NOCONNECTION)
	    return tmp_winner; 
		 
	return this.NOCONNECTION;
		 
    }
	 
    public int checkHorizontally(){
	int max1=0;
	int max2=0;
	boolean player1_win=false;
	boolean player2_win=false;
	//check each row, horizontally
	for(int i=0;i<this.height;i++){
	    max1=0;
	    max2=0;
	    for(int j=0;j<this.width;j++){
		if(board[i][j]==PLAYER1){
		    max1++;
		    max2=0;
		    if(max1==N)
			player1_win=true;
		}
		else if(board[i][j]==PLAYER2){
		    max1=0;
		    max2++;
		    if(max2==N)
			player2_win=true;
		}
		else{
		    max1=0;
		    max2=0;
		}
	    }
	} 
	if (player1_win && player2_win)
	    return this.TIE;
	if (player1_win)
	    return this.PLAYER1;
	if (player2_win)
	    return this.PLAYER2;
		 
	return this.NOCONNECTION;
    }

    public int checkVertically(){
	//check each column, vertically
	int max1=0;
	int max2=0;
	boolean player1_win=false;
	boolean player2_win=false;
		 
	for(int j=0;j<this.width;j++){
	    max1=0;
	    max2=0;
	    for(int i=0;i<this.height;i++){
		if(board[i][j]==PLAYER1){
		    max1++;
		    max2=0;
		    if(max1==N)
			player1_win=true;
		}
		else if(board[i][j]==PLAYER2){
		    max1=0;
		    max2++;
		    if(max2==N)
			player2_win=true;
		}
		else{
		    max1=0;
		    max2=0;
		}
	    }
	} 
	if (player1_win && player2_win)
	    return this.TIE;
	if (player1_win)
	    return this.PLAYER1;
	if (player2_win)
	    return this.PLAYER2;
		 
	return this.NOCONNECTION;
    }
  
    public int checkDiagonally1(){
	//check diagonally y=-x+k
	int max1=0;
	int max2=0;
	boolean player1_win=false;
	boolean player2_win=false;
	int upper_bound=height-1+width-1-(N-1);
	   
	for(int k=N-1;k<=upper_bound;k++){			
	    max1=0;
	    max2=0;
	    int x,y;
	    if(k<width) 
		x=k;
	    else
		x=width-1;
	    y=-x+k;
			 
	    while(x>=0  && y<height){
		if(board[height-1-y][x]==PLAYER1){
		    max1++;
		    max2=0;
		    if(max1==N)
			player1_win=true;
		}
		else if(board[height-1-y][x]==PLAYER2){
		    max1=0;
		    max2++;
		    if(max2==N)
			player2_win=true;
		}
		else{
		    max1=0;
		    max2=0;
		}
		x--;
		y++;
	    }	 
			 
	}
	if (player1_win && player2_win)
	    return this.TIE;
	if (player1_win)
	    return this.PLAYER1;
	if (player2_win)
	    return this.PLAYER2;
		 
	return this.NOCONNECTION;
    }
	 
    public int checkDiagonally2(){
	//check diagonally y=x-k
	int max1=0;
	int max2=0;
	boolean player1_win=false;
	boolean player2_win=false;
	int upper_bound=width-1-(N-1);
	int  lower_bound=-(height-1-(N-1));
	for(int k=lower_bound;k<=upper_bound;k++){			
	    max1=0;
	    max2=0;
	    int x,y;
	    if(k>=0) 
		x=k;
	    else
		x=0;
	    y=x-k;
	    while(x>=0 && x<width && y<height){
		if(board[height-1-y][x]==PLAYER1){
		    max1++;
		    max2=0;
		    if(max1==N)
			player1_win=true;
		}
		else if(board[height-1-y][x]==PLAYER2){
		    max1=0;
		    max2++;
		    if(max2==N)
			player2_win=true;
		}
		else{
		    max1=0;
		    max2=0;
		}
		x++;
		y++;
	    }	 
			 
	}	 //end for y=x-k
		 
	if (player1_win && player2_win)
	    return this.TIE;
	if (player1_win)
	    return this.PLAYER1;
	if (player2_win)
	    return this.PLAYER2;
		 
	return this.NOCONNECTION;
    }
   
    public boolean isFull(){
	for(int i=0;i<height;i++)
	    for(int j=0;j<width;j++){
		if(board[i][j]==this.emptyCell)
		    return false;
	    }
	return true;
    }
	 
	 
    public void setBoard(int row, int col, int player){
	if(row>=height || col>=width)
	    throw new IllegalArgumentException("The row or column number is out of bound!");
	if(player!=this.PLAYER1 && player!=this.PLAYER2)
	    throw new IllegalArgumentException("Wrong player!");
	this.board[row][col]=player;
    }
}
