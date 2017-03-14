/*
 * Author: Kyle Lawson
 * 
 * Description: subclass for cover block item. Item allows robbers to avoid sniper fire when behind
 */

package Objects;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class CoverBlock extends GameObject {
	public final static int gridCode = 'C';

	public CoverBlock(double x, double y, double s) {
		width = s;
		height = s;
		img = new Image("objectAssets/cover.gif");
		this.x = x + (width / 2);// center x and y
		this.y = y + (height / 2);

		scalef = 1;
		// System.out.println(cellSize + " " + scalef);
		boundingBox = new BoundingBox(x, y, width, height);
	}
}
