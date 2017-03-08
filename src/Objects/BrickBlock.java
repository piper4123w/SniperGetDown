package Objects;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class BrickBlock extends GameObject {
	public final static char gridCode = 'B';

	public BrickBlock(double x, double y, double s) {
		size = s;
		img = new Image("objectAssets/brick.gif", size, size, false, false);
		this.x = x + (size / 2);// center x and y
		this.y = y + (size / 2);

		scalef = 1;

		boundingBox = new BoundingBox(x, y, size, size);
	}

}
