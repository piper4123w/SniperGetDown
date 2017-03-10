package Objects;

import java.util.ArrayList;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class Robber extends GameObject {
	ArrayList<Image> sprites;
	String[] uris = { "idle_blink.png", "idle.png", "running1.png", "running2.png", "running3.png" };

	public Robber(int playerNum, double x, double y, double s) {
		sprites = new ArrayList<Image>(5);
		switch (playerNum) {
		case 1:
			loadSprites("G_");
			break;
		case 2:
			loadSprites("B_");
			break;
		default:
			break;
		}
		this.x = 200;
		this.y = 500;

		this.width = s;
		this.height = 2*s;

		scalef = 1;

		boundingBox = new BoundingBox(x, y, width, height);
	}

	public void loadSprites(String s) {
		for (String u : uris) {
			sprites.add(new Image("CharacterAssets/" + s + u));
		}
	}

	public void update() {

	}

	public void drawActor() {

	}
}
