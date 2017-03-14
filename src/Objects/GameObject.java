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
	double dx, dy, r, dr;
	public Image img;

	double scalef;
	protected double height, width;

	public BoundingBox boundingBox;

	// render method for object image
	public void render(GraphicsContext gc) {
		Affine affine = new Affine();

		gc.save();
		affine.appendTranslation(x - (width / 2), y - (height / 2));
		affine.appendRotation(r);
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

	// updates location and rotation based on velocity
	public void update() {
		r += dr;
		x += dx;
		y += dy;
	}

}
