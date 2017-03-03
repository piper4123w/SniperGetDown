package Scenes;

import javafx.scene.paint.Color;
import Display.Display;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;

public class MainMenue extends Scene {

	public void initChildScene() {
		backGround = Color.AQUA;
		buttonList.add(new Button(new Image("menuItems/LevelSelect.gif"), Display.WIDTH / 2, Display.HEIGHT / 4, 0.75,
				"SCENE:levelSelect"));
		buttonList.add(new Button(new Image("menuItems/LevelEditor.gif"), Display.WIDTH / 2, 2 * (Display.HEIGHT / 4),
				.75, "SCENE:levelEditor"));
	}

}
