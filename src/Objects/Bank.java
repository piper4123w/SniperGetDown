package Objects;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class Bank extends GameObject {
	public final static char gridCode = 'S';

	public Bank(double x, double y, double s) {
		size = s * 8;
		img = new Image("objectAssets/Bank.png", size, size, false, false);
		this.x = x + (size / 2);
		this.y = y + (size / 2);

		scalef = 1;

		boundingBox = new BoundingBox(x, y, size, size);
	}

}
