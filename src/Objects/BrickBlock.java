/*
 * Author: Kyle Lawson
 * 
 * Description: Sub class for brick block item. Object used for main construction of floors and walls
 */

package Objects;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class BrickBlock extends GameObject {
	public final static char gridCode = 'B';

	public BrickBlock(double x, double y, double s) {
		width = height = s;
		img = new Image("objectAssets/brick.gif");
		this.x = x + (s / 2);// center x and y
		this.y = y + (s / 2);

		scalef = 1;

		boundingBox = new BoundingBox(this.x - (width / 2), this.y - (width / 2), width, height);
	}

}
