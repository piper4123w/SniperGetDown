/* Author: Kyle Lawson
 * 
 * Description: Super class handling data transfer for all sub scenes
 * 
 */

package Scenes;

import java.util.ArrayList;

import Actor.Robber;
import Display.Display;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Scene {
	public GraphicsContext gc;
	public Display display;

	double cursorX, cursorY;

	ArrayList<Button> buttonList;
	ArrayList<Robber> robberList;
	public ArrayList<String> input;

	protected Objects.Grid grid;
	Objects.World world;

	Color backGround;

	public void initScene(GraphicsContext gc, Display d) {
		this.gc = gc;
		this.display = d;
		buttonList = new ArrayList<Button>();
		input = new ArrayList<String>();
		//these instance of messages are required here because all of the child scenes 
		//contain different init functions
		if (this instanceof MainMenue)
			((MainMenue) this).initChildScene();
		if (this instanceof EditorScene)
			((EditorScene) this).initChildScene();
		if (this instanceof LevelSelect)
			((LevelSelect) this).initChildScene();
		if (this instanceof PlayScene)
			((PlayScene) this).initChildScene();
	}

	//checks click/drag based on location
	public String checkClick(double x, double y, boolean dragging) {
		for (Button b : buttonList) {
			if (checkHover(b)) {	//if hovering on button in scene
				if (!b.message.isEmpty()) {	//button contains message
					if (Display.debug)
						System.out.println(b.message);
					//message calls for scene switch
					if (b.message.contains("SCENE") && !dragging)	
						return b.message.substring(b.message.indexOf(':') + 1);
					else {
						if (this instanceof EditorScene)
							((EditorScene) this).handleMessage(b.message, dragging);
						if (this instanceof PlayScene)
							((PlayScene) this).handleMessage(b.message, dragging);
							//special handler for editor scene
						return null;
					}
				}
			}
		}
		//if clicking in non-button location
		if (this instanceof EditorScene) {
			((EditorScene) this).handleClick(x, y);
		}
		return null;
	}

	//checks if mouse is over button box
	public boolean checkHover(Button b) {
		BoundingBox box = b.getBoundingBox();
		b.hoverEffect(box.contains(cursorX, cursorY));
		return box.contains(cursorX, cursorY);
	}

	//draws buttons and their bounding box if in debug mode
	protected void drawButtons() {
		for (Button b : buttonList) {
			b.render(gc);
			if (Display.debug) {
				gc.setStroke(Color.BLACK);
				BoundingBox bb = b.getBoundingBox();
				gc.strokeRect(bb.getMinX(), bb.getMinY(), bb.getWidth(), bb.getHeight());
			}
		}
	}

	//scene update method
	public void updateScene(double x, double y) {
		cursorX = x;
		cursorY = y;

		fillBackground();	//fill background

		for (Button b : buttonList) {	//hovering over button with no click
			checkHover(b);	//will show hover animation
		}
		
		if (world != null)
			world.drawWorld(gc);
		if(robberList != null && !robberList.isEmpty())
			for(Robber a : robberList)
				a.update(input, world, gc);
		drawButtons();

	}

	//fills background with color set in child scene
	private void fillBackground() {
		gc.setFill(backGround);
		gc.fillRect(0, 0, Display.WIDTH, Display.HEIGHT);
	}

	public Objects.Grid getGrid() {
		return grid;
	}

}
