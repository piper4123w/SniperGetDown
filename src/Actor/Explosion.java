package Actor;

import javafx.scene.image.Image;

public class Explosion extends Actor {
	double x, y, BR;
	int life = 10;

	public Explosion(double x, double y, double BR) {
		this.x = x;
		this.y = y;
		this.BR = BR;
		width = height = BR;
		img = new Image("objectAssets/explosion.png");
		System.out.println("Created new explosion at " + x + " " + y + " " + BR);
	}
}
