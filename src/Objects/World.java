package Objects;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.canvas.GraphicsContext;

public class World {
	public ArrayList<GameObject> worldArray; // list of world objects

	public World() {
		worldArray = new ArrayList<GameObject>();
	}

	public void update() {
		for (GameObject o : worldArray) {
			o.update();
		}
	}

	public void populateWorld(Grid grid) {
		for (int i = 0; i < grid.cols; i++) {
			for (int j = 0; j < grid.rows; j++) {
				double x = j * grid.cellSize;
				double y = i * grid.cellSize;

				switch (grid.gridCode[j][i]) {
				case BrickBlock.gridCode:
					worldArray.add(new BrickBlock(x, y, grid.cellSize));
					break;
				case CoverBlock.gridCode:
					worldArray.add(new CoverBlock(x, y, grid.cellSize));
					break;
				case Bank.gridCode:
					worldArray.add(new Bank(x, y, grid.cellSize));
					break;
				}
			}
		}
		// debugPrint();
	}

	public void debugPrint() {
		for (GameObject o : worldArray)
			System.out.println(o.getClass().getName() + " " + o.x + "," + o.y);
	}

	public void drawWorld(GraphicsContext gc) {
		for (GameObject o : worldArray) {
			o.render(gc);
		}
	}

	public int getSize() {
		return worldArray.size();
	}

	public void addObject(Grid grid, int r, int c) {
		double y = c * grid.cellSize;
		double x = r * grid.cellSize;
		switch (grid.gridCode[r][c]) {
		case BrickBlock.gridCode:
			worldArray.add(new BrickBlock(x, y, grid.cellSize));
			break;
		case CoverBlock.gridCode:
			worldArray.add(new CoverBlock(x, y, grid.cellSize));
			break;
		case Bank.gridCode:
			worldArray.add(new Bank(x, y, grid.cellSize));
			break;
		}
	}

	// checks the world for instances of the object
	public boolean constains(Class<?> cls) {
		boolean exists = false;
		for (GameObject o : worldArray) {
			if (o.getClass() == cls)
				exists = true;
		}
		return exists;
	}

	// removes all instances of objects
	public void remove(Class<Bank> cls) {
		Iterator<GameObject> iter = worldArray.iterator();

		while (iter.hasNext()) {
			GameObject o = iter.next();
			if (o.getClass() == cls)
				iter.remove();
		}

	}

}
