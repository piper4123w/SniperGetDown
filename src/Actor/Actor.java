package Actor;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.geometry.BoundingBox;

public class Actor {
	public double x, y;
	double dx, dy, r, dr;
	public Image img;

	double scalef;
	protected double height, width;
	public ArrayList<String> input;

	public BoundingBox boundingBox;

	public void render(GraphicsContext gc) {
		Affine affine = new Affine();
		gc.save();
		affine.appendTranslation(x - (width / 2), y - (height / 2));
		affine.appendRotation(r);
		affine.appendScale(scalef * (width / img.getWidth()), scalef * (height / img.getHeight()));
		gc.setTransform(affine);
		gc.drawImage(img, 0, 0);
		gc.restore();
		if (Display.Display.debug) {
			if (boundingBox != null) {
				gc.save();
				affine = new Affine();
				affine.appendTranslation(x - (width / 2), y - (height / 2));
				affine.appendRotation(r);
				gc.setTransform(affine);
				gc.setStroke(Color.BLACK);
				gc.strokeRect(0, 0, boundingBox.getWidth(), boundingBox.getHeight());
				gc.restore();
			}
		}
	}

	public void update() {
		r += dr;
		x += dx;
		y += dy;
	}
}
