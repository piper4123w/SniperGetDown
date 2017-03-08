package Objects;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class CoverBlock extends GameObject {
	public final static int gridCode = 'C';

	public CoverBlock(double x, double y, double s) {
		size = s;
		img = new Image("objectAssets/cover.gif", size, size, false, false);
		this.x = x + (size / 2);// center x and y
		this.y = y + (size / 2);

		scalef = 1;
		// System.out.println(cellSize + " " + scalef);
		boundingBox = new BoundingBox(x, y, img.getWidth(), img.getHeight());
	}
}
