/*
 * Author: Kyle Lawson
 * 
 * Description: child class handles the sniper reticle that follows the cursor.
 */

package Actor;

import javafx.scene.image.Image;

public class Sniper extends Actor {

	public boolean clicked = false;
	public int clickCount = 0;
	private int clickRotCount;
	private final int shootCoolDown = 16;
	private final double scaleSpeed = 0.025;

	private double dScale;

	public Sniper() {
		width = height = 100;
		img = new Image("objectAssets/reticle.gif");
		scalef = 1;

	}

	// update method handling reticle location and animation
	public void update() {
		if (clicked && clickCount == 0) { // just clicked the mouse so start
											// click animation
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
		x = x2;
		y = y2;
	}

}
