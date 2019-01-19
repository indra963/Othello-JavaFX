package application;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * CPT: Othello - a strategic two-player board game
 * 
 * <p> Class: Instructions - an Object that shows the rules of the game that extends Stage </p>
 * 
 * @author Steven Mathew
 * @version 1.0
 *
 */

public class Instructions  extends Stage {
	
	/**
	 * <p>
	 * 	A no-arg constructor for the Instructions class.
	 * </p>
	 * 
	 * @version 1.0
	 * @author Steven Mathew
	 */ 
	
	public Instructions() {	
		// declare and init a scroll pane
		ScrollPane root = new ScrollPane();
		// setting padding of root
		root.setPadding(new Insets(20, 10, 20, 10));

		// declare and init a TextFlow object
		TextFlow flow = new TextFlow();
	
		/* ADDING TEXT TO TextFlow*/
		
		// declare and init a title and set style
		Text title = new Text("Othello\n");
		title.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

		// declare and init a description of the game and set style
		Text briefDescription = new Text();
		briefDescription.setStyle("-fx-font-size: 14px;");
		briefDescription.setText("Othello is a two-player strategy game played on an 8x8 board with pieces colored "
				+ "white on one side and black\non the other. You will try to play the pieces black side up while the AI tries to play" 
				+ " the discs white side up.\n\n");
		
		// declare and init the objective heading and set style
		Text object = new Text("Objective\n");
		object.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

		// define the objective and set style
		Text objective = new Text();
		objective.setStyle("-fx-font-size: 14px;");
		objective.setText("Your goal is to place the pieces on the board to flip the opponent's disc to your color. The "
				+ "player with the most discs\nby the end of the game wins.\n\n");
		
		// declare and init rules heading and set style
		Text rules = new Text("Rules\n");
		rules.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
		
		// define the rules of the game and set style
		Text rulesList = new Text();
		rulesList.setStyle("-fx-font-size: 14px;");
		rulesList.setText("\t1.  Every game starts with four discs in the center of the board.\n\n\t\t");
		
		// declare and init an image figure example
		ImageView fig1 = new ImageView(new Image("file:images/rulesFigure1.gif"));
		
		// continue the rules list
		Text rulesListCont = new Text();
		rulesListCont.setStyle("-fx-font-size: 14px;");
		rulesListCont.setText("\n\n\t2.  Players take turns making moves. A move is one in which a player places a disc\n"
				+ "\tof his/her color on the board. The disc must be placed so as to overtake opponent's discs\n"
				+ "\twhich are then flipped over to the current player's color.\n\n"
				+ "\tOvertaking an opponent occurs when:\n\t\t- an opponent's disc is trapped between "
				+ "another disc of your own color either\n\t\t horizontally, vertically or diagonally.\n\n\tSee the figures below:\n"
				+ "\tThe game begins with the initial set up (see the above figure) and Black decides to place a disc at C4\n"
				+ "\t(from the moves: C4, D3, E6, or F5).\n\n\t");
		
		// declare and init an image two more figure examples
		ImageView fig2 = new ImageView(new Image("file:images/rulesFigure2.gif"));
		ImageView fig3 = new ImageView(new Image("file:images/rulesFigure3.gif"));
		
		// continue the rules list
		Text rulesListCont2 = new Text();
		rulesListCont2.setStyle("-fx-font-size: 14px;");
		rulesListCont2.setText("\n\n\t3.  Legal moves are those which allow for players to overtake an opponent's disc.\n"
				+ "\tIf a player cannot make a legal move, he/she forfeits his turn and the other player moves again.\n"
				+ "\tThe game ends when neither player can make a legal move.\n\n"
				+ "\t4.  The player with the most discs of his/her color by the end of the game wins. The game is tied when\n"
				+ "\tboth players have the same number of pieces.\n\n");
		
		// declare and init a controls heading with style
		Text controls = new Text("Controls\n");
		controls.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
		
		// define the controls and style
		Text controlsDescription = new Text();
		controlsDescription.setStyle("-fx-font-size: 14px;");
		controlsDescription.setText("Left-click with the mouse to select a cell and place a black piece.");

		// add all text and images to TextFlow Object
		flow.getChildren().addAll(title, briefDescription, object, objective, rules, rulesList, fig1, rulesListCont, 
				fig2, fig3, rulesListCont2, controls, controlsDescription);
		
		/*END ADDING TEXT TO TextFlow*/

		// set content of root 
		root.setContent(flow);
		
		// declare and init Scene
		Scene scene = new Scene(root, 840, 400);
			
		// setting properties of Stage...
		setResizable(false);
		setTitle("Rules");
		setScene(scene);
		show();
	}
}
