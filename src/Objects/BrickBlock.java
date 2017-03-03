package Objects;

import javafx.scene.image.Image;

public class BrickBlock extends Block {
	public final static int gridCode = 1;

	public BrickBlock(double x, double y, double size) {
		img = new Image("objectAssets/brick.gif", size, size, false, false);
		this.x = x + (size / 2);// center x and y
		this.y = y + (size / 2);

		scalef = 1;
	}

}
