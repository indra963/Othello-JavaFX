package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * CPT: Othello - a strategic two-player board game
 * 
 * <p> Class: DifficultyMenu - an Object that allows users to set the difficulty of the game that extends Stage </p>
 * 
 * @author Steven Mathew
 * @version 1.0
 *
 */

public class DifficultyMenu extends Stage {

	/**
	 * <p>
	 * 	A no-arg constructor for the DifficultyMenu class.
	 * </p>
	 * 
	 * @version 1.0
	 * @author Steven Mathew
	 */ 

	public DifficultyMenu() {

		// declare and init FlowPane
		BorderPane root = new BorderPane();
		// setting properties of root
		root.setPadding(new Insets(20, 10, 20, 10));

		// declare and init a toggle group
        ToggleGroup rbGroup = new ToggleGroup();
        
        // declare and init difficulty RadioButton Objects
        RadioButton rbEasy = new RadioButton("Easy");
        RadioButton rbHard  = new RadioButton("Hard");
        
        // add the RadioButtons to the toggle group
        rbEasy.setToggleGroup(rbGroup);
        rbHard.setToggleGroup(rbGroup);
        
        // if the difficulty is easy/hard, automatically set the toggle to easy/hard
        if (Main.difficulty == 0) {
        	rbGroup.selectToggle(rbEasy);
        } else {
        	rbGroup.selectToggle(rbHard);
        }
        
        // setting alignment and margins of nodes
		BorderPane.setAlignment(rbEasy, Pos.TOP_CENTER);
		BorderPane.setAlignment(rbHard, Pos.TOP_CENTER);
		BorderPane.setMargin(rbEasy, new Insets(5));
		BorderPane.setMargin(rbHard, new Insets(5));
        
		// declare and init a button object
        Button confirm = new Button();
        confirm.setText("Confirm");
        
        // when the confirm button is pressed...
        confirm.setOnAction(e-> {
        	// if the selected toggle is not null...
        	if (rbGroup.getSelectedToggle() != null) {
        		// if easy is selected...
	        	if (rbGroup.getSelectedToggle().equals(rbEasy)) {
	        		// set the difficulty and close
	        		Main.difficulty = 0;
	        		close();
	        	// otherwise, hard is selected
	        	} else {
	        		// set the difficulty and close
	        		Main.difficulty = 1;
	        		close();
	        	}
        	}
        });       

        // setting alignment and margins of nodes
		BorderPane.setMargin(confirm, new Insets(5));
		BorderPane.setAlignment(confirm, Pos.TOP_CENTER);
      
		// add the RadioButtons and Button to root
        root.setTop(rbEasy);
        root.setCenter(rbHard);
        root.setBottom(confirm);
              
		// declare and init Scene
		Scene scene = new Scene(root, 250, 100);

		// setting properties of Stage
		setResizable(false);
		setTitle("Difficulty");
		setScene(scene);
		show();
	}
}
