/*
 * Author: Kyle Lawson
 * 
 * Description: child class handles the sniper reticle that follows the cursor.
 */

package Actor;

import java.io.File;

import Scenes.PlayScene;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Sniper extends Actor {
	AudioClip shot;

	public boolean clicked = false;
	public int clickCount = 0;
	private int clickRotCount;
	private final int shootCoolDown = 30;
	private final double scaleSpeed = 0.025;
	double velocity;

	private double destX, destY;

	private double dScale;

	public Sniper() {
		width = height = 100;
		img = new Image("objectAssets/reticle.gif");
		scalef = 1;

		shot = new AudioClip(new File("src/audio/sniperShot.mp3").toURI().toString());

	}

	// update method handling reticle location and animation
	public void update() {
		double distX = destX - x;
		double distY = destY - y;
		x += distX / 20;
		y += distY / 20;
		if (clicked && clickCount == 0) { // just clicked the mouse so start
											// click animation
			if (Display.Display.ActiveScene instanceof PlayScene)
				shot.play();

			clickCount = shootCoolDown;
			clickRotCount = clickCount / 2;
			if (Math.random() > 0.5) // chose random rotation direction
				dr = 1;
			else
				dr = -1;
			dScale = scaleSpeed; // change of scale of reticle image every
									// update
			clicked = false;
		}

		if (clickRotCount == 0) { // if clickRotCount counter reaches 0, start
									// to return to normal
			dr = -dr;
			dScale = -scaleSpeed;
			clickRotCount = clickCount;
		} else { // decrement counters for animation timing
			clickRotCount--;
			clickCount--;
			clicked = false;
		}
		if (clickCount == 0) { // animation timer over, return to normal
								// position
			dr = 0;
			r = 0;
			dScale = 0;
		}

		scalef += dScale;
		super.update();

	}

	// sets Position of reticle (usually following with cursor)
	public void setPosition(double x2, double y2) {
		destX = x2;
		destY = y2;
	}

}
