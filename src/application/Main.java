package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * CPT: Othello - a strategic two-player board game
 * 
 * <p> Class: Main </p>
 * 
 * @author Steven Mathew ICS4U1 - Bulhao, R.
 * @version 1.0
 *
 */

public class Main extends Application {

	/*
	 * TITLE          title of program
	 * SCENE_WIDTH    less typing for width of scene
	 * SCENE_HEIGHT   less typing for height of scene		
	 */	
	
	private static final String TITLE 	      = "Othello";
	private static final double SCENE_WIDTH   = 964;
	private static final double SCENE_HEIGHT  = 750;

	// declare and init the names of the close option buttons
	private static String[] btnOptionNames = {"red", "yellow", "green"};

	// declare and init a Pane for the gameBoard
	private Pane gameBoard;
	// declare and init an ImageView for the background image
	private ImageView bg = new ImageView(Loader.BG);

	// declare and init an array of Images for the frames of flips
	private List<Image> framesPlayer = new ArrayList<>();
	private List<Image> framesEnemy = new ArrayList<>();

	// declare an ImageView for the side UI
	private ImageView side;

	// declare a BorderPane
	private BorderPane root;

	// declare and init maincontroller object for transparent dragging
	private static MainController mc = new MainController();

	// declare and init main stage
	public static Stage primaryStage = null;

	// declare FileChooser, FastReader, and BufferedWriter objects
	private FileChooser fc;
	private FastReader fr;
	private BufferedWriter bw;

	// declare a boolean to check if the user has been prompted with an error
	private boolean prompted;

	// declare and init default difficulty and number of moves made since gameplay began
	public static int difficulty = 0;
	private int movesMade = 0;
	
	// declare and init boards, grids, tiles
	private Tile[][] tile = new Tile[8][8];
	private int[][] piece;
	private Board b;
	int[][] grid = new int[8][8];
	
	// max depth
	public static int maxn = 5; 
	// optimal board for pruning
	public static Board best;
	
	// declare and init VBox
	private VBox vb;
	
	// declare labels for score
	Label lblPlayerScore;
	Label lblCPUScore;
	
	/**
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */

	@Override
	public void start(Stage primaryStage) { 
		try { 
			// init the root
			root = new BorderPane();			

			// init the scene object
			Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

			// use makeStageDragable(BorderPane) from MainController class
			mc.makeStageDraggable(root);
			
			// init the game board and add the background image
			gameBoard = new Pane();   
			gameBoard.getChildren().add(bg);

			// loop through all tiles
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					// add the tile object and set properties
					tile[i][j] = new Tile(i, j);
					tile[i][j].setPrefWidth(61);
					tile[i][j].setPrefHeight(61);
					tile[i][j].setStyle("-fx-background-color: #009944; -fx-background-radius: 0");
					tile[i][j].setLayoutX(j * Tile.DEFAULT_TILE_SIZE + 65);
					tile[i][j].setLayoutY(i * Tile.DEFAULT_TILE_SIZE + 60);

					// add each Tile Object to the gameboard
					gameBoard.getChildren().add(tile[i][j]);
				}
			}

			// for each tile...
			for (int i = 0; i < tile.length; i++) {
				for (int j = 0; j < tile[0].length; j++) {
					final int _i = i, _j = j;

					// if the tile is pressed...
					tile[i][j].setOnAction(e-> {
						Tile cur = tile[_i][_j];

						// move there
						move(cur.getRow(), cur.getColumn());
					});
				}
			}

			// init the side UI	
			side = new ImageView(Loader.YOUR_TURN);

			// setting padding and margins
			gameBoard.setPadding(new Insets(0, -3, 0, 0));

			// add frames to player and enemy
			for (int i = 1; i <= 6; i++)
				framesPlayer.add(new Image("file:images/btow" + i + ".png"));
			for (int i = 1; i <= 6; i++)
				framesEnemy.add(new Image("file:images/wtob" + i + ".png"));

			// set the left to the game board 
			root.setLeft(gameBoard);

			// add style class
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// fill the scene
			scene.setFill(Color.TRANSPARENT);

			// setTop() block of root: 

			// grid pane for top portion of border pane
			GridPane topPanelSplitter = new GridPane();

			// declare and init MenuBar and add style class
			MenuBar menuBar = new MenuBar();
			menuBar.getStyleClass().add("menu-bar");

