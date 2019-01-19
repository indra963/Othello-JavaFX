package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * CPT: Othello - a strategic two-player board game
 * 
 * <p> Class: Piece - an Object for a game piece that extends an ImageView object</p>
 * 
 * @author Steven Mathew
 * @version 1.0
 *
 */

public class Piece extends ImageView {
	
	// private fields:
	
	// declare frog img, width, height and state
	private Image img;
	@SuppressWarnings("unused")
	private double width, height;
	private int state;
	
	// public fields:
	
	// declare and init constants for direction
	public static final int WHITE = 1, BLACK = 2;
	
	/**
	 * <p>
	 * A no-arg constructor for the Piece class that is set to default values.
	 * </p>
	 * 
	 * @version 1.0
	 * @author Steven Mathew
	 */ 
	
	Piece() {
		// call super constructor
		super();
		
		// set the default direction to north
		state = BLACK;
		
		// set the image to normal frog sprite
		img = Loader.B_PIECE;
		super.setImage(img);
		super.setScaleX(2); super.setScaleY(2);

		
		// set the width to the img widths
		width = img.getWidth();
		height = img.getHeight();
	}
	
	/**
	 * <p>
	 * A constructor for the Piece class that is set to user-desired values.
	 * </p>
	 * 
	 * @param img - Image: the image for the Frog
	 * @version 1.0
	 * @author Steven Mathew
	 */ 
	
	Piece(Image img) {	
		// call super constructor
		super();
		
		// set the default direction to north
		state = BLACK;

		// set the image to the image passed
		this.img = img;
		super.setImage(img);	
		super.setScaleX(2); super.setScaleY(2);

		// set the width to the img widths
		width = img.getWidth();
		height = img.getHeight();
	}
	

	/**
	 * <p>
	 * A constructor for the Piece class that is set to user-desired values.
	 * </p>
	 * 
	 * @param row - int: the row of the piece
	 * @param col - int: the col of the piece
	 * @param state - int: the state of the piece (WHITE = 1, BLACK = 2).
	 * @version 1.0
	 * @author Steven Mathew
	 */ 
	
	Piece(int row, int col, int state) {	
		// call super constructor
		super();
		
		this.state = state;
		
		if (state == Piece.BLACK) {
			this.img = Loader.B_PIECE;
			super.setImage(img);	
		} else {
			this.img = Loader.W_PIECE;
			super.setImage(img);	
		}
		
		setRow(row);
		setColumn(col);
		
		// set the width to the img widths
		width = img.getWidth();
		height = img.getHeight();
	}
	
	 /**
     * 
     * Getter Methods
     * 
     * @version 1.0
     * @author Steven Mathew
     * 
     */
	
	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       getHeight
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic double getHeight()
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that gets the height of the Piece as an <strong>int</strong>.
     * </p>
     * 
     * <br>
     * 
     * @return The height of the Piece as an int.
     * @version 1.0
     * @author Steven Mathew
     */
	
	public double getHeight() {
		return img.getHeight();
	}
		
	/**
     * <h1> 
     *   <i> 
     *     <br>
     *       getWidth
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic double getWidth()
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that gets the width of the Piece as an <strong>int</strong>.
     * </p>
     * 
     * <br>
     * 
     * @return The width of the Piece as an int.
     * @version 1.0
     * @author Steven Mathew
     */
	
	public double getWidth() {
		return img.getWidth();
	}
	
	/**
     * <h1> 
     *   <i> 
     *     <br>
     *       getState
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic int getState() 
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that gets the state of the Piece as an <strong>int</strong> (WHITE = 1, BLACK = 2).
     * </p>
     * 
     * <br>
     * 
     * @return The side the Piece is facing (WHITE = 1, BLACK = 2).
     * @version 1.0
     * @author Steven Mathew
     */
	
	public int getState() {
		return state;
	}
	
	/**
     * 
     * Setter Methods
     * 
     * @version 1.0
     * @author Steven Mathew
     * 
     */
			
	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       setHeight
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic void setHeight(double h) 
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that sets the height of the Piece.
     * </p>
     * 
     * <br>
     * 
     * @param h - double: the height of the Piece.
     * @version 1.0
     * @author Steven Mathew
     */
	
	public void setHeight(double h) {
		height = h;
	}
	
	/**
     * <h1> 
     *   <i> 
     *     <br>
     *       setWidth
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic void setWidth(double w) 
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that sets the width of the Piece.
     * </p>
     * 
     * <br>
     * 
     * @param w - double: the width of the Piece.
     * @version 1.0
     * @author Steven Mathew
     */
	
	public void setWidth(double w) {
		width = w;
	}
	
	/**
     * <h1> 
     *   <i> 
     *     <br>
     *       setX
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic void setX(int xx)
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that sets the x-position of the Piece.
     * </p>
     * 
     * <br>
     * 
     * @param xx - int: the x-position of the Piece.
     * @version 1.0
     * @author Steven Mathew
     */
	
	public void setX(int xx) {
		super.setLayoutX(xx);
	}
	
	/**
     * <h1> 
     *   <i> 
     *     <br>
     *       setY
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic void setY(int yy)
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that sets the y-position of the Piece.
     * </p>
     * 
     * <br>
     * 
     * @param yy - int: the y-position of the Piece.
     * @version 1.0
     * @author Steven Mathew
     */ 
	
	public void setY(int yy) {
		super.setLayoutY(yy);
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
     *    A method that sets the row of the Piece.
     * </p>
     * 
     * <br>
     * 
     * @param row - int: the row of the Piece.
     * @version 1.0
     * @author Steven Mathew
     */ 
	
	public void setRow(int row) {
		super.setLayoutY(row * (67) + 60);
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
     *    A method that sets the column of the Piece.
     * </p>
     * 
     * <br>
     * 
     * @param col - int: the column of the Piece.
     * @version 1.0
     * @author Steven Mathew
     */ 
	
	public void setColumn(int col) {
		super.setLayoutX(col * (67) + 65); 
	}
	
	/**
     * <h1> 
     *   <i> 
     *     <br>
     *       setState
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic void setState(int state)
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that sets the state of the Piece.
     * </p>
     * 
     * <br>
     * 
     * @param dir - int: the state of the Piece (WHITE = 1, BLACK = 2).
     * @version 1.0
     * @author Steven Mathew
     */
	
	public void setState(int state) {
		this.state = state;
		
		// if the state is BLACK, set the image to black; otherwise, if WHITE, set the image to WHITE
		if (state == Piece.BLACK) super.setImage(Loader.B_PIECE);
		else super.setImage(Loader.W_PIECE);
	}	
}
