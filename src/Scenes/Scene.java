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
		if (this instanceof MainMenue)
			((MainMenue) this).initChildScene();
		if (this instanceof EditorScene)
			((EditorScene) this).initChildScene();
		if (this instanceof LevelSelect)
			((LevelSelect) this).initChildScene();
		if (this instanceof PlayScene)
			((PlayScene) this).initChildScene();
	}

	public String checkClick(double x, double y, boolean dragging) {
		for (Button b : buttonList) {
			if (checkHover(b)) {
				if (!b.message.isEmpty()) {
					if (Display.debug)
						System.out.println(b.message);
					if (b.message.contains("SCENE") && !dragging)
						return b.message.substring(b.message.indexOf(':') + 1);
					else {
						if (this instanceof EditorScene)
							((EditorScene) this).handleMessage(b.message, dragging);
						return null;
					}
				}
			}
		}
		if (this instanceof EditorScene) {
			((EditorScene) this).handleClick(x, y);
		}
		return null;
	}

	public boolean checkHover(Button b) {
		BoundingBox box = b.getBoundingBox();
		b.hoverEffect(box.contains(cursorX, cursorY));
		return box.contains(cursorX, cursorY);
	}

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

	public void updateScene(double x, double y) {
		cursorX = x;
		cursorY = y;

		fillBackground();

		for (Button b : buttonList) {
			checkHover(b);
		}
		if (world != null)
			world.drawWorld(gc);
		if(robberList != null && !robberList.isEmpty())
			for(Robber a : robberList)
				a.update(input, world, gc);
		drawButtons();

	}

	private void fillBackground() {
		gc.setFill(backGround);
		gc.fillRect(0, 0, Display.WIDTH, Display.HEIGHT);
	}

	public Objects.Grid getGrid() {
		return grid;
	}

}
