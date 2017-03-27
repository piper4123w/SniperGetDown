/*
 * Author: Kyle Lawson
 * 
 * Description: Superclass for all in game objects not associated with a player. Contains update and render methods as well as location and physical data
 */

package Objects;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class GameObject extends Objects.World {
	public double x, y;
	public Image img;

	double scalef;
	public double height;
	public double width;

	public BoundingBox boundingBox;

	// render method for object image
	@SuppressWarnings("unused")
	public void render(GraphicsContext gc) {
		if (img != null) {
			Affine affine = new Affine();

			gc.save();
			affine.appendTranslation(x - (width / 2), y - (height / 2));
			affine.appendScale(scalef * (width / img.getWidth()), scalef * (height / img.getHeight()));
			gc.setTransform(affine);
			gc.drawImage(img, 0, 0);
			gc.restore();

			// displays the bounding box if in debug mode
			if (Display.Display.debug && boundingBox != null) {
				gc.setStroke(Color.BLACK);
				gc.strokeRect(boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getWidth(),
						boundingBox.getHeight());

			}
		}

	}
}
