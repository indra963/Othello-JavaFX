package application;

import javafx.scene.layout.BorderPane;

/**
 * CPT: Othello - a strategic two-player board game
 * 
 * <p> Class: MainController - an Object that makes the stage draggable</p>
 * 
 * @author Steven Mathew
 * @version 1.0
 *
 */

public class MainController {

	// x, y offset of initial mouse press before drag
    public double xOffSet = 0;
    public double yOffSet = 0;

	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       makeStageDraggable
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsp public void makeStageDraggable(BorderPane parent)
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that makes the stage draggable and changes the opactity.
     * </p>
     * 
     * <br>
     * 
     * @param parent - BorderPane: the stage that should be changed
     * @version 1.0
     * @author Steven Mathew
     */
    
    public void makeStageDraggable(BorderPane parent) {  
    	// store x,y offset on press
    	parent.setOnMousePressed(e -> {
            xOffSet = e.getSceneX();
            yOffSet = e.getSceneY();
        });
        
    	// when dragging...
        parent.setOnMouseDragged(e -> {
        	// shift the stage
            Main.primaryStage.setX(e.getScreenX() - xOffSet);
            Main.primaryStage.setY(e.getScreenY() - yOffSet);
        });
        
        // when drag is complete...
        parent.setOnDragDone(e -> {
        });
        
        // or when mouse released...
        parent.setOnMouseReleased(e -> {
        	// set opacity to 100%
            Main.primaryStage.setOpacity(1.0f);
        });
    }
}
