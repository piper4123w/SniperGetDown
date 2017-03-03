package Objects;

import javafx.scene.image.Image;

public class CoverBlock extends Block {
	public final static int gridCode = 2;

	public CoverBlock(double x, double y, double size) {
		img = new Image("objectAssets/cover.gif", size, size, false, false);
		this.x = x + (size / 2);// center x and y
		this.y = y + (size / 2);

		scalef = 1;
		// System.out.println(cellSize + " " + scalef);
	}
}
