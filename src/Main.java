import java.util.Random;
public class Main {

	static final int BOARDSIZE = 10;

	public void printBoard(String[][] b){
		for (int i = 0; i < BOARDSIZE; i++){
			for (int j = 0; j < BOARDSIZE; j++){
				System.out.print(b[i][j]);
			}
			System.out.println();
		}
	}

	public int checkDead(String[][] b, int i, int j, String condition){
		int dead = 0;
		String[] chars;
		//determine how many squares to check
		if (condition != "none" && condition != "corner")
			chars = new String[5];
		else if (condition == "corner")
			chars = new String[3];
		else
			chars = new String[8];

		//Check in clockwise orientation
		if(condition == "top"){
			chars[0] = b[i][j + 1];
			chars[1] = b[i+1][j + 1];
			chars[2] = b[i+1][j];
			chars[3] = b[i+1][j-1];
			chars[4] = b[i][j-1];
		}
		else if (condition == "right"){
			chars[0] = b[i+1][j];
			chars[1] = b[i+1][j-1];
			chars[2] = b[i][j-1];
			chars[3] = b[i-1][j-1];
			chars[4] = b[i-1][j];
		}
		else if (condition == "left"){
			chars[0] = b[i-1][j];
			chars[1] = b[i-1][j+1];
			chars[2] = b[i][j+1];
			chars[3] = b[i+1][j+1];
			chars[4] = b[i+1][j];
		}
		else if (condition == "down"){
			chars[0] = b[i][j-1];
			chars[1] = b[i-1][j-1];
			chars[2] = b[i-1][j];
			chars[3] = b[i-1][j+1];
			chars[4] = b[i][j+1];
		}
		else if (condition == "corner"){
			if (i == 0 && j == 0) {
				chars[0] = b[i][j+1];
				chars[1] = b[i+1][j+1];
				chars[2] = b[i+1][j];
			}
			else if (i == 0 && j == BOARDSIZE - 1) {
				chars[0] = b[i+1][j];
				chars[1] = b[i+1][j-1];
				chars[2] = b[i][j-1];
			}
			else if (i == BOARDSIZE - 1 && j == 0){
				chars[0] = b[i-1][j];
				chars[1] = b[i-1][j+1];
				chars[2] = b[i][j+1];
			}
			else if ( i == BOARDSIZE - 1 && j == BOARDSIZE - 1) {
				chars[0] = b[i][j-1];
				chars[1] = b[i-1][j-1];
				chars[2] = b[i-1][j];
			}
		}
		else{
			chars[0] = b[i-1][j];
			chars[1] = b[i-1][j+1];
			chars[2] = b[i][j+1];
			chars[3] = b[i+1][j+1];
			chars[4] = b[i+1][j];
			chars[5] = b[i+1][j-1];
			chars[6] = b[i][j-1];
			chars[7] = b[i-1][j-1];
		}
		//Only count those that are alive.
		for(String s : chars){
			if (s == "O")
				dead += 1;
		}

		return dead;
	}

    public static void main(String[] args) {
		//For helper functions
		Main m = new Main();

	    String[][] board = new String[BOARDSIZE][BOARDSIZE];
	    int alive = 0, percent = 22;

	    //For randomizing the initial configuration
	    Random rand = new Random();
	    for (int i = 0; i < BOARDSIZE; i++){
	         for (int j = 0; j < BOARDSIZE; j++){
	         	if (rand.nextInt(99) < percent) {
	         		board[i][j] = "O";
					alive++;
				}
				else{
	         		board[i][j] = "x";
				}
			 }
        }

		m.printBoard(board);
	    System.out.println("\n" + alive);

		while (alive > 0){
			int nearDead = 0;
			String[][] tempBoard = board;
			for (int i = 0; i < BOARDSIZE; i++){
				for (int j = 0; j < BOARDSIZE; j++){
					if (i == 0 && j == 0 || i == 0 && j == BOARDSIZE - 1 ||
							i == BOARDSIZE - 1 && j == 0 || i == BOARDSIZE - 1 && j == BOARDSIZE - 1){
						nearDead = m.checkDead(board, i, j, "corner");
					}
					else if (i == 0){
						nearDead = m.checkDead(board, i, j, "top");
					}
					else if (j == BOARDSIZE - 1){
						nearDead = m.checkDead(board, i, j, "right");
					}
					else if (j == 0){
						nearDead = m.checkDead(board, i, j, "left");
					}
					else if (i == BOARDSIZE - 1){
						nearDead = m.checkDead(board, i, j, "down");
					}

					else{
						nearDead = m.checkDead(board, i, j, "none");
					}
					if (nearDead == 3 && board[i][j].equals("x")){
						tempBoard[i][j] = "O";
						alive += 1;
					}
					else if (nearDead > 3 || nearDead < 2){
						if (board[i][j].equals("O")) {
							alive -= 1;
						}
						tempBoard[i][j] = "x";

					}
				}
			}
			board = tempBoard;
			m.printBoard(board);
			System.out.println("\n" + alive + " alive\n\n");
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException ex)
			{
				Thread.currentThread().interrupt();
			}
		}
    }
}
