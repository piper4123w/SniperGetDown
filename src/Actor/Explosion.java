/* Author: Kyle Lawson
 * 
 * Description: Subclass of Actor. Handles explosion animation and delayed Tnt chaining
 * 
 */
package Actor;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Explosion extends Actor {
	public int life = 10;
	AudioClip boom;

	public Explosion(double xPos, double yPos, double BR) {
		x = xPos;
		y = yPos;
		width = BR;
		height = BR;
		scalef = 1;
		img = new Image("objectAssets/explosion.png");

		boom = new AudioClip(new File("src/audio/boom.wav").toURI().toString());

	}

	public void updateExplosion() {
		if (life == 10)
			boom.play();
		life--;
		if (life <= 0)
			img = null;
		r = Math.random() * 360;
	}
}
