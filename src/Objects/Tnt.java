/*
 * Author: Kyle Lawson
 * 
 * Description: Sub class for Tnt block item. Object explodes and kills robbers or Tnt in area
 */

package Objects;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class Tnt extends GameObject {
	public final static char gridCode = 'T';
	public final double blastRadius = 100;
	public boolean dead = false;

	Tnt(double x, double y, double s) {
		width = height = s;
		img = new Image("objectAssets/TNT.png");
		this.x = x + (s / 2);// center x and y
		this.y = y + (s / 2);

		scalef = 1;

		boundingBox = new BoundingBox(this.x - (width / 2), this.y - (width / 2), width, height);
	}

	public void blowUp() {
		if (!dead) {
			width = height = 0;
			img = null;
			x = y = -100;
		}

		dead = true;
	}

}
