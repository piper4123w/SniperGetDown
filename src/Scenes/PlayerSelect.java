package Scenes;

import Display.Display;
import javafx.scene.paint.Color;

public class PlayerSelect extends Scene {
	public PlayerSelect(String s) {
		backGround = Color.DARKOLIVEGREEN;
		if (Display.debug)
			System.out.println("SCENE:play," + s + "*" + 2);
		buttonList.add(new Button("2 Player", Display.WIDTH / 2, Display.HEIGHT / 3, "SCENE:play," + s + "*" + 2,
				Display.WIDTH / 5, Display.HEIGHT / 7));
	}

	public void initChildScene() {

	}
}
