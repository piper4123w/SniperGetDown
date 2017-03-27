/*
 * Author: Kyle Lawson
 * 
 * Description: Super class for Actors, similar to game object but containing different bounding box definitions
 */

package Actor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import javafx.geometry.BoundingBox;

public class Actor {
	String dir = "right";
	public double x, y;
	double dx, dy, r, dr;
	public Image img;

	double scalef;
	protected double height, width;

	public BoundingBox boundingBox;

	// renders the Actor image
	public void render(GraphicsContext gc) {
		Affine affine = new Affine();
		gc.save();
		affine.appendTranslation(x, y);

		affine.appendRotation(r);
		affine.appendScale(scalef * (width / img.getWidth()), scalef * (height / img.getHeight()));
		gc.setTransform(affine);
		if (dir.equals("right"))
			gc.drawImage(img, 0 - (img.getWidth() / 2), 0 - (img.getHeight() / 2), img.getWidth(), img.getHeight());
		else
			gc.drawImage(img, 0 + (img.getWidth() / 2), 0 - (img.getHeight() / 2), -img.getWidth(), img.getHeight());
		gc.restore();
	}

	// updates location data based on velocities
	public void update() {
		r += dr;
		x += dx;
		y += dy;
		if (this instanceof Explosion) {
			if (((Explosion) this).life-- <= 0) {
				width = height = 0;
			}
		}
	}
}
