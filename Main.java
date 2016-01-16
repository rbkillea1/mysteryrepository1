public class Main {
    public static int rows, columns, connectN, timeLimit;
    public static boolean player1, goingFirst;
    public static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    public static String name = "Ryan and Adilet";
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
	String s=input.readLine();
	List<String> ls=Arrays.asList(s.split(" "));
	player1 = ls.get(1).equals(name);
	// Read the line telling the parameters of the game	
	String s=input.readLine();
	List<String> ls = Arrays.asList(s.split(" "));
	rows = Integer.parseInt(ls.get(0));
	columns = Integer.parseInt(ls.get(1));
	connectN = Integer.parseInt(ls.get(2));
	goingFirst = (Integer.parseInt(ls.get(3)) == 1) ^ player1;
	timeLimit = Integer.parseInt(ls.get(4));
	board = new Board(rows, columns, connectN);
	if(goingFirst) {
	    board.dropADiscFromTop(columns/2, 1);
	}
	while(true) {
	    String s=input.readLine();
	    if(s.length >= 3) {
		processInput(Integer.parseInt(s.substring(0,1)), Integer.parseInt(s.substring(2,3)));
	    }
        }
    }
}
