package Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;

public class GameObject extends Objects.World {
	public double x, y;
	double dx, dy, r, dr;
	public Image img;
	
	double scalef;

	public void render(GraphicsContext gc) {
		Affine affine = new Affine();

		gc.save();
		affine.appendScale(scalef, scalef);
		affine.appendTranslation(x / scalef, y / scalef);
		affine.appendRotation(r);
		gc.setTransform(affine);
		gc.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2);
		gc.restore();
	}
	
	public void update(){
		r += dr;
		x += dx;
		y += dy;
	}

}
