/*
 * Author: Kyle Lawson
 * 
 * Description: Sub Class for Van Object
 */

package Objects;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class Van extends GameObject {
	public final static char gridCode = 'V';

	public Van(double x, double y, double s) {
		height = s * 3;
		width = s * 4;
		img = new Image("objectAssets/Van.png");
		this.x = x + (width / 2);
		this.y = y + (height / 2);

		scalef = 1;

		boundingBox = new BoundingBox(x, y, width, height);
	}
}
