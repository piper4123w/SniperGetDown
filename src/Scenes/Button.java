package Scenes;

import javafx.scene.image.*;
import javafx.scene.transform.Affine;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.geometry.*;
import javafx.scene.canvas.GraphicsContext;

public class Button extends Scene {
	Image img;
	String title;
	boolean imageButton = true;

	double width = 0;
	double height = 0;

	double scaleW = 1;
	double scaleH = 1;
	double bigScaleW, bigScaleH;
	double smallScaleW = 1;
	double smallScaleH = 1;
	double x, y;
	final double hoverScale = 0.1;
	boolean oldHover, newHover = true;

	String message;

	public Button(String t, double x, double y, String message, double w, double h) {
		title = t;
		this.message = message;
		imageButton = false;
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}

	public Button(Image i, String message) {
		img = i;
		this.message = message;
		this.x = 0;
		this.y = 0;
	}

	public Button(Image i, double x, double y, String message) {
		img = i;
		this.x = x;
		this.y = y;
		this.message = message;
	}

	public Button(Image i, double x, double y, double scale, String message) {
		img = i;
		this.x = x;
		this.y = y;
		this.message = message;
		this.scaleW = scale;
		this.scaleH = scale;
		this.smallScaleW = scale;
		this.smallScaleH = scale;
		this.bigScaleW = scale + hoverScale;
		this.bigScaleH = scale + hoverScale;
	}

	@SuppressWarnings("deprecation")
	public Button(Image i, double x, double y, double sizeW, double sizeH, String message) {
		img = new Image(i.impl_getUrl(), sizeW, sizeH, false, false);
		this.x = x;
		this.y = y;
		this.message = message;
		this.scaleH = 1;
		this.scaleW = 1;
	}

	public BoundingBox getBoundingBox() {
		if (imageButton) {
			width = img.getWidth();
			height = img.getHeight();
		}
		return new BoundingBox(x - ((width * scaleW) / 2), y - ((height * scaleH) / 2), width * scaleW,
				height * scaleH);

	}

	public void hoverEffect(boolean hovering) {
		if (hovering) {
			if (bigScaleW == 0 || bigScaleH == 0) {
				bigScaleW = scaleW + hoverScale;
				bigScaleH = scaleH + hoverScale;
			}
			scaleW = bigScaleW;
			scaleH = bigScaleH;
		} else {
			scaleW = smallScaleW;
			scaleH = smallScaleH;
		}
	}

	public void render(GraphicsContext gc) {
		if (!imageButton) {
			gc.setFill(Color.LIGHTGREY);
			gc.fillRoundRect(x-width/2, y-height/2, width, height, 10, 10);
		}
		gc.save();
		Affine affine = new Affine();
		affine.appendScale(this.scaleW, this.scaleH);
		affine.appendTranslation(this.x / this.scaleW, this.y / this.scaleH);
		gc.setTransform(affine);
		if (imageButton)
			gc.drawImage(this.img, -this.img.getWidth() / 2, -this.img.getHeight() / 2);
		else {
			gc.setFill(Color.BLACK);
			gc.setFont(new Font("Arial Black", 25));
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText(title, 0, 0);
			//System.out.println("Filling at " + x + ',' + y + " " + title);
		}
		gc.restore();

	}
}
