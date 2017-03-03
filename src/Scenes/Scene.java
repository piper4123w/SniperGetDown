package Scenes;

import java.util.ArrayList;

import Display.Display;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class Scene {
	public GraphicsContext gc;
	public Display display;

	double cursorX, cursorY;

	boolean debug = true;

	ArrayList<Button> buttonList;
	Objects.World world;

	Color backGround;

	public void initScene(GraphicsContext gc, Display d) {
		this.gc = gc;
		this.display = d;
		buttonList = new ArrayList<Button>();
		if (this instanceof MainMenue)
			((MainMenue) this).initChildScene();
		if (this instanceof EditorScene)
			((EditorScene) this).initChildScene();
		if (this instanceof LevelSelect)
			((LevelSelect) this).initChildScene();
	}

	public String checkClick(double x, double y) {
		for (Button b : buttonList) {
			if (checkHover(b)) {
				if (!b.message.isEmpty()) {
					if (b.message.contains("SCENE"))
						return b.message.substring(b.message.indexOf(':') + 1);
					else {
						if (this instanceof EditorScene)
							((EditorScene) this).handleMessage(b.message);
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
			if (debug) {
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
		drawButtons();

	}

	private void fillBackground() {
		gc.setFill(backGround);
		gc.fillRect(0, 0, Display.WIDTH, Display.HEIGHT);
	}

}
