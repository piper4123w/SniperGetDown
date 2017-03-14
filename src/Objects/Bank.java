/*
 * Author: Kyle Lawson
 * 
 * Description: Sub Class for Bank object (start position for robbers)
 */
package Objects;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class Bank extends GameObject {
	public final static char gridCode = 'S';

	public Bank(double x, double y, double s) {
		height = s * 7;
		width = s * 8;
		img = new Image("objectAssets/Bank.png");
		this.x = x + (width / 2);
		this.y = y + (height / 2);

		scalef = 1;

		boundingBox = new BoundingBox(x, y, width, height);
	}

}
