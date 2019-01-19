package application;

import javafx.scene.control.Button;

/**
 * CPT: Othello - a strategic two-player board game
 * 
 * <p> Class: Tile - an Object for a board square that extends a Button object</p>
 * 
 * @author Steven Mathew
 * @version 1.0
 *
 */

public class Tile extends Button {
		
	// row and col of the tile
	private int row, col;
	
	// declare a static int for Tile size
	public static int DEFAULT_TILE_SIZE = 67;
	
	/**
	 * <p>
	 * A no-arg constructor for the Tile class that is set default values.
	 * </p>
	 * 
	 * @param img - Image: the image for the Tile
	 * @version 1.0
	 * @author Steven Mathew
	 */ 
	
	Tile() {
		this.row = 0;
		this.col = 0;
	}
	
	/**
	 * <p>
	 * A constructor for the Tile class that is set to user-desired values.
	 * </p>
	 * 
	 * @param row - int: the row of the piece
	 * @param col - int: the col of the piece
	 * @version 1.0
	 * @author Steven Mathew
	 */ 
	
	Tile(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
     * <h1> 
     *   <i> 
     *     <br>
     *       setRow
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic int getRow() 
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that gets the row of the tile.
     * </p>
     * 
     * <br>
     * 
     * @return row - int: the row of the Tile.
     * @version 1.0
     * @author Steven Mathew
     */ 
	
	public int getRow() {
		return row;
	}

	/**
     * <h1> 
     *   <i> 
     *     <br>
     *       setColumn
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic int getColumn() 
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that gets the column of the tile.
     * </p>
     * 
     * <br>
     * 
     * @return col - int: the column of the Tile.
     * @version 1.0
     * @author Steven Mathew
     */ 
	
	public int getColumn() {
		return col;
	}
	
	
	/**
     * <h1> 
     *   <i> 
     *     <br>
     *       setRow
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic void setRow(int row)
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that sets the row of the Tile.
     * </p>
     * 
     * <br>
     * 
     * @param row - int: the row of the Tile.
     * @version 1.0
     * @author Steven Mathew
     */ 
	
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
     * <h1> 
     *   <i> 
     *     <br>
     *       setColumn
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic void setColumn(int col)
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that sets the column of the Tile.
     * </p>
     * 
     * <br>
     * 
     * @param col - int: the column of the Tile.
     * @version 1.0
     * @author Steven Mathew
     */ 
	
	public void setColumn(int col) {
		this.col = col;
	}
}