			// declare and init menu options and set styles
			Menu mnuFile = new Menu("File");
			Menu mnuView = new Menu("Options");
			mnuFile.getStyleClass().add("menu");
			mnuView.getStyleClass().add("menu");

			// in the File option, add an open menu button
			MenuItem miOpen = new MenuItem(String.format("%-15s", "Open"));
			// set keyboard shortcut to open button
			miOpen.setAccelerator(KeyCombination.keyCombination(String.format("%10s", "Ctrl+O")));

			// when the open button is pressed...
			miOpen.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					// init a file chooser
					fc = new FileChooser();
					// set the title of the Open menu
					fc.setTitle("Open Folder");
					// set the directory
					fc.setInitialDirectory(new File("..."));				
					// add extension filters
					fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files (*.txt)", "*.txt"),
							new ExtensionFilter("All Files", "*.*")); 		

					// show open file dialog
					File file = fc.showOpenDialog(primaryStage);

					// if the file exists...
					if (file != null) {
						// fill the seats in the plane according to the file passed
						fillCells(file.toString());	                
					}	
				}
			});


			// in the File option, add a save menu button
			MenuItem miSave = new MenuItem(String.format("%-15s", "Save"));
			// set keyboard shortcut to save button
			miSave.setAccelerator(KeyCombination.keyCombination(String.format("%10s", "Ctrl+S")));

			// when the save button is pressed...
			miSave.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					// save the file
					saveFile();
				}
			});

			// declare and init a menu item (this will be added to the View Menu option)
			MenuItem miRules = new MenuItem(String.format("%-15s", "Rules"));
			// set keyboard shortcut to Rules button
			miRules.setAccelerator(KeyCombination.keyCombination(String.format("%10s", "Ctrl+M")));
			// when the Rules button is pressed...
			miRules.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					// create an instance of the Rules and pass the passengers
					new Instructions();
				}
			});


			// declare and init a menu item
			MenuItem miDifficulty = new MenuItem(String.format("%-15s", "Difficulty"));
			// set keyboard shortcut to waiting button
			miDifficulty.setAccelerator(KeyCombination.keyCombination(String.format("%10s", "Ctrl+W")));
			// when the waiting button is pressed...
			miDifficulty.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					// create an instance of the WaitingList and pass the button array of seats
					new DifficultyMenu();
				}
			});

			// add open and save to File menu
			mnuFile.getItems().addAll(miOpen, miSave);
			// add Rules and waiting to View menu
			mnuView.getItems().addAll(miRules, miDifficulty);
			// add the Menus to the bar
			menuBar.getMenus().addAll(mnuFile, mnuView);

			// add the menu bar and set constraints
			GridPane.setConstraints(menuBar, 0, 0, 11, 1);

			// set the style of the menubar
			menuBar.setStyle("-fx-background-color: #22242A;");

			// topmost panel of scene
			BorderPane titleBar = new BorderPane();
			// set theme to dark mode
			titleBar.setStyle("-fx-background-color: #292C33");

			// hbox of all close options: close, minimize, maximize
			HBox closeOptions = new HBox();
			// set properties of hbox
			closeOptions.setPadding(new Insets(12, 12, 6, 18));
			closeOptions.setSpacing(10d);
			closeOptions.setStyle("-fx-background-color: #292C33");

			// declare btn array for all titlebar window options
			Button[] btnOptions = new Button[3];
			for (int i = 0; i < 3; i++) {
				// init btn at each index
				btnOptions[i] = new Button();
				// add style class of each button
				btnOptions[i].getStyleClass().add("btn-" + btnOptionNames[i]);

				// add btn to close option hbox
				closeOptions.getChildren().add(btnOptions[i]);
			}

			// when close button is pressed...
			btnOptions[0].setOnMousePressed(e -> {
				// declare and init alert of confirmation type
				Alert alertExit = new Alert(AlertType.CONFIRMATION);

				// set properties of alert...
				alertExit.setContentText("Are you sure you want to exit?");
				alertExit.setTitle(TITLE);
				alertExit.setHeaderText(null);
				alertExit.getButtonTypes().clear();

				// add yes or no option...
				alertExit.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
				// show dialog and wait for user to select yes or no
				Optional<ButtonType> result = alertExit.showAndWait();

				// if user selected no, close alert
				if (result.get() == ButtonType.NO) {
					e.consume();
				} else if (result.get() == ButtonType.YES) {
					Alert alertMessage = new Alert(AlertType.INFORMATION);
					// set properties of alert...
					alertMessage.setContentText("Thank you for playing Othello!");
					alertMessage.setTitle(TITLE);
					alertMessage.setHeaderText(null);

					// show alert and exit
					alertMessage.showAndWait();
					System.exit(0);
				}
			});

			// when minimize button is pressed...
			btnOptions[1].setOnMousePressed(e -> {
				// minimize
				primaryStage.setIconified(true);
			});

			// when maximize button is pressed...
			btnOptions[2].setOnMousePressed(e -> {	
				// deny the user with shake animation:

				// declare and init translate transition animation
				TranslateTransition ttMaximize = new TranslateTransition(Duration.millis(100), btnOptions[2]);
				// set properties of transition
				ttMaximize.setToX(2);
				ttMaximize.setCycleCount(4);
				ttMaximize.setAutoReverse(true);

				// play transition
				ttMaximize.play();
				// disable the button so that user cannot repress
				btnOptions[2].setDisable(true);

				// when animation is complete... re-enable button so it is pressable
				ttMaximize.setOnFinished(ee-> btnOptions[2].setDisable(false));
			});

			// set window options to left of borderpane titleBar so that it is topleft
			titleBar.setLeft(closeOptions);

			// declare and init coloumn constraints
			ColumnConstraints colConst = new ColumnConstraints();
			// set width to max
			colConst.setPercentWidth(100.0);

			// make top panel splitter take up full width of scene
			topPanelSplitter.getColumnConstraints().add(colConst);
			// add the titleBar and top Panel
			topPanelSplitter.add(menuBar, 0, 1);
			topPanelSplitter.add(titleBar, 0, 0);

			// set top of root border pane to topPanelSplitter
			root.setTop(topPanelSplitter);

			// end setTop() block

			// init the board and output with possible moves shown
			initBoard();
			b = new Board(piece);
			output();
			possibleMoves();
			
			// init VBox to the right of screen
			vb = new VBox();

			// init labels for scoring
			lblPlayerScore = new Label(" Player: 2");
			lblCPUScore = new Label(" CPU: 2");
			
			// set the margin of the vbox
			BorderPane.setMargin(vb, new Insets(13, 0, 0, 0));
			
			// add sideUI and labels
			vb.getChildren().addAll(side, lblPlayerScore, lblCPUScore);
			// set them to right of root
			root.setRight(vb);					

			// set properties of stage
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle(TITLE);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			// set private static primary stage to this method's primaryStage
			Main.primaryStage = primaryStage;
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}


	/**
	 * <h1> 
	 *   <i> 
	 *     <br>
	 *       initBoard
	 *     </br> 
	 *   </i>
	 * </h1>
	 * 
	 * <p>
	 *   <code>
	 *      &nbsp&nbsppublic void initBoard()
	 *   </code>
	 * </p>
	 * 
	 * <p>
	 *    A method that initializes the piece 2D array storing positions
	 * </p>
	 * 
	 * <br>
	 * 
	 * @version 1.0
	 * @author Steven Mathew
	 */
	
	public void initBoard() {
		// init default board settings
		piece = new int[8][8];
		piece[3][3] = piece[4][4] = 1;
		piece[3][4] = piece[4][3] = 2;
	}

	/**
	 * <h1> 
	 *   <i> 
	 *     <br>
	 *       move
	 *     </br> 
	 *   </i>
	 * </h1>
	 * 
	 * <p>
	 *   <code>
	 *      &nbsp&nbsppublic void move(int row, int col)
	 *   </code>
	 * </p>
	 * 
	 * <p>
	 *    A method that moves a player by row and col.
	 * </p>
	 * 
	 * <br>
	 * 
	 * @param row - int: the row to move to
	 * @param col - int: the col to move to
	 * @version 1.0
	 * @author Steven Mathew
	 */
	
	public void move(int row, int col) {
		/*BEGIN BASE CASES*/
		// if the player has no more moves return... the game ended
		if (b.getLegalMoves(Piece.BLACK).size() == 0) {
			initBoard();
			output();

			return;
		}

		// if the square is occupied, return
		if (b.piece[row][col] != 0)
			return;

		// if the move is illegal, return
		if (!b.isMoveLegal(row, col, Piece.BLACK))
			return;
		/*END BASE CASES*/

		// increment moves made
		movesMade++;
		
		// update the UI to the right with scores
		side = new ImageView(Loader.AI_TURN);
		vb.getChildren().clear();
		vb.getChildren().addAll(side, lblPlayerScore, lblCPUScore);
		BorderPane.setMargin(vb, new Insets(13, 0, 0, 0));

		// output the board to user
		output();
		
		// iterate through until depth of "it" is reached
		int it = 4;

		// copy the board
		int[][] temp = Board.memCopy(b.piece);

		// loop through paths
		for (int i = 1; i <= it; i++) {
			// set max depth to current i
			maxn = i;
			
			// recurse
			minimax(new Board(temp), true, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

			// store the current most optimal moves
			b = best;
		}

		// set the text
		lblPlayerScore.setText(" Player: " + b.getScore()[Piece.BLACK-1]);
		lblCPUScore.setText(" CPU: " + b.getScore()[Piece.WHITE-1]);
		
		// output and find possible moves for next iteration of game
		output();
		possibleMoves();
	}

	/**
	 * <h1> 
	 *   <i> 
	 *     <br>
	 *       possibleMoves
	 *     </br> 
	 *   </i>
	 * </h1>
	 * 
	 * <p>
	 *   <code>
	 *      &nbsp&nbsppublic void possibleMoves()
	 *   </code>
	 * </p>
	 * 
	 * <p>
	 *    A method that finds possible moves for the player to choose from
	 * </p>
	 * 
	 * <br>
	 * 
	 * @version 1.0
	 * @author Steven Mathew
	 */
	
	public void possibleMoves() {
		// declare and init a moves array that gets the legal moves of the player
		LinkedList<Board> moves = b.getLegalMoves(Piece.BLACK);

		// if there are no more moves...
		if (moves.size() == 0) {
			// get the scores and store for convenience
			int[] scores = b.getScore();
			int pScore = scores[Piece.BLACK-1];
			int aiScore = scores[Piece.WHITE-1];

			// if the user has made atleast one move 
			if (movesMade > 0) {
				// decide the winner...
				
				if (pScore > aiScore) {					
					confirmPrompt("You won with a score of " + pScore + " to " + aiScore + "!");				
				} else if (pScore < aiScore) {
					confirmPrompt("You lost with a score of " + pScore + " to " + aiScore + "!");				
				} else {
					confirmPrompt("You tied with a score of " + pScore + " to " + aiScore + "!");				
				}		
			}
		}

		// loop through all boards and get possible player moves
		for (Board i : moves) {
			// set the style to indicate to the user
			tile[i.prevRow][i.prevCol].setStyle("-fx-background-color: #1dd370");		
		}
	}
	
	/**
	 * <h1> 
	 *   <i> 
	 *     <br>
	 *       confirmPrompt
	 *     </br> 
	 *   </i>
	 * </h1>
	 * 
	 * <p>
	 *   <code>
	 *      &nbsp&nbsppublic void confirmPrompt(String s)
	 *   </code>
	 * </p>
	 * 
	 * <p>
	 *    A method that prompts the user to play again.
	 * </p>
	 * 
	 * <br>
	 * 
	 * @param s - String: a String to append to the label
	 * @version 1.0
	 * @author Steven Mathew
	 */
	
	public void confirmPrompt(String s) {
		// declare and init alert
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		// set properties of alert
		alert.setHeaderText(null);
		alert.setContentText(s + "\nWould you like to play again?");
		alert.setTitle(TITLE);
		
		// clear the buttons and add yes, no option
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
		
		// wait for the player's option
		Optional<ButtonType> result = alert.showAndWait();
		
		// if the user presses yes, restart
		if (result.get() == ButtonType.YES) {

			// reset according to default game board
			fillCells("normal_mode.txt");
			
		// otherwise,
		} else {
			// display new info alert
			Alert alertMessage = new Alert(AlertType.INFORMATION);
			// set properties of alert...
			alertMessage.setContentText("Thank you for playing!");
			alertMessage.setTitle("Goodbye");
			alertMessage.setHeaderText(null);

			// show alert and exit
			alertMessage.showAndWait();
			System.exit(0);
		}
	}

	/**
	 * <h1> 
	 *   <i> 
	 *     <br>
	 *       output
	 *     </br> 
	 *   </i>
	 * </h1>
	 * 
	 * <p>
	 *   <code>
	 *      &nbsp&nbsppublic void output() 
	 *   </code>
	 * </p>
	 * 
	 * <p>
	 *    A method that outputs the board.
	 * </p>
	 * 
	 * <br>
	 * 
	 * @version 1.0
	 * @author Steven Mathew
	 */
	
	public void output() {
		// for each position in the grid...
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {

				// for each tile...
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						// set the style to original
						tile[i][j].setStyle("-fx-background-color: #009944; -fx-background-radius: 0");
					}
				}

				// temporarily store the grid value
				int tmp = grid[y][x];
				// set the grid to the "after-flipping" version of the board at each index 
				grid[y][x] = b.piece[y][x];

				// if after the flip, the grid at the index changed and it was not blank...
				if (tmp != grid[y][x] && tmp != 0) {
					// play the animation and flip...
					
					// if it is now WHITE...
					if (grid[y][x] == Piece.WHITE) {
						// add a white piece
						Piece p = new Piece(y, x, Piece.WHITE);					
						gameBoard.getChildren().add(p);

						// declare and init Transition Object
						Transition animation = new Transition() {
							{
								setCycleDuration(Duration.millis(200));
							}

							@Override
							protected void interpolate(double fraction) {
								// store the index of the frame and set image
								int index = (int) (fraction*(framesPlayer.size()-1));
								p.setImage(framesPlayer.get(index)); 

								// if reached the end of the frames... it is the player's turn
								if (index == framesPlayer.size() - 1) {
									// update the UI to the right with scores
									vb.getChildren().clear();
									side = new ImageView(Loader.YOUR_TURN);
									vb.getChildren().addAll(side, lblPlayerScore, lblCPUScore);
									BorderPane.setMargin(vb, new Insets(13, 0, 0, 0));
								}
							}
						};
						// play the animation
						animation.play();	
					}
					// if the grid is now BLACK
					if (grid[y][x] == Piece.BLACK) {
						// create a Black piece and add
						Piece p = new Piece(y, x, Piece.BLACK);					
						gameBoard.getChildren().add(p);

						// declare and init Transition Object
						Transition animation = new Transition() {
							{
								setCycleDuration(Duration.millis(200));
							}

							@Override
							protected void interpolate(double fraction) {
								// store the index of the frame and set image
								int index = (int) (fraction*(framesEnemy.size()-1));
								p.setImage(framesEnemy.get(index)); 
							}
						};
						// play the animation
						animation.play();	

					}
					
				// otherwise, if after the flip, the grid at the index changed but it was blank...
				} else if (tmp != grid[y][x] && tmp == 0) {
					// do not play any animation and just create an instance of the piece at their respective locations
					
					// if it is WHITE
					if (grid[y][x] == Piece.WHITE) {
						// create a white piece
						Piece p = new Piece(y, x, Piece.WHITE);					
						gameBoard.getChildren().add(p);
					} else if (grid[y][x] == Piece.BLACK) {
						Piece p = new Piece(y, x, Piece.BLACK);					
						gameBoard.getChildren().add(p);
					}
				}
			}
		}
	}
	
	/**
	 * <h1> 
	 *   <i> 
	 *     <br>
	 *       minimax
	 *     </br> 
	 *   </i>
	 * </h1>
	 * 
	 * <p>
	 *   <code>
	 *      &nbsp&nbsppublic int minimax(Board b, boolean maximizingPlayer, int depth, int alpha, int beta)
	 *   </code>
	 * </p>
	 * 
	 * <p>
	 *    An algorithm for maximizing/minimizing all possibilities of moves to pick the most optimal solution (alpha-beta pruning)
	 *    The time complexity is O(b^(d/2)), where b is the branching factor (number of moves) and d is the maximum depth of the tree
	 *    My version of the algorithm uses a "corners captured" heuristic model. This model allows the AI to flank the player with corner pieces.
	 *
	 * 	  @see minimax.png for pseudo code (source: https://en.wikipedia.org/wiki/Minimax#Pseudocode)
	 * 
	 * </p>
	 * 
	 * <br>
	 * 
	 * 
	 * @param b - Board: a Board Object for the board positions
	 * @param maximizing - boolean: to optimize the player's moves
	 * @param depth - int: the current depth of the search tree
	 * @param 
	 * 
	 * @return the optimal 
	 * @version 1.0
	 * @author Steven Mathew
	 */
	
	public int minimax(Board b, boolean maximizing, int depth, int alpha, int beta) {
		/*BEGIN BASE CASES*/
		
		// if reached max depth and beyond, return corner heuristic value
		if (depth > maxn) {	
			return b.heuristic(difficulty);
		}

		// get all legal moves
		LinkedList<Board> moves = b.getLegalMoves((maximizing) ? (Piece.WHITE) : (Piece.BLACK));

		// if there are no more moves... end
		if (moves.size() == 0) {
			return (maximizing) ? (64) : (-64);
		}
		/*END BASE CASES*/
		
		// if the player move potential is being maximized...
		if (maximizing) {
			// declare and init int for best value
			int top = 0;

			// recur for all children in combinations of moves
			for (int i = 0; i < moves.size(); i++) {
				// recurse 
				int score = minimax(moves.get(i), false, depth + 1, alpha, beta);

				// maximize alpha
				if (score > alpha) {
					alpha = score;
					top = i;
				}

				// when alpha is greater, best maximized move is found
				if (alpha >= beta) {
					break;	
				}
			}

			// base case: if at head, get the best move out of all combinations
			if (depth == 0) best = moves.get(top);
			
			// return
			return alpha;
		// otherwise, minimize
		} else {
			// loop through all boards
			for (Board i : moves) {
				// recurse and store
				int score = minimax(i, true, depth + 1, alpha, beta);

				// minimize beta
				if (score < beta)
					beta = score;

				// when alpha is greater, best minimized move is found
				if (alpha >= beta)
					break;
			}

			// return 
			return beta;
		}
	}


	/**
	 * <h1> 
	 *   <i> 
	 *     <br>
	 *       saveFile
	 *     </br> 
	 *   </i>
	 * </h1>
	 * 
	 * <p>
	 *   <code>
	 *      &nbsp&nbsppublic void saveFile()
	 *   </code>
	 * </p>
	 * 
	 * <p>
	 *    A method that saves and writes to a file.
	 *    
	 *    Files are in the format of:
	 *    	- black pieces
	 *    	- white pieces
	 *    	- an 8x8 grid of pieces
	 *    	- difficulty
	 * </p>
	 * 
	 * <br>
	 * 
	 * @version 1.0
	 * @author Steven Mathew
	 */

	public void saveFile() {
		// init a file chooser
		fc = new FileChooser();

		// set the title of the Save menu
		fc.setTitle("Save File");
		// set the directory
		fc.setInitialDirectory(new File("..."));				
		// add extension filters
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files (*.txt)", "*.txt"),
				new ExtensionFilter("All Files", "*.*")); 		

		// show save file dialog
		File f = fc.showSaveDialog(primaryStage);				

		// if the file exists...
		if (f != null) {
			// try to read the file...
			try {						
				// init the BufferedWriter
				bw = new BufferedWriter(new FileWriter(f));

				// declare and init ints for the number of pieces for each player
				int blackPieces = b.getScore()[1];						
				int whitePieces = b.getScore()[0];

				// write them to the file
				bw.write(String.valueOf(blackPieces));
				bw.newLine();
				bw.write(String.valueOf(whitePieces));
				bw.newLine();

				// for all pieces...
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						// write the state of the piece
						bw.write(String.valueOf(b.piece[i][j]) + " ");
					}
					bw.newLine();
				}

				// write the difficulty to the file
				bw.write(String.valueOf(difficulty));
				bw.newLine();
				
				// close the BufferedWriter
				bw.close();

				// catch input/output exceptions
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}	
	}

	/**
	 * <h1> 
	 *   <i> 
	 *     <br>
	 *       fillCells
	 *     </br> 
	 *   </i>
	 * </h1>
	 * 
	 * <p>
	 *   <code>
	 *      &nbsp&nbspprivate void fillCell(String file)
	 *   </code>
	 * </p>
	 * 
	 * <p>
	 *    A method that fills the seats according to the file. 
	 *    
	 *    Files are in the format of:
	 *    - black pieces
	 *    - white pieces
	 *    - an 8x8 grid of pieces
	 *    - difficulty
	 * </p>
	 * 
	 * <br>
	 * 
	 * @param file - String: the directory of the file
	 * @version 1.0
	 * @author Steven Mathew
	 */

	private void fillCells(String file) {
		// try to read the file...
		try {
			// init a FastReader and pass the file
			fr = new FastReader(file.toString());

			// reset the prompt
			prompted = false;

			// declare and init vars for the number of passengers in the plane and waiting list
			int blackPieces = -1;
			int whitePieces = -1;

			// try to read the input
			try {			
				// read the int
				blackPieces = fr.readInt();
				whitePieces = fr.readInt();

				// set the text
				lblPlayerScore.setText(" Player: " + blackPieces);
				lblCPUScore.setText(" CPU: " + whitePieces);

				// catch any format errors
			} catch (NumberFormatException nfe) {
				// prompt the user with an error message
				if (!prompted) {
					promptError("Incorrect Input!");
					prompted = true;
				}        	
			}
			
			// reset the board
			reset();
	
			// for each row...
			for (int i = 0; i < 8; i++) {	

				// try to read the input...
				try {
					// read the next token and split by commas
					String[] tokens = fr.readLine().trim().split(" ");

					// parse and add to board
					for (int j = 0; j < 8; j++) {
						b.piece[i][j] = Integer.parseInt(tokens[j]);
					}

					// output and get all legal moves
					output();
					possibleMoves();

				// catch error in the input and throw to user 
				} catch (Exception e) {					
					// if user has not been prompted... prompt
					if (!prompted) {
						promptError("Incorrect Input!");
						prompted = true;
					}
				}		
			}

			// try and read the difficulty
			try {
				difficulty = Integer.parseInt(fr.readLine().trim());
			} catch (Exception e) {				
				// if user has not been prompted... prompt
				if (!prompted) {
					promptError("Incorrect Input!");
					prompted = true;
				}
			}

			// for each tile...
			for (int i = 0; i < tile.length; i++) {
				for (int j = 0; j < tile[0].length; j++) {
					// store temp nums for indices
					final int _i = i, _j = j;

					// if a tile is pressed...
					tile[i][j].setOnAction(e-> {
						// get the tile
						Tile cur = tile[_i][_j];

						// call move
						move(cur.getRow(), cur.getColumn());
					});
				}
			}

			// catch exception if the file is not found
		} catch (FileNotFoundException ex) {
			promptError("The file does not exist!");
		}

	}

	/**
	 * <h1> 
	 *   <i> 
	 *     <br>
	 *       reset
	 *     </br> 
	 *   </i>
	 * </h1>
	 * 
	 * <p>
	 *   <code>
	 *      &nbsp&nbsppublic void reset()
	 *   </code>
	 * </p>
	 * 
	 * <p>
	 *    A method that resets the state of the game.
	 * </p>
	 * 
	 * <br>
	 * 
	 * @version 1.0
	 * @author Steven Mathew
	 */
	
	public void reset() {
		// reset UI with score
		side = new ImageView(Loader.YOUR_TURN);
		vb.getChildren().clear();
		vb.getChildren().addAll(side, lblPlayerScore, lblCPUScore);
		BorderPane.setMargin(vb, new Insets(13, 0, 0, 0));

		// reset the moves made in the current instance of gameplay
		movesMade = 0;

		// clear the game board and add the image	
		gameBoard.getChildren().clear();
		gameBoard.getChildren().add(new ImageView(Loader.BG));

		// reset the board pieces
		b.piece = new int[8][8];

		// loop through all tiles...
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// add the tile object and set properties
				tile[i][j] = new Tile(i, j);
				tile[i][j].setPrefWidth(61);
				tile[i][j].setPrefHeight(61);
				tile[i][j].setStyle("-fx-background-color: #009944; -fx-background-radius: 0");
				tile[i][j].setLayoutX(j * Tile.DEFAULT_TILE_SIZE + 65);
				tile[i][j].setLayoutY(i * Tile.DEFAULT_TILE_SIZE + 60);

				// add each Tile Object to the gameboard
				gameBoard.getChildren().add(tile[i][j]);
			}
		}
	}

	/**
	 * <h1> 
	 *   <i> 
	 *     <br>
	 *       promptError
	 *     </br> 
	 *   </i>
	 * </h1>
	 * 
	 * <p>
	 *   <code>
	 *      &nbsp&nbsppublic static void promptError(String s)
	 *   </code>
	 * </p>
	 * 
	 * <p>
	 *    A method that prompts the user with an error.
	 * </p>
	 * 
	 * <br>
	 * 
	 * @param s - String: the String to be appended to the error message.
	 * @version 1.0
	 * @author Steven Mathew
	 */

	public static void promptError(String s) {
		// declare and init an alert
		Alert alertError = new Alert(AlertType.ERROR);
		// setting properties of the alert
		alertError.setTitle("Error");
		alertError.setHeaderText(null);
		alertError.setContentText(s);
		// show the alert and wait for the user
		alertError.showAndWait();
	}
}