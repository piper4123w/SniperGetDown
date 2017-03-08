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
	protected double size;

	BoundingBox boundingBox;

	public void render(GraphicsContext gc) {
		Affine affine = new Affine();
		// gc.save();
		// affine.appendScale(scalef, scalef);
		// affine.appendTranslation(x / scalef, y / scalef);
		// affine.appendRotation(r);
		// gc.setTransform(affine);
		gc.save();
		affine.appendTranslation(x - (size / 2), y - (size / 2));
		affine.appendRotation(r);
		affine.appendScale(scalef, scalef);
		gc.setTransform(affine);
		gc.drawImage(img, 0, 0);

		if (Display.Display.debug && boundingBox != null) {
			gc.setStroke(Color.BLACK);
			gc.strokeRect(0, 0, boundingBox.getWidth(), boundingBox.getHeight());
		}
		gc.restore();

	}

	public void update() {
		r += dr;
		x += dx;
		y += dy;
	}

}
