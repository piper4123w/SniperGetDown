package Scenes;

import javafx.scene.paint.Color;
import Display.Display;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;

public class MainMenue extends Scene {
	Image img;

	public void initChildScene() {
		backGround = Color.AQUA;
		buttonList.add(new Button(new Image("menuItems/LevelSelect.gif"), Display.WIDTH / 2, 2 * Display.HEIGHT / 4,
				0.75, "SCENE:levelSelect"));
		buttonList.add(new Button(new Image("menuItems/LevelEditor.gif"), Display.WIDTH / 2, 3 * (Display.HEIGHT / 4),
				.75, "SCENE:levelEditor"));

		img = new Image("menuItems/title.png");
	}

	void drawTitle() {
		Affine affine = new Affine();
		gc.save();
		affine.appendTranslation(Display.WIDTH / 2 - (img.getWidth()/2), 10);
		affine.appendScale(500 / img.getWidth(), 300 / img.getHeight());
		gc.setTransform(affine);
		gc.drawImage(img, 0, 0);
		gc.restore();
	}

}
