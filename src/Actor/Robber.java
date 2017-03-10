package Actor;

import java.util.ArrayList;

import Objects.GameObject;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class Robber extends Actor {
	ArrayList<Image> sprites;
	String[] uris = { "idle_blink.png", "idle.png", "running1.png", "running2.png", "running3.png" };
	double headX, headY, headR;

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
		this.height = 2 * s;

		scalef = 1;
	}

	public void loadSprites(String s) {
		for (String u : uris) {
			sprites.add(new Image("CharacterAssets/" + s + u));
		}
	}

	public double getHeadX() {
		return headX;
	}

	public double getHeadY() {
		return headY;
	}

	public double getHeadR() {
		return headR;
	}

}
