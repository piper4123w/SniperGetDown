package Actor;

import javafx.scene.image.Image;

public class Explosion extends Actor {
	public int life = 10;

	public Explosion(double xPos, double yPos, double BR) {
		x = xPos;
		y = yPos;
		width = BR;
		height = BR;
		scalef = 1;
		img = new Image("objectAssets/explosion.png");
	}

	public void updateExplosion() {
		life--;
		if (life <= 0)
			img = null;
		r = Math.random() * 360;
	}
}
