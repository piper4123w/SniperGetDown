package Scenes;

import Display.Display;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PlayerSelect extends Scene {
	String levelFile;

	public PlayerSelect(String s) {
		levelFile = s;
	}

	public void initChildScene() {
		backGround = Color.DARKOLIVEGREEN;
		if (Display.debug)
			System.out.println("SCENE:play," + levelFile + "*" + 2);
		buttonList.add(new Button("2 Player", Display.WIDTH / 2, Display.HEIGHT / 4,
				"SCENE:playLevel," + levelFile + "*" + 2, Display.WIDTH / 5, Display.HEIGHT / 7));
		buttonList.add(new Button("3 Player", Display.WIDTH / 2, 2 * Display.HEIGHT / 4,
				"SCENE:playLevel," + levelFile + "*" + 3, Display.WIDTH / 5, Display.HEIGHT / 7));
		buttonList.add(new Button("4 Player", Display.WIDTH / 2, 3 * Display.HEIGHT / 4,
				"SCENE:playLevel," + levelFile + "*" + 4, Display.WIDTH / 5, Display.HEIGHT / 7));
	}

	public void drawCharacters() {
		double scale = 0.4;
		Image img = new Image("menuItems/G_disp.png");
		gc.drawImage(img, 100, Display.HEIGHT / 2, img.getWidth() * scale, img.getHeight() * scale);
		img = new Image("menuItems/B_disp.png");
		gc.drawImage(img, 100 + 2 * (img.getWidth() * scale), Display.HEIGHT / 2, img.getWidth() * scale,
				img.getHeight() * scale);
		img = new Image("menuItems/R_disp.png");
		gc.drawImage(img, Display.WIDTH - (100 + 2 * (img.getWidth() * scale)), Display.HEIGHT / 2,
				img.getWidth() * scale, img.getHeight() * scale);
	}
}
