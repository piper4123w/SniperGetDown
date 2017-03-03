package Objects;

import javafx.scene.image.*;
import javafx.geometry.*;

public class Block extends GameObject {
	BoundingBox boundingBox;

	public BoundingBox getBoundingBox() {
		double width = img.getWidth();
		double height = img.getHeight();
		return new BoundingBox(x, y, width, height);

	}

}
