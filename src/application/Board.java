package application;

import java.util.LinkedList;

/**
 * CPT: Othello - a strategic two-player board game
 * 
 * <p> Class: Board - an Object for the game board</p>
 * 
 * @author Steven Mathew
 * @version 1.0
 *
 */

public class Board {

	// public fields:

	// 8 row 8 grid
	public int[][] piece;

	// prev positions in board
	public int prevRow, prevCol; 


	/**
	 * <p>
	 * 	A constructor for the Board class that is set to user-desired values.
	 * </p>
	 * 
	 * @param piece - a 2D int array of the grid
	 * @version 1.0
	 * @author Steven Mathew
	 */ 

	public Board(int[][] piece) {
		this.piece = piece;	
	}

	
	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       isMoveLegal
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic boolean isMoveLegal(int row, int col, int player)
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that checks if a move is legal or not.
     * </p>
     * 
     * <br>
     * 
     * @return legal - boolean: returns true if move is legal; otherwise, false
     * @version 1.0
     * @author Steven Mathew
     */
	
	public boolean isMoveLegal(int row, int col, int player) {

		if (piece[row][col] != 0) 
			return false;

		// declare and init bool for if a move is legal or not
		boolean legal = false;

		// declare and init array for cardinal dirs
		int[] card = {-1, 0, 1};


		// loop through: W, NW, N, NE, E, SE, S, SW
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// ignore center
				if (card[i] == 0 && card[j] == 0)
					continue;

				// declare and init vars for finding valid moves
				boolean flip = false;
				boolean overtake = false;
				int k = 1;

				// while within the board...
				while (row + card[j] * k >= 0 && row + card[j] * k < 8 && col + card[i] * k >= 0 && col + card[i] * k < 8) { 
					int posY = row + card[j] * k;
					int posX = col + card[i] * k;


					if (piece[posY][posX] == 0 || piece[posY][posX] == player && !overtake) {
						break;
					}

					if (piece[posY][posX] == player && overtake) {
						flip = true;
						break;
					} else { 					
						if (piece[posY][posX] == Piece.BLACK || piece[posY][posX] == Piece.WHITE) {
							overtake = true;
							k++;
						}
					}
				}

				// if can flip... 
				if (flip) {					
					// set piece to current player
					piece[row][col] = player;

					// reverse dirs flip back to original location
					for (int h = 1; h <= k; h++) {
						piece[row + card[j] * h][col + card[i] * h] = player;
					}

					// the move is valid
					legal = true;
				}
			}
		}

		// set the prevrow/col to the current row/col
		this.prevRow = row;
		this.prevCol = col;

		return legal;
	}

	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       getScore
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic int[] getScore()
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that gets the scores of both players.
     * </p>
     * 
     * <br>
     * 
     * @return int[]: returns both scores as an int array (index 0 is WHITE, index 1 is BLACK)
     * @version 1.0
     * @author Steven Mathew
     */
	
	public int[] getScore() {
		// declare and init counter vars for score
		int cnt1 = 0;
		int cnt2 = 0;

		// loop through grid
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				// increment counter vars for each color...
				
				if (piece[i][j] == Piece.WHITE)
					cnt1++;
				else if (piece[i][j] == Piece.BLACK)
					cnt2++;
			}

		// return the scores as an int
		return new int[] {cnt1, cnt2};
	}

	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       heuristic
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic int heuristic(int difficulty)
     *   </code>
     * </p>
     * 
     * <p>
     *    A "corners captured" heuristic model. This model allows the AI to flank the player with corner pieces.
     * </p>
     * 
     * <br>
     * 
     * @param the difficulty of the AI (0 for EASY or 1 for HARD)
     * @return the difference of the scores as an int
     * @version 1.0
     * @author Steven Mathew
     */
	
	public int heuristic(int difficulty) {
		// get the scores of both players
		int[] score = getScore();
		
		// store the scores in vars (for readability)
		int score1 = score[0];
		int score2 = score[1];

		// declare and init a bonus for the AI
		int bonus = 10;
		
		// evaluate each corner and add to each player's score...
		
		if (piece[0][0] == Piece.WHITE) score1 += bonus;
		if (piece[0][7] == Piece.WHITE) score1 += bonus;		
		if (piece[7][0] == Piece.WHITE) score1 += bonus;		
		if (piece[7][7] == Piece.WHITE) score1 += bonus;		
		if (piece[0][0] == Piece.BLACK) score2 += bonus;		
		if (piece[0][7] == Piece.BLACK) score2 += bonus;		
		if (piece[7][0] == Piece.BLACK) score2 += bonus;		
		if (piece[7][7] == Piece.BLACK) score2 += bonus;
		

		// return the delta score depending on the difficulty of the Ai
		if (difficulty == 1) return score1 - score2;
		return score2 - score1;
	}


	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       getLegalMoves
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic LinkedList<Board> getLegalMoves(int player)
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that returns a list of 64 combinations of boards
     * </p>
     * 
     * <br>
     * 
     * @param player - int: the current player to evaluate
     * @return temp - LinkedList<Board>: 64 board combinations
     * @version 1.0
     * @author Steven Mathew
     */
	
	public LinkedList<Board> getLegalMoves(int player) {
		
		// store a temp linkedlist for ALL 64 boards
		LinkedList<Board> temp = new LinkedList<Board>();
		// copy the current board
		Board b = new Board(memCopy(piece));

		// loop through all boards
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// check if the move is legal and add to list of boards
				if (b.isMoveLegal(i, j, player)) {
					temp.add(b);
					b = new Board(memCopy(piece)); 
				}
			}
		}

		return temp;
	}

	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       memCopy
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic static int[][] memCopy(int[][] piece)
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that returns a copy of the array.
     * </p>
     * 
     * <br>
     * 
     * @param piece - int[][]: the array to copy
     * @return arr - int[][]: the copied array
     * @version 1.0
     * @author Steven Mathew
     */
	
	public static int[][] memCopy(int[][] piece) {
		// declare and init an array for the temporary grid
		int[][] arr = new int[8][8];
		
		// loop through the board and copy
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				arr[i][j] = piece[i][j];
			}
		}
		
		// return the temp grid
		return arr;
	}
}